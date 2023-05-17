import control.ClientManager;
import control.CollectionManager;
import control.CommandManager;

public class Main {
    public static void main(String[] args){
        ClientManager clientManager = new ClientManager(8080, new CommandManager(new CollectionManager()));
        clientManager.start();
    }
}