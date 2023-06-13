package control.response;

import control.CommandDescription;

import java.io.Serializable;
import java.util.ArrayList;

public class CommandsListResponse extends Response implements Serializable {
    ArrayList<CommandDescription> commands;
    public CommandsListResponse(ArrayList<CommandDescription> commands){
        this.commands = commands;
    }

    public ArrayList<CommandDescription> getCommands() {
        return commands;
    }
}
