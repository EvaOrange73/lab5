package commands;

import control.*;
import control.response.CommandResponse;
import data.MusicBand;

import java.util.ArrayList;

public class RemoveLowerCommand extends Command {

    public RemoveLowerCommand(CollectionManager collectionManager, UserManager userManager) {
        super(new CommandDescription("remove_lower", "удалить из коллекции все ваши элементы, меньшие, чем заданный",
                true), collectionManager, userManager);
    }

    @Override
    public CommandResponse execute(Request request) {
        MusicBand musicBandReference = request.getMusicBand();
        ArrayList<Integer> ids = super.userManager.getListOfUserOwnedItemIds(request.getUserID());
        for (Integer id: ids) {
            MusicBand musicBand = super.collectionManager.getById(id);
            if (musicBand.compareTo(musicBandReference) < 0) {
                super.collectionManager.remove(id);
            }
        }
        return new CommandResponse();
    }

}
