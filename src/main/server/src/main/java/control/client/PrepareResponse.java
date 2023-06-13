package control.client;

import control.CommandManager;
import control.Request;
import control.UserManager;
import control.response.AuthorizeResponse;
import control.response.CommandsListResponse;
import control.response.Response;
import data.User;

public class PrepareResponse implements Runnable {
    private final ClientPool clientPool;
    UserManager userManager;
    CommandManager commandManager;

    public PrepareResponse(ClientPool clientPool, UserManager userManager, CommandManager commandManager) {
        this.clientPool = clientPool;
        this.userManager = userManager;
        this.commandManager = commandManager;
    }

    public void run() {
        Client client = clientPool.getClientToPrepareResponse();
        Request request = client.getRequest();
        Response response = switch (request.getCommandName()) {
            case "authorize" -> new AuthorizeResponse(userManager.authorize((User) request.getArgument()));
            case "getCommands" -> new CommandsListResponse(commandManager.getCommandDescriptions());
            default -> this.commandManager.execute(request);
        };
        client.setResponse(response);
        clientPool.addClientToSendResponse(client);

    }
}
