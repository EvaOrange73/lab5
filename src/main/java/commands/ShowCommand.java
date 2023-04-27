package commands;

import control.CollectionManager;
import control.IOManager;
import data.MusicBand;

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
