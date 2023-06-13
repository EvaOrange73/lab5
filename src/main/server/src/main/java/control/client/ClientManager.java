package control.client;

import control.CommandManager;
import control.UserManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * Менеджер клиента - класс, отвечающий за обработку запросов и отправку ответов клиентам
 */
public class ClientManager {
    private final CommandManager commandManager;
    private final UserManager userManager;
    private final ServerSocketChannel serverSocket;
    private final ClientPool clientPool;
    private Thread readRequestThread;
    private final ForkJoinPool prepareResponsePool;
    private final ExecutorService sendResponsePool;

    public ClientManager(int port, CommandManager commandManager, UserManager userManager) {
        this.commandManager = commandManager;
        this.userManager = userManager;
        try {
            this.serverSocket = ServerSocketChannel.open();
            serverSocket.configureBlocking(false);
            serverSocket.socket().bind(new InetSocketAddress(port));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.clientPool = new ClientPool();
        int p = Runtime.getRuntime().availableProcessors() / 2;
        this.prepareResponsePool = new ForkJoinPool(p);
        this.sendResponsePool = Executors.newFixedThreadPool(p);

    }

    public void close() {
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void connectClient() {
        try {
            SocketChannel client = serverSocket.accept();
            if (client != null) {
                clientPool.addClientToReadRequest(new Client(client.socket()));
                this.readRequestThread = new Thread(new ReadRequest(clientPool));
                this.readRequestThread.start();
                this.prepareResponsePool.execute(new PrepareResponse(clientPool, userManager, commandManager));
                this.sendResponsePool.submit(new SendResponse(clientPool));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}