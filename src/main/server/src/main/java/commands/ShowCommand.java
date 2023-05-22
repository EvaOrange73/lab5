package commands;

import control.CommandDescription;
import control.Request;
import control.Response;
import control.CollectionManager;
import data.MusicBand;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class ShowCommand extends Command {

    public ShowCommand(CollectionManager collectionManager) {
        super(new CommandDescription(
                        "show",
                        "вывести в стандартный поток вывода все элементы коллекции в строковом представлении"),
                collectionManager);
    }

    @Override
    public Response execute(Request request) {
        LinkedHashSet<MusicBand> collection = this.collectionManager.getCollection();
        if (collection.isEmpty()) return new Response("Коллекция пуста");
        return new Response(new ArrayList<>(collection));
    }
}
