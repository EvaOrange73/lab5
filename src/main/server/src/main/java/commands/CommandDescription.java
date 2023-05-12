package commands;

public class CommandDescription {
    private String name;
    private String description;
    private String argumentName;
    private String argumentType; //TODO enum
    private boolean needMusicBand;

    public CommandDescription(String name, String description, String argumentName, String argumentType, boolean needMusicBand) {
        this.name = name;
        this.description = description;
        this.argumentName = argumentName;
        this.argumentType = argumentType;
        this.needMusicBand = needMusicBand;
    }
    public CommandDescription(String name, String description, boolean needMusicBand) {
        this(name, description, null, null, needMusicBand);
    }
    public CommandDescription(String name, String description, String argumentName, String argumentType) {
        this(name, description, argumentName, argumentType, false);
    }
    public CommandDescription(String name, String description) {
        this(name, description, null, null, false);
    }

    public String getName() {
        return name;
    }
}
