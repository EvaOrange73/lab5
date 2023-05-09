package control;

import commands.*;
import inputExceptions.InputException;
import inputExceptions.NoCommandException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Менеджер команд -- класс, отвечающий за вызов метода, выполняющего переданную команду
 */
public class CommandManager {
    /**
     * Словарь, сопоставляющий название команды и класс, отвечающий за её исполнение
     */
    private LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
    private final IOManager ioManager;

    public CommandManager(IOManager ioManager, CollectionManager collectionManager) {
        this.ioManager = ioManager;
        ArrayList<Command> commandsList = new ArrayList<>(List.of(
                new HelpCommand(ioManager, collectionManager, this),
                new InfoCommand(ioManager, collectionManager),
                new ShowCommand(ioManager, collectionManager),
                new AddCommand(ioManager, collectionManager),
                new UpdateCommand(ioManager, collectionManager),
                new RemoveByIdCommand(ioManager, collectionManager),
                new ClearCommand(ioManager, collectionManager),
                new SaveCommand(ioManager, collectionManager),
                new ExecuteScriptCommand(ioManager, collectionManager, this),
                new ExitCommand(ioManager, collectionManager),
                new AddIfMaxCommand(ioManager, collectionManager),
                new AddIfMinCommand(ioManager, collectionManager),
                new RemoveLowerCommand(ioManager, collectionManager),
                new RemoveAnyByDescriptionCommand(ioManager, collectionManager),
                new FilterContainsDescriptionCommand(ioManager, collectionManager),
                new printFieldDescendingGenge(ioManager, collectionManager))
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
    public LinkedHashMap<String, Command> getCommands() {
        return this.commands;
    }

    /**
     * Метод, обрабатывающий ошибки отсутствия вызванной команды или аргументов.
     *
     * @param str строка, содержащая команду и, возможно, аргумент
     */
    public void execute(String str) {
        String[] words = str.split(" ");
        try {
            Command commandExecutor = commands.get(words[0]);
            if (commandExecutor == null) throw new NoCommandException();

            if (words.length == 1)
                commandExecutor.execute();
            else
                commandExecutor.execute(words[1]);

            this.ioManager.print("Выполнение команды " + words[0] + " завершено");
        } catch (InputException e) {
            this.ioManager.print(e.toString());
        }
    }
}
