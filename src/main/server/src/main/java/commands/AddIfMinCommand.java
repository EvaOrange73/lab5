package commands;

import control.CommandDescription;
import control.Request;
import control.Response;
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
    public Response execute(Request request) {
        for (MusicBand musicBand: super.collectionManager.getCollection()){
            if (musicBand.equals(request.getMusicBand())) return new Response("В коллекции уже есть такой экземпляр.");
        }
        MusicBand musicBand = request.getMusicBand();
        MusicBand maxElement = super.collectionManager.getMinElement();
        if (musicBand.compareTo(maxElement) < 0) {
            super.collectionManager.add(musicBand);
            return new Response("элемент добавлен в коллекцию");
        } else {
            return new Response("элемент не добавлен в коллекцию");
        }
    }
}
