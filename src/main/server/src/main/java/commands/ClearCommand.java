package commands;

import control.*;
import control.response.CommandResponse;

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
    public CommandResponse execute(Request request) {
        ArrayList<Integer> ids = super.userManager.getListOfUserOwnedItemIds(request.getUser().getId());
        for (Integer id: ids) {
            super.collectionManager.remove(id);
        }
        return new CommandResponse();
    }
}
