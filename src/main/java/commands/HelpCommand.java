package commands;

import control.CollectionManager;
import control.CommandManager;
import control.IOManager;

import java.util.HashMap;

public class HelpCommand extends CommandWithoutArguments {
    private final CommandManager commandManager;

    public HelpCommand(IOManager ioManager, CollectionManager collectionManager, CommandManager commandManager) {
        super("help", " : вывести справку по доступным командам", ioManager, collectionManager);
        this.commandManager = commandManager;
    }

    @Override
    public void execute() {
        for (Command command : this.commandManager.getCommands().values()) {
            super.ioManager.print(command.getDescription());
        }
    }

}
