package commands;

import сontrol.CommandDescription;
import сontrol.Request;
import сontrol.Response;
import control.CollectionManager;

/**
 * Абстрактный класс для команды
 */
public abstract class Command {
    protected final CommandDescription commandDescription;

    protected final CollectionManager collectionManager;

    Command(CommandDescription commandDescription, CollectionManager collectionManager) {
        this.commandDescription = commandDescription;
        this.collectionManager = collectionManager;
    }

    public abstract Response execute(Request request);

    public String getName() {
        return this.commandDescription.getName();
    }

    public CommandDescription getDescription() {
        return this.commandDescription;
    }
}
