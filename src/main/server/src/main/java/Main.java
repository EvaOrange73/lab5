import control.ClientManager;
import control.CollectionManager;
import control.CommandManager;
import control.StartFileManager;
import exceptions.EnvException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            CollectionManager collectionManager = new CollectionManager();
            String startFileName = System.getenv("start_file");
            StartFileManager startFileManager = new StartFileManager(startFileName, collectionManager);
            startFileManager.readStartFile();
            ClientManager clientManager = new ClientManager(8080, new CommandManager(collectionManager, startFileManager));
            System.out.println("Сервер запущен");
            clientManager.connectClient();
        } catch (IOException e) {
            System.out.println("Стартовый файл не найден");
        } catch (EnvException e) {
            System.out.println(e.toString());
        }
    }
}