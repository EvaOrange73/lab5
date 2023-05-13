package commands;

import Control.CommandDescription;
import Control.Request;
import Control.Response;
import control.CollectionManager;
import data.MusicBand;
import data.MusicGenre;

import java.util.ArrayList;

public class PrintFieldDescendingGenge extends Command {

    public PrintFieldDescendingGenge(CollectionManager collectionManager) {
        super(new CommandDescription(
                "print_field_descending_genre",
                " : вывести значения поля genre всех элементов в порядке убывания"
        ), collectionManager);
    }

    @Override
    public Response execute(Request request) {
        ArrayList<MusicGenre> genres = new ArrayList<>();
        for (MusicBand musicBand : super.collectionManager.getCollection()) {
            genres.add(musicBand.getGenre());
        }
        genres.sort(MusicGenre::compareTo);
        String genresText = "";
        for (MusicGenre genre : genres)
            genresText += genre.toString();
        return new Response(genresText);
    }

}
