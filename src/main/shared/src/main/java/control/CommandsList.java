package control;

import java.io.Serializable;
import java.util.ArrayList;

public class CommandsList implements Serializable {
    ArrayList<CommandDescription> commands;
    public CommandsList(ArrayList<CommandDescription> commands){
        this.commands = commands;
    }

    public ArrayList<CommandDescription> getCommands() {
        return commands;
    }
}
