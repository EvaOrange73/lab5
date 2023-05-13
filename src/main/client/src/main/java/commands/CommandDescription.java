package commands;

import data.description.Types;

/**
 * Описание команды
 */
public class CommandDescription {
    private String name;
    private String argumentName;
    private Types argumentType; //TODO enum
    private boolean needMusicBand;
    private String description;

    public CommandDescription(String name, String argumentName, Types argumentType, boolean needMusicBand, String description) {
        this.name = name;
        this.argumentName = argumentName;
        this.argumentType = argumentType;
        this.needMusicBand = needMusicBand;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    } //TODO argument name

    public String getArgumentName() {
        return argumentName;
    }

    public Types getArgumentType() {
        return argumentType;
    }

    public boolean isNeedMusicBand() {
        return needMusicBand;
    }
}
