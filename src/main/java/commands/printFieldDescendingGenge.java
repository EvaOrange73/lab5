package commands;

import control.CollectionManager;
import control.IOManager;
import data.MusicBand;
import data.MusicGenre;

import java.util.ArrayList;

public class printFieldDescendingGenge extends CommandWithoutArguments {

    public printFieldDescendingGenge(IOManager ioManager, CollectionManager collectionManager) {
        super("print_field_descending_genre", " : вывести значения поля genre всех элементов в порядке убывания",
                ioManager, collectionManager);
    }

    @Override
    public void execute() {
        ArrayList<MusicGenre> genres = new ArrayList<>();
        for (MusicBand musicBand : super.collectionManager.getCollection()) {
            genres.add(musicBand.getGenre());
        }
        genres.sort(MusicGenre::compareTo);
        for (MusicGenre genre : genres)
            super.ioManager.print(genre.toString());
    }

}
