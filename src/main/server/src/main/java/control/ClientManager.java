package control;

import DTO.Request;
import DTO.Response;
import commands.Command;

import java.util.ArrayList;

public class ClientManager { //TODO
    CommandManager commandManager;

    public ArrayList<Command> getCommands(){
        return commandManager.getCommands();
    }

    public Response executeCommand(Request request) {
        return commandManager.execute(request);
    }
}
