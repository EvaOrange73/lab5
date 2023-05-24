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

    public ClientManager(int port, CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public void connectClient() {
        try (ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
            serverSocket.configureBlocking(false);
            serverSocket.socket().bind(new InetSocketAddress(9000));
            while (true) {
                try (SocketChannel client = serverSocket.accept()) {
                    if (client != null) {
                        ObjectInputStream in = new ObjectInputStream(client.socket().getInputStream());
                        Request request = (Request) in.readObject();
                        ObjectOutputStream out = new ObjectOutputStream(client.socket().getOutputStream());
                        if (request.getCommandName().equals("getCommands"))
                            out.writeObject(commandManager.getCommandDescriptions());
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}