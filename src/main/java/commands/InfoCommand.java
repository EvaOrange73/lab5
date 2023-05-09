package commands;

import control.CollectionManager;
import control.IOManager;

public class InfoCommand extends CommandWithoutArguments {


    public InfoCommand(IOManager ioManager, CollectionManager collectionManager) {
        super("info", " : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)",
                ioManager, collectionManager);
    }

    @Override
    public void execute() {
        super.ioManager.print("Тип коллекции: " + this.collectionManager.getType() + "\n" +
                "Дата инициализации: " + this.collectionManager.getCreationDate() + "\n" +
                "Количество элементов коллекции:" + this.collectionManager.getSize());
    }

}
