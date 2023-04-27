package commands;

import control.CollectionManager;
import control.IOManager;

public class SaveCommand extends CommandWithoutArguments {

    public SaveCommand(IOManager ioManager, CollectionManager collectionManager) {
        super("save : сохранить коллекцию в файл", ioManager, collectionManager);
    }

    @Override
    public void execute() {
        super.ioManager.saveInStartFile(collectionManager.getCollection());
    }

}
