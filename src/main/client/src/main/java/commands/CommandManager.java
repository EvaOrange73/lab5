package commands;

import control.CommandDescription;
import IO.IOManager;
import IO.InputExceptions.ArgumentException;
import IO.InputExceptions.FieldsException;
import data.MusicBand;
import control.Request;
import control.response.CommandResponse;
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

    public CommandManager(IOManager ioManager, ServerManager serverManager) {
        this.ioManager = ioManager;
        this.serverManager = serverManager;
        ArrayList<CommandDescription> commands = new ArrayList<>(List.of(
                new HelpCommand(this),
                new ExitCommand(),
                new ExecuteScriptCommand(ioManager)
        ));
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

    public void addCommands(ArrayList<CommandDescription> serverCommands) {
        this.commands.putAll(serverCommands.stream().collect(
                LinkedHashMap::new,
                (map, item) -> map.put(item.getName(), item),
                (l, r) -> {
                    throw new IllegalStateException("combiner not needed here");
                }
        ));
    }

    /**
     * @param input команда, введенная пользователем
     * @return о
     */
    public String execute(String input, int userId) {
        String[] words = input.split(" ");
        String commandName = words[0];
        String argumentName = words.length == 1 ? null : words[1];
        CommandDescription commandDescription = commands.get(commandName);
        if (commandDescription == null) return "такой команды нет, \n" +
                "Посмотрите список доступных команд: help";

        CommandResponse commandResponse;

        if (commandDescription instanceof ClientCommand) {
            commandResponse = ((ClientCommand) commandDescription).execute(new Request(commandName, argumentName, userId));
            if (commandResponse.hasException()) return "сервер временно недоступен";
        }
        else {
            Request request;
            try {
                request = validateCommand(commandName, argumentName, commandDescription, userId);
            } catch (FieldsException e) {
                return e.toString();
            }
            if (request == null) return (new ArgumentException(
                    commandDescription.getArgumentName(),
                    commandDescription.getArgumentType().toString())
            ).toString();
            try {
                commandResponse = serverManager.request(request);
            } catch (IOException e) {
                return "сервер временно недоступен";
            }
            if (commandResponse.hasException()) return "при выполнении команды возникла ошибка";
        }
        StringBuilder answer = new StringBuilder(commandResponse.getText());
        if (commandResponse.getMusicBandAList() != null)
            for (MusicBand musicBand : commandResponse.getMusicBandAList()) {
                answer.append("\n").append(musicBand.toString());
            }
        answer.append("\n").append("команда ").append(commandName).append(" завершена");
        return answer.toString();
    }

    private Request validateCommand(String commandName, String argumentName, CommandDescription commandDescription, int userId) throws FieldsException {
        Request request = new Request(commandName, userId);

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
