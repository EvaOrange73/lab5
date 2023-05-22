package commands;

import control.CommandDescription;
import control.Request;
import control.Response;
import control.CollectionManager;

public class AddCommand extends Command {

    public AddCommand(CollectionManager collectionManager) {
        super(new CommandDescription("add", "добавить новый элемент в коллекцию", true), collectionManager);
    }

    @Override
    public Response execute(Request request) {
        super.collectionManager.add(request.getMusicBand());
        return new Response();
    }
}
