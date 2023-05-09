package commands;

import control.CollectionManager;
import control.IOManager;
import data.MusicBand;

public class FilterContainsDescriptionCommand extends CommandWithOneArgument {

    public FilterContainsDescriptionCommand(IOManager ioManager, CollectionManager collectionManager) {
        super("filter_contains_description", " description : вывести элементы, значение поля description которых содержит заданную подстроку",
                ioManager, collectionManager,
                "description", "String");
    }

    @Override
    public void execute(String argument) {
        for (MusicBand musicBand : super.collectionManager.getCollection())
            if (musicBand.getDescription().contains(argument))
                super.ioManager.print(musicBand.toString());

    }
}
