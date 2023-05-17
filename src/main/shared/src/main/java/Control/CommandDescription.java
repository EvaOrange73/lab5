package Control;

import data.description.Types;

import java.io.Serializable;

/**
 * Описание команды
 */
public class CommandDescription implements Serializable {
    private final String name;
    private final String argumentName;
    private final Types argumentType;
    private final boolean needMusicBand;
    private final String description;

    public CommandDescription(String name, String argumentName, Types argumentType, boolean needMusicBand, String description) {
        this.name = name;
        this.argumentName = argumentName;
        this.argumentType = argumentType;
        this.needMusicBand = needMusicBand;
        this.description = description;
    }

    public CommandDescription(String name, String description, boolean needMusicBand) {
        this(name, null, null, needMusicBand, description);
    }

    public CommandDescription(String name, String description, String argumentName, Types argumentType) {
        this(name, argumentName, argumentType, false, description);
    }

    public CommandDescription(String name, String description) {
        this(name, null, null, false, description);
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        String description = this.name;
        if (this.argumentName != null) description += " " + this.argumentName;
        if (this.needMusicBand) description += " {element}";
        description += " : ";
        description += this.description;
        return description;
    }

    public String getArgumentName() {
        return argumentName;
    }

    public Types getArgumentType() {
        return argumentType;
    }

    public boolean isNeedMusicBand() {
        return needMusicBand;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
