package commands;

import Control.CommandDescription;
import Control.Request;
import Control.Response;
import control.CollectionManager;
import data.MusicBand;

import java.util.LinkedHashSet;

public class RemoveLowerCommand extends Command {

    public RemoveLowerCommand(CollectionManager collectionManager) {
        super(new CommandDescription("remove_lower", " {element} : удалить из коллекции все элементы, меньшие, чем заданный",
                true), collectionManager);
    }

    @Override
    public Response execute(Request request) {
        MusicBand musicBandReference = request.getMusicBand();
        LinkedHashSet<MusicBand> musicBands = new LinkedHashSet<>(super.collectionManager.getCollection());
        for (MusicBand musicBand : musicBands) {
            if (musicBand.compareTo(musicBandReference) < 0) {
                super.collectionManager.remove(musicBand);
            }
        }
        return new Response();
    }

}
