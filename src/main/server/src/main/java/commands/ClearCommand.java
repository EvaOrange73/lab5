package commands;

import DTO.Request;
import DTO.Response;
import control.CollectionManager;

public class ClearCommand extends Command {
    public ClearCommand(CollectionManager collectionManager) {
        super(new CommandDescription(
                        "clear",
                        " : очистить коллекцию"
                        ),
                collectionManager);
    }

    @Override
    public Response execute(Request request) {
        super.collectionManager.clear();
        return new Response();
    }
}
