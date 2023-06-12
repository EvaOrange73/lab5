package commands;

import control.CommandDescription;
import control.Request;
import control.Response;
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
    public Response execute(Request request) {
        for (MusicBand musicBand: super.collectionManager.getCollection()){
            if (musicBand.equals(request.getMusicBand())) return new Response("В коллекции уже есть такой экземпляр.");
        }
        MusicBand musicBand = request.getMusicBand();
        MusicBand maxElement = super.collectionManager.getMaxElement();
        if (musicBand.compareTo(maxElement) > 0) {
            super.collectionManager.add(musicBand);
            return new Response("элемент добавлен в коллекцию");
        } else {
            return new Response("элемент не добавлен в коллекцию");
        }
    }
}

