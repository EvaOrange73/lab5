package commands;

import control.CollectionManager;
import control.IOManager;
import data.MusicBand;
import inputExceptions.TypeOfArgumentException;

public class RemoveByIdCommand extends CommandWithOneArgument {

    public RemoveByIdCommand(IOManager ioManager, CollectionManager collectionManager) {
        super("remove_by_id id : удалить элемент из коллекции по его id",
                ioManager, collectionManager,
                "id", "long");
    }

    @Override
    public void execute(String argument) throws TypeOfArgumentException {
        long id = super.parseLong(argument);
        for (MusicBand musicBand : super.collectionManager.getCollection()) {
            if (musicBand.getId() == id) {
                super.collectionManager.remove(musicBand);
                return;
            }
        }
        super.ioManager.print("В коллекции нет элемента с таким id");
    }
}
