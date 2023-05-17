package control;

import сontrol.Request;
import exceptions.EnvException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Менеджер клиента - класс, отвечающий за обработку запросов и отправку ответов клиентам
 */
public class ClientManager {
    private final CommandManager commandManager;
    private final ServerSocket serverSocket;

    public ClientManager(int port, CommandManager commandManager) {
        this.commandManager = commandManager;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void connectClient() {
        while (true) {
            try (Socket server = serverSocket.accept()) {
                ObjectInputStream in = new ObjectInputStream(server.getInputStream());
                Request request = (Request) in.readObject();
                ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
                if (request.getCommandName().equals("getCommands"))
                    out.writeObject(commandManager.getCommandDescriptions());
                else
                    out.writeObject(this.commandManager.execute(request));
            } catch (ClassNotFoundException e) {
                System.out.println("на сервер пришел запрос в неверном формате");
            } catch (IOException e) {
                System.out.println("io exception");
            } catch (EnvException e) {
                System.out.println(e.toString());
            }
        }
    }
}
