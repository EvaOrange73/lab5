package commands;

import control.*;

import java.util.ArrayList;

public class ClearCommand extends Command {
    public ClearCommand(CollectionManager collectionManager, UserManager userManager) {
        super(new CommandDescription(
                        "clear",
                        "удалить все ваши элементы коллекции"
                        ),
                collectionManager, userManager);
    }

    @Override
    public Response execute(Request request) {
        ArrayList<Integer> ids = super.userManager.getListOfUserOwnedItemIds(request.getUserID());
        for (Integer id: ids) {
            super.collectionManager.remove(id);
        }
        return new Response();
    }
}
