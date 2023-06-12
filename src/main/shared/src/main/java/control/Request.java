package control;

import data.MusicBand;

import java.io.Serializable;

/**
 * Формат запросов к серверу
 */
public class Request implements Serializable {
    private final String commandName;
    private Object argument;
    private MusicBand musicBand;
    private final Integer userID;

    public Request(String commandName, Object argument, Integer userID) {
        this.commandName = commandName;
        this.argument = argument;
        this.userID = userID;
    }

    public Request(String commandName, Integer userID) {
        this(commandName, null, userID);
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

    public String getCommandName() {
        return this.commandName;
    }

    public MusicBand getMusicBand() {
        return this.musicBand;
    }

    public int getUserID() {
        return userID;
    }
}
