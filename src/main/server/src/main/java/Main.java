import control.*;
import control.client.ClientManager;

import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        try {
            DatabaseManager databaseManager = new DatabaseManager();
            CollectionManager collectionManager = new CollectionManager(databaseManager);
            UserManager userManager = new UserManager(databaseManager, collectionManager);
            collectionManager.setCollection(databaseManager.getCollection());
            CommandManager commandManager = new CommandManager(collectionManager, userManager);
            ClientManager clientManager = new ClientManager(9000, commandManager, userManager);

            System.out.println("Сервер запущен");
            Thread console = new Thread(new ConsoleManager());
            console.start();
            while (console.isAlive()) {
                clientManager.connectClient();
            }
            clientManager.close();

        } catch (IOException e) {
            System.out.println("Стартовый файл не найден");
        }
    }
}