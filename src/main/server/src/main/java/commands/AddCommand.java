package commands;

import DTO.Request;
import DTO.Response;
import control.CollectionManager;

public class AddCommand extends Command {

    public AddCommand(CollectionManager collectionManager) {
        super(new CommandDescription("add", " {element} : добавить новый элемент в коллекцию", true), collectionManager);
    }

    @Override
    public Response execute(Request request) {
        super.collectionManager.add(request.getMusicBand());
        return new Response();
    }
}
