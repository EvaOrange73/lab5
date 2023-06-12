import IO.IOManager;
import commands.CommandManager;
import data.User;
import server.ServerManager;



public class Main {
    public static void main(String[] args) {
        ServerManager serverManager = new ServerManager(9000);
        IOManager ioManager = new IOManager(serverManager);
        CommandManager commandManager = new CommandManager(ioManager, serverManager);
        ioManager.setCommandManager(commandManager);
        User user = ioManager.authorize();
        ioManager.setUser(user);
        if (user != null) {
            commandManager.addCommands(serverManager.askCommands(user.getId()));
            ioManager.printStartMessage();
            ioManager.readCommands();
        }
    }
}