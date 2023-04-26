import Control.CollectionManager;
import Control.CommandManager;
import Control.IOManager;
import Control.StartFileManager;

public class Main {
    public static void main(String[] args) {
        String startFileName = "";
        if (args.length > 0) startFileName = args[0];
        StartFileManager startFileManager = new StartFileManager(startFileName);
        CollectionManager collectionManager = new CollectionManager();
        IOManager ioManager = new IOManager(collectionManager, startFileManager, IOManager.Input.CONSOLE);
        CommandManager commandManager = new CommandManager(ioManager, collectionManager);
        ioManager.setCommandManager(commandManager);
        ioManager.start();
    }
}
