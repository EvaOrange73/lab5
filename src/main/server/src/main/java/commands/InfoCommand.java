package commands;

import control.CommandDescription;
import control.Request;
import control.response.CommandResponse;
import control.CollectionManager;

public class InfoCommand extends Command {

    public InfoCommand(CollectionManager collectionManager) {
        super(new CommandDescription(
                "info",
                "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)"
        ), collectionManager);
    }

    @Override
    public CommandResponse execute(Request request) {
        return new CommandResponse("Тип коллекции: " + this.collectionManager.getType() + "\n" +
                "Дата инициализации: " + this.collectionManager.getCreationDate() + "\n" +
                "Количество элементов коллекции:" + this.collectionManager.getSize());
    }
}
