package commands;

import Control.CommandDescription;
import Control.Request;
import Control.Response;
import control.CollectionManager;
import data.MusicBand;
import data.description.Types;

public class RemoveAnyByDescriptionCommand extends Command {

    public RemoveAnyByDescriptionCommand(CollectionManager collectionManager) {
        super(new CommandDescription(
                        "remove_any_by_description",
                        " description : удалить из коллекции один элемент, значение поля description которого эквивалентно заданному",
                        "description",
                        Types.STRING),
                collectionManager);
    }

    @Override
    public Response execute(Request request) {
        for (MusicBand musicBand : super.collectionManager.getCollection()) {
            if (musicBand.getDescription().equals(request.getArgument())) {
                super.collectionManager.remove(musicBand);
                return new Response();
            }
        }
        return new Response("В коллекции нет элемента с таким description");
    }

}
