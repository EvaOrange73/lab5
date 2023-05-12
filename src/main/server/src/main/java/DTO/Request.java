package DTO;

import data.MusicBand;

public class Request {
    private String commandName;
    private Object argument;
    private MusicBand musicBand;

    public Request(String commandName){
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }

    public Object getArgument() {
        return argument;
    }

    public MusicBand getMusicBand() {
        return musicBand;
    }
}
