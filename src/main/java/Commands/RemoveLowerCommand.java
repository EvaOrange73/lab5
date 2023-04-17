package Commands;

import Control.CollectionManager;
import Control.IOManager;
import Data.MusicBand;
import InputExceptions.InputException;

import java.util.LinkedHashSet;

public class RemoveLowerCommand extends CommandWithoutArguments {

    public RemoveLowerCommand(IOManager ioManager, CollectionManager collectionManager) {
        super("remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный",
                ioManager, collectionManager);
    }

    @Override
    public void execute() throws InputException {
        MusicBand musicBandReference = super.ioManager.askMusicBand();
        LinkedHashSet<MusicBand> musicBands = new LinkedHashSet<>(super.collectionManager.getCollection());
        for (MusicBand musicBand : musicBands) {
            if (musicBandReference.compareTo(musicBand) > 0) {
                super.collectionManager.remove(musicBand);
            }
        }
    }

}
