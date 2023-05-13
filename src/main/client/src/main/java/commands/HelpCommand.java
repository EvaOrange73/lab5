package commands;


import Control.CommandDescription;
import Control.Request;
import Control.Response;

public class HelpCommand extends ClientCommand {
    private final CommandManager commandManager;

    public HelpCommand(CommandManager commandManager) {
        super("help", " : вывести справку по доступным командам");
        this.commandManager = commandManager;
    }

    @Override
    public Response execute(Request request) {
        String response = "";
        for (CommandDescription command : this.commandManager.getCommands().values()) {
            response += command.getDescription();
        }
        return new Response(response);
    }

}
