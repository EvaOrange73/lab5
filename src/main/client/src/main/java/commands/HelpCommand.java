package commands;


import control.CommandDescription;
import control.Request;
import control.Response;

public class HelpCommand extends ClientCommand {
    private final CommandManager commandManager;

    public HelpCommand(CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
        this.commandManager = commandManager;
    }

    @Override
    public Response execute(Request request) {
        StringBuilder response = new StringBuilder();
        for (CommandDescription command : this.commandManager.getCommands().values()) {
            response.append(command.getDescription()).append("\n");
        }
        return new Response(response.toString());
    }
}
