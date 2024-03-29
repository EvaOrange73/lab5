package commands;

import control.*;
import control.response.CommandResponse;
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
    public CommandResponse execute(Request request) {
        Integer oldMusicBandId = (Integer) request.getArgument();
        MusicBand newMusicBand = request.getMusicBand();
        newMusicBand.setId(oldMusicBandId);
        if ((!userManager.checkRights(request.getUser().getId(), oldMusicBandId)))
            return new CommandResponse("У вас нет прав на изменение элементами с заданным id");
        for (MusicBand oldMusicBand : super.collectionManager.getCollection()) {
            if (oldMusicBand.getId().equals(oldMusicBandId)) {
                super.collectionManager.update(oldMusicBand, newMusicBand);
                return new CommandResponse();
            }
        }
        return new CommandResponse("В коллекции нет элемента с заданным id");
    }

}
