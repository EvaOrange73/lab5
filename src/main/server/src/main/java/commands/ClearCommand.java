package commands;

import control.CommandDescription;
import control.Request;
import control.Response;
import control.CollectionManager;

public class ClearCommand extends Command {
    public ClearCommand(CollectionManager collectionManager) {
        super(new CommandDescription(
                        "clear",
                        "очистить коллекцию"
                        ),
                collectionManager);
    }

    @Override
    public Response execute(Request request) {
        super.collectionManager.clear();
        return new Response();
    }
}
