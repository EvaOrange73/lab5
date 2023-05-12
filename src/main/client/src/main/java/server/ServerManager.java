package server;

import commands.CommandDescription;
import commands.CommandManager;

import java.util.ArrayList;

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
