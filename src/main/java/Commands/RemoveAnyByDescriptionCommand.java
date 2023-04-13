package Commands;

import Control.CollectionManager;
import Control.IOManager;
import DataDescription.MusicBand;

public class RemoveAnyByDescriptionCommand extends CommandWithOneArgument {

    public RemoveAnyByDescriptionCommand(IOManager ioManager, CollectionManager collectionManager) {
        super("remove_any_by_description description : удалить из коллекции один элемент, значение поля description которого эквивалентно заданному",
                ioManager, collectionManager,
                "description", "String");
    }

    @Override
    public void execute(String argument) {
        for (MusicBand musicBand : super.collectionManager.getCollection()) {
            if (musicBand.getDescription().equals(argument)) {
                super.collectionManager.remove(musicBand);
                return;
            }
        }
        super.ioManager.print("В коллекции нет элемента с таким description");
    }

}
