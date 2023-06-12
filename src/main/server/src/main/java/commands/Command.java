package commands;

import control.*;

/**
 * Абстрактный класс для команды
 */
public abstract class Command {
    protected final CommandDescription commandDescription;

    protected final CollectionManager collectionManager;
    protected final UserManager userManager;

    Command(CommandDescription commandDescription, CollectionManager collectionManager, UserManager userManager) {
        this.commandDescription = commandDescription;
        this.collectionManager = collectionManager;
        this.userManager = userManager;
    }

    Command(CommandDescription commandDescription, CollectionManager collectionManager){
        this(commandDescription, collectionManager, null);
    }

    public abstract Response execute(Request request);

    public String getName() {
        return this.commandDescription.getName();
    }

    public CommandDescription getDescription() {
        return this.commandDescription;
    }
}
