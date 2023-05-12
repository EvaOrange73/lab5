package commands;

import DTO.Request;
import DTO.Response;
import control.CollectionManager;
import data.MusicBand;

public class UpdateCommand extends Command {

    public UpdateCommand(CollectionManager collectionManager) {
        super(new CommandDescription(
                        "update",
                        " id {element} : обновить значение элемента коллекции, id которого равен заданному",
                        "id",
                        "Integer",
                        true),
                collectionManager
        );
    }

    @Override
    public Response execute(Request request) {
        Integer id = (Integer) request.getArgument();
        MusicBand newMusicBand = request.getMusicBand();
        for (MusicBand musicBand : super.collectionManager.getCollection()) {
            if (musicBand.getId().equals(id)) {
                super.collectionManager.remove(musicBand);
                super.collectionManager.add(newMusicBand);
                return new Response();
            }
        }
        return new Response("В коллекции нет элемента с заданным id");
    }

}
