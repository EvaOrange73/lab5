package control.response;

import data.MusicBand;
import data.description.MusicBandByCoordsComparator;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Формат ответов от сервера
 */
public class CommandResponse extends Response implements Serializable {
    private final boolean exception;
    private final String text;
    private final ArrayList<MusicBand> musicBandAList;

    public CommandResponse(boolean exception, String text, ArrayList<MusicBand> musicBandAList) {
        super(false);
        this.exception = exception;
        this.text = text;
        if (musicBandAList != null) musicBandAList.sort(new MusicBandByCoordsComparator());
        this.musicBandAList = musicBandAList;
    }

    public CommandResponse() {
        this(false, "", null);
    }

    public CommandResponse(String text) {
        this(false, text, null);
    }

    public CommandResponse(ArrayList<MusicBand> musicBandAList) {
        this(false, "", musicBandAList);
    }

    public CommandResponse(boolean exception) {
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
