package commands;

import control.CommandDescription;
import control.Request;
import control.response.CommandResponse;
import control.CollectionManager;
import data.MusicBand;

public class AddIfMaxCommand extends Command {

    public AddIfMaxCommand(CollectionManager collectionManager) {
        super(new CommandDescription(
                        "add_if_max",
                        "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции",
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
        MusicBand maxElement = super.collectionManager.getMaxElement();
        if (musicBand.compareTo(maxElement) > 0) {
            super.collectionManager.add(musicBand);
            return new CommandResponse("элемент добавлен в коллекцию");
        } else {
            return new CommandResponse("элемент не добавлен в коллекцию");
        }
    }
}

