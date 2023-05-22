package commands;

import IO.IOManager;
import data.description.Types;
import control.Request;
import control.Response;

public class ExecuteScriptCommand extends ClientCommand {

    private final IOManager ioManager;

    public ExecuteScriptCommand(IOManager ioManager) {
        super("execute_script",
                "file_name",
                Types.STRING,
                "считать и исполнить скрипт из указанного файла. " +
                        "В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме."

        );
        this.ioManager = ioManager;
    }

    @Override
    public Response execute(Request request) {
        this.ioManager.startExecuteScript((String) request.getArgument());
        return new Response();
    }
}
