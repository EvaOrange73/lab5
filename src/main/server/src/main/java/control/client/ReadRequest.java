package control.client;

import control.Request;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadRequest implements Runnable {
    ClientPool clientPool;

    public ReadRequest(ClientPool clientPool) {
        this.clientPool = clientPool;
    }

    public void run() {
        Client client = clientPool.getClientToReadRequest();
        try {
            ObjectInputStream in = new ObjectInputStream(client.getSocket().getInputStream());
            client.setRequest((Request) in.readObject());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }
        clientPool.addClientToPrepareResponse(client);

    }

}
