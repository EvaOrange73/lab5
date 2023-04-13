package Commands;

import Control.CollectionManager;
import Control.IOManager;
import DataDescription.MusicBand;
import InputExceptions.InputException;

public class AddCommand extends CommandWithoutArguments {

    public AddCommand(IOManager ioManager, CollectionManager collectionManager) {
        super("add {element} : добавить новый элемент в коллекцию", ioManager, collectionManager);
    }

    @Override
    public void execute() throws InputException {
        MusicBand musicBand = super.ioManager.askMusicBand();
        super.collectionManager.add(musicBand);
    }
}
