package commands;

import сontrol.CommandDescription;
import сontrol.Request;
import сontrol.Response;
import control.CollectionManager;
import data.MusicBand;
import data.description.Types;

public class RemoveByIdCommand extends Command {

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super(new CommandDescription(
                        "remove_by_id",
                        "удалить элемент из коллекции по его id",
                        "id",
                        Types.LONG
                ),
                collectionManager
        );
    }

    @Override
    public Response execute(Request request) {
        long id = (Long) request.getArgument();
        for (MusicBand musicBand : super.collectionManager.getCollection()) {
            if (musicBand.getId() == id) {
                super.collectionManager.remove(musicBand);
                return new Response();
            }
        }
        return new Response("В коллекции нет элемента с таким id");
    }
}
