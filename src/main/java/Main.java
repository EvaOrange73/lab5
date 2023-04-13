import Control.CollectionManager;
import Control.ConsoleManager;

public class Main {
    public static void main(String[] args) {
        String startFileName = "";
        if(args.length > 0) startFileName = args[0];
        ConsoleManager consoleManager = new ConsoleManager(startFileName, new CollectionManager());
        consoleManager.start();
    }
}
