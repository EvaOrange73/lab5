package Commands;

import Control.CollectionManager;
import Control.IOManager;
import Data.MusicBand;
import InputExceptions.InputException;

public class AddIfMaxCommand extends CommandWithoutArguments {

    public AddIfMaxCommand(IOManager ioManager, CollectionManager collectionManager) {
        super("добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции",
                ioManager, collectionManager);
    }

    @Override
    public void execute() throws InputException {
        MusicBand musicBand = super.ioManager.askMusicBand();
        MusicBand maxElement = super.collectionManager.getMaxElement();
        if (musicBand.compareTo(maxElement) > 0) {
            super.collectionManager.add(musicBand);
            super.ioManager.print("элемент добавлен в коллекцию");
        } else {
            super.ioManager.print("элемент не добавлен в коллекцию");
        }
    }
}

