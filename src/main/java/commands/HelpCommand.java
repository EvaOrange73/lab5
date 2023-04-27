package commands;

import control.CollectionManager;
import control.CommandManager;
import control.IOManager;

import java.util.HashMap;

public class HelpCommand extends CommandWithoutArguments {
    private HashMap<String, Command> commands;

    public HelpCommand(IOManager ioManager, CollectionManager collectionManager, CommandManager commandManager) {
        super("help : вывести справку по доступным командам", ioManager, collectionManager);
        this.commands = commandManager.getCommands();
    }

    @Override
    public void execute() {
        for (Command command : this.commands.values()) {
            super.ioManager.print(command.getDescription());
        }
    }

}
