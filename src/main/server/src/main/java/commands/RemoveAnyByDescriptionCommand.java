package commands;

import control.*;
import control.response.CommandResponse;
import data.MusicBand;
import data.description.Types;

import java.util.ArrayList;

public class RemoveAnyByDescriptionCommand extends Command {

    public RemoveAnyByDescriptionCommand(CollectionManager collectionManager, UserManager userManager) {
        super(new CommandDescription(
                        "remove_any_by_description",
                        "удалить из коллекции один элемент, значение поля description которого эквивалентно заданному",
                        "description",
                        Types.STRING),
                collectionManager, userManager);
    }

    @Override
    public CommandResponse execute(Request request) {
        ArrayList<Integer> ids = super.userManager.getListOfUserOwnedItemIds(request.getUserID());
        for (Integer id: ids) {
            MusicBand musicBand = super.collectionManager.getById(id);
            if (musicBand.getDescription().equals(request.getArgument())) {
                super.collectionManager.remove(id);
                return new CommandResponse();
            }
        }
        return new CommandResponse("В коллекции нет вашего элемента с таким description");
    }

}
