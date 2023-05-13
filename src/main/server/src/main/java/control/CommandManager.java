package control;

import DTO.Request;
import DTO.Response;
import commands.*;

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
    private LinkedHashMap<String, Command> commands = new LinkedHashMap<>();

    public CommandManager(CollectionManager collectionManager) {
        ArrayList<Command> commandsList = new ArrayList<>(List.of(
                new InfoCommand(collectionManager),
                new ShowCommand(collectionManager),
                new AddCommand(collectionManager),
                new UpdateCommand(collectionManager),
                new RemoveByIdCommand(collectionManager),
                new ClearCommand(collectionManager),
                new AddIfMaxCommand(collectionManager),
                new AddIfMinCommand(collectionManager),
                new RemoveLowerCommand(collectionManager),
                new RemoveAnyByDescriptionCommand(collectionManager),
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
    public ArrayList<Command> getCommands() {
        return (ArrayList<Command>) this.commands.values();
    } //TODO это ок?

    /**
     * Метод, вызывающий исполнение команды
     * @param request запрос от клиента
     * @return ответ от сервера
     */
    public Response execute(Request request) {
        Command commandExecutor = commands.get(request.getCommandName());
        return commandExecutor.execute(request);
    }
}
