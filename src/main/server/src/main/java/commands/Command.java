package commands;

import DTO.Request;
import DTO.Response;
import control.CollectionManager;

/**
 * Абстрактный класс для команды
 */
public abstract class Command {
    protected CommandDescription commandDescription;

    protected CollectionManager collectionManager;

    Command(CommandDescription commandDescription, CollectionManager collectionManager) {
        this.commandDescription = commandDescription;
        this.collectionManager = collectionManager;
    }

    public abstract Response execute(Request request);

    public String getName() {
        return this.commandDescription.getName();
    }
}
