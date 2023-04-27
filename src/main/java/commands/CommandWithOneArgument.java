package commands;

import control.CollectionManager;
import control.IOManager;
import inputExceptions.NoArgumentsException;
import inputExceptions.TypeOfArgumentException;

/**
 * Класс для команд с одним аргументом
 */
public abstract class CommandWithOneArgument extends Command {
    protected String argument;
    protected String type;

    CommandWithOneArgument(String description, IOManager ioManager, CollectionManager collectionManager, String argument, String type) {
        super(description, ioManager, collectionManager);
        this.argument = argument;
        this.type = type;
    }
    @Override
    public void execute() throws NoArgumentsException {
        throw new NoArgumentsException(this.argument, this.type);
    }

    protected int parseInt(String str) throws TypeOfArgumentException {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e){
            throw new TypeOfArgumentException(this.argument, this.type);
        }
    }
    protected Long parseLong(String str) throws TypeOfArgumentException {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e){
            throw new TypeOfArgumentException(this.argument, this.type);
        }
    }
}
