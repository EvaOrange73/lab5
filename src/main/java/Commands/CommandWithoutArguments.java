package Commands;

import Control.CollectionManager;
import Control.IOManager;
import InputExceptions.InputException;

/**
 * Класс для команд без аргументов
 */
public abstract class CommandWithoutArguments extends Command {

    CommandWithoutArguments(String description, IOManager ioManager, CollectionManager collectionManager) {
        super(description, ioManager, collectionManager);
    }

    @Override
    public void execute(String argument) throws InputException {
        this.ioManager.print("Это команда без аргументов");
        this.execute();
    }
}
