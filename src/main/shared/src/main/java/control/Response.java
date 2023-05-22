package control;

import data.MusicBand;
import data.description.MusicBandByCoordsComparator;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Формат ответов от сервера
 */
public class Response implements Serializable {
    private final boolean exception;
    private final String text;
    private final ArrayList<MusicBand> musicBandAList;

    public Response(boolean exception, String text, ArrayList<MusicBand> musicBandAList) {
        this.exception = exception;
        this.text = text;
        if (musicBandAList != null) musicBandAList.sort(new MusicBandByCoordsComparator());
        this.musicBandAList = musicBandAList;
    }

    public Response() {
        this(false, "", null);
    }

    public Response(String text) {
        this(false, text, null);
    }

    public Response(ArrayList<MusicBand> musicBandAList) {
        this(false, "", musicBandAList);
    }

    public Response(boolean exception){
        this(exception, "", null);
    }

    public boolean hasException() {
        return exception;
    }

    public String getText() {
        return text;
    }

    public ArrayList<MusicBand> getMusicBandAList() {
        return musicBandAList;
    }
}
