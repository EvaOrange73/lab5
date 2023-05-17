import IO.IOManager;
import commands.CommandManager;
import server.ServerException;
import server.ServerManager;


public class Main {
    public static void main(String[] args) {
        IOManager ioManager = new IOManager(IOManager.Input.CONSOLE);
        ServerManager serverManager = null;
        boolean isConnected = true;
        try {
             serverManager = new ServerManager(8080);
        } catch (ServerException e) {
            isConnected = false;
        }
        CommandManager commandManager = new CommandManager(ioManager, serverManager);
        ioManager.setCommandManager(commandManager);
        ioManager.start(isConnected);
    }
}