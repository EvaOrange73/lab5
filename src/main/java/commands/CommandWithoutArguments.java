package commands;

import control.CollectionManager;
import control.IOManager;
import inputExceptions.InputException;

/**
 * Класс для команд без аргументов
 */
public abstract class CommandWithoutArguments extends Command {

    CommandWithoutArguments(String name, String description, IOManager ioManager, CollectionManager collectionManager) {
        super(name, description, ioManager, collectionManager);
    }

    @Override
    public void execute(String argument) throws InputException {
        this.ioManager.print("Это команда без аргументов");
        this.execute();
    }
}
