package server;

import Control.CommandDescription;
import Control.Request;
import Control.Response;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


/**
 * Менеджер сервера - класс, отвечающий за отправку запросов и получение ответов от сервера
 */
public class ServerManager {
    int port;

    public ServerManager(int port){
        this.port = port;
    }

    public ArrayList<CommandDescription> askCommands() throws IOException{
        try (Socket client = new Socket("localhost", port)) {
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            out.writeObject(new Request("getCommands"));

            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            return (ArrayList<CommandDescription>) in.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("что-то пошло не так при отправке списка команд с сервера");
        }
    }

    public Response request(Request request) throws IOException{
        try (Socket client = new Socket("localhost", port)) {
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            out.writeObject(request);

            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            return (Response) in.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("что-то пошло не так при отправке команды на сервер");
        }
    }
}
