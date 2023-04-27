package commands;

import control.CollectionManager;
import control.IOManager;

public class ClearCommand extends CommandWithoutArguments {
    public ClearCommand(IOManager ioManager, CollectionManager collectionManager) {
        super("clear : очистить коллекцию", ioManager, collectionManager);
    }

    @Override
    public void execute() {
        super.collectionManager.clear();
    }
}
