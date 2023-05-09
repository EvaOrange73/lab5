package commands;

import control.CollectionManager;
import control.IOManager;
import data.MusicBand;
import inputExceptions.InputException;

public class AddIfMaxCommand extends CommandWithoutArguments {

    public AddIfMaxCommand(IOManager ioManager, CollectionManager collectionManager) {
        super("add_if_max", " {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции",
                ioManager, collectionManager);
    }

    @Override
    public void execute() throws InputException {
        MusicBand musicBand = super.ioManager.askMusicBand();
        MusicBand maxElement = super.collectionManager.getMaxElement();
        if (musicBand.compareTo(maxElement) < 0) {
            super.collectionManager.add(musicBand);
            super.ioManager.print("элемент добавлен в коллекцию");
        } else {
            super.ioManager.print("элемент не добавлен в коллекцию");
        }
    }
}

