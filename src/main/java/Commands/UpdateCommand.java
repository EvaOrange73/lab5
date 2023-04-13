package Commands;

import Control.CollectionManager;
import Control.IOManager;
import DataDescription.MusicBand;
import InputExceptions.InputException;

public class UpdateCommand extends CommandWithOneArgument {

    public UpdateCommand(IOManager ioManager, CollectionManager collectionManager) {
        super("update id {element} : обновить значение элемента коллекции, id которого равен заданному",
                ioManager, collectionManager,
                "id", "Integer");
    }

    @Override
    public void execute(String argument) throws InputException {
        Integer id = super.parseInt(argument);
        MusicBand newMusicBand = super.ioManager.askMusicBand();
        for (MusicBand musicBand : super.collectionManager.getCollection()) {
            if (musicBand.getId().equals(id)) {
                super.collectionManager.remove(musicBand);
                super.collectionManager.add(newMusicBand);
                return;
            }
        }
        super.ioManager.print("В коллекции нет элемента с заданным id");
    }

}
