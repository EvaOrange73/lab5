import control.CollectionManager;
import control.CommandManager;
import control.IOManager;
import control.StartFileManager;

public class Main {
    public static void main(String[] args) {
        String startFileName = System.getenv("start_file");
        StartFileManager startFileManager = new StartFileManager(startFileName);
        CollectionManager collectionManager = new CollectionManager();
        IOManager ioManager = new IOManager(collectionManager, startFileManager, IOManager.Input.CONSOLE);
        CommandManager commandManager = new CommandManager(ioManager, collectionManager);
        ioManager.setCommandManager(commandManager);
        ioManager.start();
    }
}
