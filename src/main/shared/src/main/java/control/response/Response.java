package control.response;

import java.io.Serializable;

public class Response implements Serializable {
    boolean authorizeError;

    public Response(boolean authorizeError) {
        this.authorizeError = authorizeError;
    }
}
