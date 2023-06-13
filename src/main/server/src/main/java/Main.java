import control.*;
import control.client.ClientManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


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
            Scanner scanner = new Scanner(new InputStreamReader(System.in));
            while (true) {
                while (System.in.available() == 0) {
                    clientManager.connectClient();
                }
                String command = scanner.nextLine();
                if (command.equals("exit")) break;
            }
            clientManager.close();

        } catch (IOException e) {
            System.out.println("Стартовый файл не найден");
        }
    }
}