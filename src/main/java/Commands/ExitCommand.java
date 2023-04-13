package Commands;

import Control.CollectionManager;
import Control.IOManager;

public class ExitCommand extends CommandWithoutArguments {
    public ExitCommand(IOManager ioManager, CollectionManager collectionManager) {
        super("exit : завершить программу (без сохранения в файл)", ioManager, collectionManager);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
