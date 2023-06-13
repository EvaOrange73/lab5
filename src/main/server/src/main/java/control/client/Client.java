package control.client;

import control.Request;
import control.response.Response;

import java.net.Socket;

public class Client {
    Socket socket;
    Request request;
    Response response;

    public Client(Socket socket){
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public Request getRequest() {
        return request;
    }

    public Response getResponse() {
        return response;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
