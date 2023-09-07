package control.client;

import java.util.LinkedList;
import java.util.Queue;

public class ClientPool {
    Queue<Client> toReadRequest;
    Queue<Client> toPrepareResponse;
    Queue<Client> toSendResponse;

    public ClientPool(){
        toReadRequest = new LinkedList<>();
        toPrepareResponse = new LinkedList<>();
        toSendResponse = new LinkedList<>();
    }

    public synchronized void addClientToReadRequest(Client client){
        toReadRequest.add(client);
        notifyAll();
    }

    public synchronized void addClientToPrepareResponse(Client client){
        toPrepareResponse.add(client);
        notifyAll();
    }

    public synchronized void addClientToSendResponse(Client client){
        toSendResponse.add(client);
        notifyAll();
    }

    public synchronized Client getClientToReadRequest(){
        while (this.toReadRequest.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
        return toReadRequest.remove();
    }

    public synchronized Client getClientToPrepareResponse(){
        while (this.toPrepareResponse.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
        return toPrepareResponse.remove();
    }

    public synchronized Client getClientToSendResponse(){
        while (this.toSendResponse.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
        return toSendResponse.remove();
    }

}
