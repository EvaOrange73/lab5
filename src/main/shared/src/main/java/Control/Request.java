package Control;

import data.MusicBand;

/**
 * Формат запросов к серверу
 */
public class Request {
    private final String commandName;
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

    public String getCommandName() {
        return this.commandName;
    }

    public MusicBand getMusicBand() {
        return this.musicBand;
    }
}
