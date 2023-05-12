package commands;

import DTO.Request;
import DTO.Response;
import control.CollectionManager;

import java.util.ArrayList;

public class ShowCommand extends Command {

    public ShowCommand(CollectionManager collectionManager) {
        super(new CommandDescription(
                        "show",
                        " : вывести в стандартный поток вывода все элементы коллекции в строковом представлении"),
                collectionManager);
    }

    @Override
    public Response execute(Request request) {
        return new Response(new ArrayList<>(this.collectionManager.getCollection()));
    }
}
