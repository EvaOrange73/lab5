package commands;

import control.CommandDescription;
import control.Request;
import control.response.CommandResponse;
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
    public CommandResponse execute(Request request) {
        ArrayList<MusicBand> musicBands = new ArrayList<>();
        for (MusicBand musicBand : super.collectionManager.getCollection())
            if (musicBand.getDescription().contains((CharSequence) request.getArgument()))
                musicBands.add(musicBand);
        return new CommandResponse(musicBands);
    }
}
