package commands;

import control.CommandDescription;
import control.Request;
import control.response.CommandResponse;
import control.CollectionManager;
import data.MusicBand;

public class AddIfMinCommand extends Command {

    public AddIfMinCommand(CollectionManager collectionManager) {
        super(new CommandDescription(
                        "add_if_min",
                        "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции",
                        true
                ),
                collectionManager);
    }

    @Override
    public CommandResponse execute(Request request) {
        for (MusicBand musicBand: super.collectionManager.getCollection()){
            if (musicBand.equals(request.getMusicBand())) return new CommandResponse("В коллекции уже есть такой экземпляр.");
        }
        MusicBand musicBand = request.getMusicBand();
        MusicBand maxElement = super.collectionManager.getMinElement();
        if (musicBand.compareTo(maxElement) < 0) {
            super.collectionManager.add(musicBand);
            return new CommandResponse("элемент добавлен в коллекцию");
        } else {
            return new CommandResponse("элемент не добавлен в коллекцию");
        }
    }
}
