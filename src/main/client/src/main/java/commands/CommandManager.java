package commands;

import Control.CommandDescription;
import IO.IOManager;
import IO.InputExceptions.ArgumentException;
import IO.InputExceptions.FieldsException;
import data.MusicBand;
import Control.Request;
import Control.Response;
import server.ServerManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Менеджер команд -- класс, отвечающий за
 * вызов метода, выполняющего переданную команду,
 * или формирование запроса на сервер.
 *
 */
public class CommandManager {
    private HashMap<String, CommandDescription> commands;
    private IOManager ioManager;
    private ServerManager serverManager;

    /**
     * @param serverCommands - список команд, пришедших с сервера
     * @param serverManager - менеджер сервера
     */
    public CommandManager(ArrayList<CommandDescription> serverCommands, ServerManager serverManager) {
        ArrayList<CommandDescription> commands = new ArrayList<>(List.of(
                new HelpCommand(this),
                new ExitCommand(),
                new ExecuteScriptCommand(ioManager, this)
        ));
        commands.addAll(serverCommands);
        this.commands = commands.stream().collect(
                LinkedHashMap::new,
                (map, item) -> map.put(item.getName(), item),
                (l, r) -> {
                    throw new IllegalStateException("combiner not needed here");
                }
        );
        this.serverManager = serverManager;
    }

    /**
     * @return список доступных команд для help
     */
    public HashMap<String, CommandDescription> getCommands() {
        return commands;
    }

    /**
     * @param input команда, введенная пользователем
     * @return о
     */
    public String execute(String input) {
        String[] words = input.split(" ");
        String commandName = words[0];
        String argumentName = words.length == 1 ? null : words[1];
        CommandDescription commandDescription = commands.get(commandName);
        if (commandDescription == null) return "такой команды нет, \n" +
                "Посмотрите список доступных команд: help";

        Response response;

        if (commandDescription instanceof ClientCommand)
            response = ((ClientCommand) commandDescription).execute(new Request(commandName, argumentName));
        else {
            Request request;
            try {
                request = validateCommand(commandName, argumentName, commandDescription);
            } catch (FieldsException e) {
                return e.toString();
            }
            if (request == null) return (new ArgumentException(
                    commandDescription.getArgumentName(),
                    commandDescription.getArgumentType().toString())
            ).toString();
            response = serverManager.request(request);
            if (response.hasException()) return "при выполнении команды возникла ошибка";
        }
        String answer = response.getText() + "\n";
        for (MusicBand musicBand : response.getMusicBandAList()) {
            answer += musicBand.toString() + "\n";
        }
        return answer;
    }

    private Request validateCommand(String commandName, String argumentName, CommandDescription commandDescription) throws FieldsException {
        Request request = new Request(commandName);

        if (commandDescription.getArgumentName() != null) {
            if (argumentName == null)
                return null;
            Object argument = commandDescription.getArgumentType().validateType(argumentName);
            if (argument == null)
                return null;
            request.setArgument(argument);
        }

        if (commandDescription.isNeedMusicBand()) {
            request.setMusicBand(this.ioManager.askMusicBand());
        }

        return request;
    }
}
