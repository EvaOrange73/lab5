package commands;

import data.description.Types;
import server.Request;
import server.Response;

public abstract class ClientCommand extends CommandDescription {
    public ClientCommand(String name, String argumentName, Types argumentType, String description) {
        super(name, argumentName, argumentType, false, description);
    }

    public ClientCommand(String name, String description) {
        super(name, null, null, false, description);
    }

    public abstract Response execute(Request request);
}
