package server;

import Control.CommandDescription;
import Control.Request;
import Control.Response;
import commands.CommandManager;

import java.util.ArrayList;


/**
 * Менеджер сервера - класс, отвечающий за отправку запросов и получение ответов от сервера
 */
public class ServerManager { //TODO

    ServerManager(){
        createCommandManager(this);
    }

    private void createCommandManager(ServerManager serverManager){
        ArrayList<CommandDescription> commands = null;
        CommandManager commandManager = new CommandManager(commands, this);
    }

    public Response request(Request request){
        return null; //TODO
    }
}
