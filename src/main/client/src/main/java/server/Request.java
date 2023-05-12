package server;

import data.MusicBand;

public class Request {
    private String commandName;
    private Object argument;
    private MusicBand musicBand;


    public Request(String commandName, String argument) {
        this.commandName = commandName;
        this.argument = argument;
    }

    public Request(String commandName) {
        this(commandName, null);
    }


    public void setArgument(Object argument) {
        this.argument = argument;
    }

    public Object getArgument() {
        return argument;
    }

    public void setMusicBand(MusicBand musicBand) {
        this.musicBand = musicBand;
    }

    @Override
    public String toString() {
        return super.toString(); //TODO
    }
}
