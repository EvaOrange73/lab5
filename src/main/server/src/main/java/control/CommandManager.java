package control;

import commands.*;
import control.response.CommandResponse;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Менеджер команд -- класс, отвечающий за вызов метода, выполняющего переданную команду
 */
public class CommandManager {
    /**
     * Словарь, сопоставляющий название команды и класс, отвечающий за её исполнение
     */
    private final LinkedHashMap<String, Command> commands;

    public CommandManager(CollectionManager collectionManager, UserManager userManager) {
        ArrayList<Command> commandsList = new ArrayList<>(List.of(
                new InfoCommand(collectionManager),
                new ShowCommand(collectionManager),
                new AddCommand(collectionManager),
                new UpdateCommand(collectionManager, userManager),
                new RemoveByIdCommand(collectionManager, userManager),
                new ClearCommand(collectionManager, userManager),
                new AddIfMaxCommand(collectionManager),
                new AddIfMinCommand(collectionManager),
                new RemoveLowerCommand(collectionManager, userManager),
                new RemoveAnyByDescriptionCommand(collectionManager, userManager),
                new FilterContainsDescriptionCommand(collectionManager),
                new PrintFieldDescendingGenge(collectionManager))
        );
        commands = commandsList.stream().collect(
                LinkedHashMap::new,
                (map, item) -> map.put(item.getName(), item),
                (l, r) -> {
                    throw new IllegalStateException("combiner not needed here");
                }
        );
    }

    /**
     * @return список команд, которые умеет обрабатывать программа
     */
    public ArrayList<CommandDescription> getCommandDescriptions() {
        ArrayList<CommandDescription> commandDescriptions = new ArrayList<>();
        for (Command command : this.commands.values()) {
            commandDescriptions.add(command.getDescription());
        }
        return commandDescriptions;
    }

    /**
     * Метод, вызывающий исполнение команды
     *
     * @param request запрос от клиента
     * @return ответ от сервера
     */
    public CommandResponse execute(Request request) {
        return commands.get(request.getCommandName()).execute(request);
    }
}
