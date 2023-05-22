package commands;

import control.Request;
import control.Response;
import server.ServerManager;

import java.io.IOException;

public class AskServerCommands extends ClientCommand {
    ServerManager serverManager;
    CommandManager commandManager;

    public AskServerCommands(ServerManager serverManager, CommandManager commandManager) {
        super("ask_server_commands", "", null, "обновить список доступных команд");
        this.serverManager = serverManager;
        this.commandManager = commandManager;
    }

    @Override
    public Response execute(Request request) {
        try {
            commandManager.addCommands(serverManager.askCommands());
            return new Response();
        } catch (IOException e) {
            return new Response(true);
        }
    }
}
