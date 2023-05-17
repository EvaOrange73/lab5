import IO.IOManager;
import commands.CommandManager;
import server.ServerManager;


public class Main {
    public static void main(String[] args) {
        IOManager ioManager = new IOManager(IOManager.Input.CONSOLE);
        boolean isConnected = true;
        ServerManager serverManager = new ServerManager(8080);
        CommandManager commandManager = new CommandManager(ioManager, serverManager);
        ioManager.setCommandManager(commandManager);
        ioManager.start(isConnected);
    }
}