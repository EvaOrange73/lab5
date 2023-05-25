package control;

import exceptions.EnvException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Менеджер клиента - класс, отвечающий за обработку запросов и отправку ответов клиентам
 */
public class ClientManager {
    private final CommandManager commandManager;
    private final ServerSocketChannel serverSocket;

    public ClientManager(int port, CommandManager commandManager) {
        this.commandManager = commandManager;
        try {
            this.serverSocket = ServerSocketChannel.open();
            serverSocket.configureBlocking(false);
            serverSocket.socket().bind(new InetSocketAddress(port));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close(){
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void connectClient() {
        try (SocketChannel client = serverSocket.accept()) {
            if (client != null) {
                ObjectInputStream in = new ObjectInputStream(client.socket().getInputStream());
                Request request = (Request) in.readObject();
                ObjectOutputStream out = new ObjectOutputStream(client.socket().getOutputStream());
                if (request.getCommandName().equals("getCommands"))
                    out.writeObject(new CommandsList(commandManager.getCommandDescriptions()));
                else
                    out.writeObject(this.commandManager.execute(request));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("на сервер пришел запрос в неверном формате");
        } catch (IOException e) {
            System.out.println("io exception");
        } catch (EnvException e) {
            System.out.println(e.toString());
        }
    }
}