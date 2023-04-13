package Commands;

import Control.CollectionManager;
import Control.CommandManager;
import Control.IOManager;
import Control.ScriptManager;
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
        IOManager previousIOManager = super.ioManager;
        try {
            ScriptManager scriptManager = new ScriptManager(
                    argument,
                    previousIOManager.getStartFileManager(),
                    super.collectionManager,
                    this.commandManager,
                    previousIOManager.getRecursionDepth() + 1
            );
            this.commandManager.setIoManagers(scriptManager);
            scriptManager.executeFile();
            this.commandManager.setIoManagers(previousIOManager);
        } catch (FileNotFoundException e) {
            super.ioManager.print("файл не найден");
        } catch (RecursionException e) {
            super.ioManager.print(e.toString());
        }
    }
}
