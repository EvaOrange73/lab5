package Commands;

import Control.CollectionManager;
import Control.IOManager;
import Data.MusicBand;

public class ShowCommand extends CommandWithoutArguments {

    public ShowCommand(IOManager ioManager, CollectionManager collectionManager) {
        super("show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении",
                ioManager, collectionManager);
    }

    @Override
    public void execute() {
        for (MusicBand musicBand : super.collectionManager.getCollection()) {
            super.ioManager.print(musicBand.toString());
        }
    }

}
