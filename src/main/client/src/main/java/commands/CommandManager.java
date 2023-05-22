package commands;

import control.CommandDescription;
import IO.IOManager;
import IO.InputExceptions.ArgumentException;
import IO.InputExceptions.FieldsException;
import data.MusicBand;
import control.Request;
import control.Response;
import server.ServerManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Менеджер команд -- класс, отвечающий за
 * вызов метода, выполняющего переданную команду,
 * или формирование запроса на сервер.
 */
public class CommandManager {
    private final HashMap<String, CommandDescription> commands;
    private final IOManager ioManager;
    private final ServerManager serverManager;

    /**
     * @param serverManager - менеджер сервера
     */
    public CommandManager(IOManager ioManager, ServerManager serverManager) {
        this.ioManager = ioManager;
        this.serverManager = serverManager;
        ArrayList<CommandDescription> commands = new ArrayList<>(List.of(
                new HelpCommand(this),
                new ExitCommand(),
                new ExecuteScriptCommand(ioManager)
        ));
        if (serverManager != null) {
            try {
                commands.addAll(serverManager.askCommands());
            } catch (IOException e) {
                ioManager.print("сервер временно недоступен"); //TODO
            }
        }
        this.commands = commands.stream().collect(
                LinkedHashMap::new,
                (map, item) -> map.put(item.getName(), item),
                (l, r) -> {
                    throw new IllegalStateException("combiner not needed here");
                }
        );

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
            try {
                response = serverManager.request(request);
            } catch (IOException e) {
                return "сервер временно недоступен";
            }
            if (response.hasException()) return "при выполнении команды возникла ошибка";
        }
        StringBuilder answer = new StringBuilder(response.getText());
        if (response.getMusicBandAList() != null)
            for (MusicBand musicBand : response.getMusicBandAList()) {
                answer.append("\n").append(musicBand.toString());
            }
        answer.append("\n").append("команда ").append(commandName).append(" завершена");
        return answer.toString();
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
