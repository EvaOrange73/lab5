import control.ClientManager;
import control.CollectionManager;
import control.CommandManager;
import control.StartFileManager;
import exceptions.EnvException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            CollectionManager collectionManager = new CollectionManager();
            String startFileName = System.getenv("start_file");
            StartFileManager startFileManager = new StartFileManager(startFileName, collectionManager);
            startFileManager.readStartFile();
            ClientManager clientManager = new ClientManager(9000, new CommandManager(collectionManager));

            System.out.println("Сервер запущен");
            Scanner scanner = new Scanner(new InputStreamReader(System.in));
            while (true) {
                while (System.in.available() == 0) {
                    clientManager.connectClient();
                }
                String command = scanner.nextLine();
                if (command.equals("exit")) break;
                if (command.equals("save")) startFileManager.save();
            }
            startFileManager.save();
            clientManager.close();

        } catch (IOException e) {
            System.out.println("Стартовый файл не найден");
        } catch (EnvException e) {
            System.out.println(e.toString());
        }
    }
}