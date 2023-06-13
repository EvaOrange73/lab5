package commands;

import control.CommandDescription;
import data.description.Types;
import control.Request;
import control.response.CommandResponse;

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

    public abstract CommandResponse execute(Request request);
}
