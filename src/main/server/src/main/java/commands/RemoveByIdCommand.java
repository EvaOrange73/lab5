package commands;

import DTO.Request;
import DTO.Response;
import control.CollectionManager;
import data.MusicBand;

public class RemoveByIdCommand extends Command {

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super(new CommandDescription(
                        "remove_by_id",
                        " id : удалить элемент из коллекции по его id",
                        "id", "long"),
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