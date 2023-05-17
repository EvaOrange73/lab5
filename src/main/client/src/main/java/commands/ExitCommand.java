package commands;

import сontrol.Request;
import сontrol.Response;

public class ExitCommand extends ClientCommand {
    public ExitCommand() {
        super("exit", "завершить программу (без сохранения в файл)");
    }

    @Override
    public Response execute(Request request) {
        System.exit(0);
        return new Response();
    }
}
