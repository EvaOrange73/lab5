package commands;

import Control.CommandDescription;
import Control.Request;
import Control.Response;
import control.CollectionManager;
import data.MusicBand;
import data.description.Types;

import java.util.ArrayList;

public class FilterContainsDescriptionCommand extends Command {

    public FilterContainsDescriptionCommand(CollectionManager collectionManager) {
        super(new CommandDescription(
                        "filter_contains_description",
                        "вывести элементы, значение поля description которых содержит заданную подстроку",
                        "description",
                        Types.STRING
                ),
                collectionManager);
    }

    @Override
    public Response execute(Request request) {
        ArrayList<MusicBand> musicBands = new ArrayList<>();
        for (MusicBand musicBand : super.collectionManager.getCollection())
            if (musicBand.getDescription().contains((CharSequence) request.getArgument()))
                musicBands.add(musicBand);
        return new Response(musicBands);
    }
}
