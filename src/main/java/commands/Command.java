package commands;

import control.CollectionManager;
import control.IOManager;
import inputExceptions.InputException;

/**
 * Абстрактный класс для команды
 */
public abstract class Command {
    protected String description;
    protected IOManager ioManager;
    protected CollectionManager collectionManager;

    Command(String description, IOManager ioManager, CollectionManager collectionManager) {
        this.description = description;
        this.ioManager = ioManager;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполнить команду без аргументов
     *
     * @throws InputException если команде требуются аргументы
     */
    public abstract void execute() throws InputException;

    /**
     * Выполнить команду с аргументом
     *
     * @param argument аргумент
     * @throws InputException если команда не требует аргументов
     */
    public abstract void execute(String argument) throws InputException;

    /**
     * @return описание команды для help
     */
    String getDescription() {
        return this.description;
    }


}
