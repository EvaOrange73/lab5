package commands;

import control.Request;
import control.response.CommandResponse;

public class ExitCommand extends ClientCommand {
    public ExitCommand() {
        super("exit", "завершить программу (без сохранения в файл)");
    }

    @Override
    public CommandResponse execute(Request request) {
        System.exit(0);
        return new CommandResponse();
    }
}
