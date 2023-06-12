package commands;

import control.CommandDescription;
import control.Request;
import control.Response;
import control.CollectionManager;
import data.MusicBand;

public class AddCommand extends Command {

    public AddCommand(CollectionManager collectionManager) {
        super(new CommandDescription("add", "добавить новый элемент в коллекцию", true), collectionManager);
    }

    @Override
    public Response execute(Request request) {
        for (MusicBand musicBand: super.collectionManager.getCollection()){
            if (musicBand.equals(request.getMusicBand())) return new Response("В коллекции уже есть такой экземпляр.");
        }
        super.collectionManager.add(request.getMusicBand());
        return new Response();
    }
}
