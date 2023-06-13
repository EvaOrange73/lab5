package control.client;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class SendResponse implements Runnable {
    ClientPool clientPool;

    public SendResponse(ClientPool clientPool) {
        this.clientPool = clientPool;
    }

    public void run() {
        Client client = clientPool.getClientToSendResponse();
        try {
            ObjectOutputStream out = new ObjectOutputStream(client.getSocket().getOutputStream());
            out.writeObject(client.getResponse());
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
