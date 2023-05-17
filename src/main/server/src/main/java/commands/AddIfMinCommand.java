package commands;

import сontrol.CommandDescription;
import сontrol.Request;
import сontrol.Response;
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
