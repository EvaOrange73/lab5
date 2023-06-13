package server;

import control.CommandDescription;
import control.response.AuthorizeResponse;
import control.response.CommandsListResponse;
import control.Request;
import control.response.CommandResponse;
import data.User;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;


/**
 * Менеджер сервера - класс, отвечающий за отправку запросов и получение ответов от сервера
 */
public class ServerManager {
    int port;

    public ServerManager(int port) {
        this.port = port;
    }

    public ArrayList<CommandDescription> askCommands(Integer userId){
        try (SocketChannel server = SocketChannel.open()) {
            SocketAddress socketAddr = new InetSocketAddress("localhost", port);
            server.connect(socketAddr);
            ObjectOutputStream out = new ObjectOutputStream(server.socket().getOutputStream());
            out.writeObject(new Request("getCommands", userId));
            ObjectInputStream in = new ObjectInputStream(server.socket().getInputStream());
            return ((CommandsListResponse) in.readObject()).getCommands();
        } catch (ClassNotFoundException | IOException e) { //TODO это плохая идея
            throw new RuntimeException("что-то пошло не так при отправке списка команд с сервера");
        }
    }

    public CommandResponse request(Request request) throws IOException {
        try (SocketChannel server = SocketChannel.open()) {
            SocketAddress socketAddr = new InetSocketAddress("localhost", 9000);
            server.connect(socketAddr);
            ObjectOutputStream out = new ObjectOutputStream(server.socket().getOutputStream());
            out.writeObject(request);
            ObjectInputStream in = new ObjectInputStream(server.socket().getInputStream());
            return (CommandResponse) in.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("что-то пошло не так при отправке команды на сервер");
        }
    }

    public Integer authorize(User user) throws IOException{
        try (SocketChannel server = SocketChannel.open()) {
            SocketAddress socketAddr = new InetSocketAddress("localhost", 9000);
            server.connect(socketAddr);
            ObjectOutputStream out = new ObjectOutputStream(server.socket().getOutputStream());
            out.writeObject(new Request("authorize", user, null));
            ObjectInputStream in = new ObjectInputStream(server.socket().getInputStream());
            return ((AuthorizeResponse) in.readObject()).getUserId();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("что-то пошло не так при авторизации");
        }
    }
}
