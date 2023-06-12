package commands;

import control.*;
import data.MusicBand;
import data.description.Types;

import java.util.ArrayList;

public class RemoveByIdCommand extends Command {

    public RemoveByIdCommand(CollectionManager collectionManager, UserManager userManager) {
        super(new CommandDescription(
                        "remove_by_id",
                        "удалить элемент из коллекции по его id",
                        "id",
                        Types.LONG
                ),
                collectionManager,
                userManager
        );
    }

    @Override
    public Response execute(Request request) {
        long argument = (Long) request.getArgument();
        ArrayList<Integer> ids = super.userManager.getListOfUserOwnedItemIds(request.getUserID());
        for (Integer id: ids) {
            MusicBand musicBand = super.collectionManager.getById(id);
            if (musicBand.getId() == argument) {
                super.collectionManager.remove(id);
                return new Response();
            }
        }
        return new Response("В коллекции нет вашего элемента с таким id");
    }
}
