package control;

import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Поток, который завершится при вводе "exit" в консоль
 */
public class ConsoleManager implements Runnable{
    @Override
    public void run() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        while (true){
            String command = scanner.nextLine();
            if (command.equals("exit")) break;
        }
    }
}
