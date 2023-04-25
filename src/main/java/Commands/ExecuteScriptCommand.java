package Commands;

import Control.CollectionManager;
import Control.CommandManager;
import Control.IOManager;
import InputExceptions.RecursionException;

import java.io.FileNotFoundException;

public class ExecuteScriptCommand extends CommandWithOneArgument {

    private CommandManager commandManager;

    public ExecuteScriptCommand(IOManager ioManager, CollectionManager collectionManager, CommandManager commandManager) {
        super("execute_script file_name : считать и исполнить скрипт из указанного файла. " +
                        "В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.",
                ioManager, collectionManager,
                "Имя файла", "String");
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String argument) {
        super.ioManager.startExecuteScript(argument);
    }
}
