import IO.IOManager;
import commands.AskServerCommands;
import commands.CommandManager;
import control.CommandDescription;
import control.Request;
import server.ServerManager;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        IOManager ioManager = new IOManager(IOManager.Input.CONSOLE);
        ServerManager serverManager = new ServerManager(8080);
        CommandManager commandManager = new CommandManager(ioManager, serverManager);
        AskServerCommands askServerCommands = new AskServerCommands(serverManager, commandManager);
        commandManager.addCommands(new ArrayList<>(List.of(new CommandDescription[]{askServerCommands})));
        boolean isConnected = !(askServerCommands.execute(new Request("")).hasException());
        ioManager.setCommandManager(commandManager);
        ioManager.start(isConnected);
    }
}