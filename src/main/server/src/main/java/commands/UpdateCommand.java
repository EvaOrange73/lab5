package commands;

import control.*;
import data.MusicBand;
import data.description.Types;

public class UpdateCommand extends Command {

    public UpdateCommand(CollectionManager collectionManager, UserManager userManager) {
        super(new CommandDescription(
                        "update",
                        "id",
                        Types.INTEGER,
                        true,
                        "обновить значение элемента коллекции, id которого равен заданному"
                ),
                collectionManager,
                userManager
        );
    }

    @Override
    public Response execute(Request request) {
        Integer oldMusicBandId = (Integer) request.getArgument();
        MusicBand newMusicBand = request.getMusicBand();
        if ((!userManager.checkRights(request.getUserID(), oldMusicBandId)))
            return new Response("У вас нет прав на изменение элементами с заданным id");
        for (MusicBand oldMusicBand : super.collectionManager.getCollection()) {
            if (newMusicBand.getId().equals(oldMusicBandId)) {
                super.collectionManager.update(oldMusicBand, newMusicBand);
                return new Response();
            }
        }
        return new Response("В коллекции нет элемента с заданным id");
    }

}
