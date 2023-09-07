package control;

import data.MusicBand;
import data.User;

import java.io.Serializable;

/**
 * Формат запросов к серверу
 */
public class Request implements Serializable {
    private final String commandName;
    private Object argument;
    private MusicBand musicBand;
    private final User user;

    public Request(String commandName, Object argument, User user) {
        this.commandName = commandName;
        this.argument = argument;
        this.user = user;
    }

    public Request(String commandName, User user) {
        this(commandName, null, user);
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

    public User getUser() {
        return this.user;
    }
}
