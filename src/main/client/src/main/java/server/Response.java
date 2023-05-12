package server;

import data.MusicBand;

import java.util.ArrayList;

public class Response {
    private boolean exception;
    private String text;
    private ArrayList<MusicBand> musicBandAList;

    public Response(boolean exception, String text, ArrayList<MusicBand> musicBandAList){
        this.exception = exception;
        this.text = text;
        this.musicBandAList = musicBandAList;
    }

    public Response(){
        this(false, "", null);
    }

    public Response(String text){
        this(false, text, null);
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
