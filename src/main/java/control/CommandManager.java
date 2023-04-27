package control;

import commands.*;
import inputExceptions.InputException;
import inputExceptions.NoCommandException;

import java.util.LinkedHashMap;

/**
 * Менеджер команд -- класс, отвечающий за вызов метода, выполняющего переданную команду
 */
public class CommandManager {
    /**
     * Словарь, сопоставляющий название команды и класс, отвечающий за её исполнение
     */
    private final LinkedHashMap<String, Command> commands;
    private final IOManager ioManager;

    public CommandManager(IOManager ioManager, CollectionManager collectionManager) {
        this.ioManager = ioManager;
        commands = new LinkedHashMap<>();
        commands.put("help", new HelpCommand(ioManager, collectionManager, this));
        commands.put("info", new InfoCommand(ioManager, collectionManager));
        commands.put("show", new ShowCommand(ioManager, collectionManager));
        commands.put("add", new AddCommand(ioManager, collectionManager));
        commands.put("update", new UpdateCommand(ioManager, collectionManager));
        commands.put("remove_by_id", new RemoveByIdCommand(ioManager, collectionManager));
        commands.put("clear", new ClearCommand(ioManager, collectionManager));
        commands.put("save", new SaveCommand(ioManager, collectionManager));
        commands.put("execute_script", new ExecuteScriptCommand(ioManager, collectionManager, this));
        commands.put("exit", new ExitCommand(ioManager, collectionManager));
        commands.put("add_if_max", new AddIfMaxCommand(ioManager, collectionManager));
        commands.put("add_if_min", new AddIfMinCommand(ioManager, collectionManager));
        commands.put("remove_lower", new RemoveLowerCommand(ioManager, collectionManager));
        commands.put("remove_any_by_description", new RemoveAnyByDescriptionCommand(ioManager, collectionManager));
        commands.put("filter_contains_description", new FilterContainsDescriptionCommand(ioManager, collectionManager));
        commands.put("print_field_ascending_best_album", new printFieldAscendingBestAlbumCommand(ioManager, collectionManager));
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

            this.ioManager.print("Команда " + words[0] + " выполнена");
        } catch (InputException e) {
            System.out.println(e.toString());
        }
    }
}
