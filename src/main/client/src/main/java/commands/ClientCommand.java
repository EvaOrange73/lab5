package commands;

import Control.CommandDescription;
import data.description.Types;
import Control.Request;
import Control.Response;

/**
 * Команды, которые клиент выполняет самостоятельно, не спрашивая сервер: help, exit, execute_script
 */
public abstract class ClientCommand extends CommandDescription {
    public ClientCommand(String name, String argumentName, Types argumentType, String description) {
        super(name, argumentName, argumentType, false, description);
    }

    public ClientCommand(String name, String description) {
        super(name, null, null, false, description);
    }

    public abstract Response execute(Request request);
}
