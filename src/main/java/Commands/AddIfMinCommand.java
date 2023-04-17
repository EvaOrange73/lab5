package Commands;

import Control.CollectionManager;
import Control.IOManager;
import Data.MusicBand;
import InputExceptions.InputException;

public class AddIfMinCommand extends CommandWithoutArguments{

    public AddIfMinCommand(IOManager ioManager, CollectionManager collectionManager) {
        super("add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции",
                ioManager, collectionManager);
    }
    @Override
    public void execute() throws InputException {
        MusicBand musicBand = super.ioManager.askMusicBand();
        MusicBand maxElement = super.collectionManager.getMinElement();
        if (musicBand.compareTo(maxElement) < 0) {
            super.collectionManager.add(musicBand);
            super.ioManager.print("элемент добавлен в коллекцию");
        }
        else {
            super.ioManager.print("элемент не добавлен в коллекцию");
        }
    }
}
