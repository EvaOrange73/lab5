package commands;

import control.CollectionManager;
import control.IOManager;
import data.MusicBand;
import inputExceptions.InputException;

public class AddCommand extends CommandWithoutArguments {

    public AddCommand(IOManager ioManager, CollectionManager collectionManager) {
        super("add", " {element} : добавить новый элемент в коллекцию", ioManager, collectionManager);
    }

    @Override
    public void execute() throws InputException {
        MusicBand musicBand = super.ioManager.askMusicBand();
        super.collectionManager.add(musicBand);
    }
}
