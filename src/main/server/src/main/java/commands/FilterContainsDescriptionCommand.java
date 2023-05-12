package commands;

import DTO.Request;
import DTO.Response;
import control.CollectionManager;
import data.MusicBand;

import java.util.ArrayList;

public class FilterContainsDescriptionCommand extends Command {

    public FilterContainsDescriptionCommand(CollectionManager collectionManager) {
        super(new CommandDescription(
                        "filter_contains_description",
                        " description : вывести элементы, значение поля description которых содержит заданную подстроку",
                        "description",
                        "String"
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
