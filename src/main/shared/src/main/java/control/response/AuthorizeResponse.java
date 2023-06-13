package control.response;

import java.io.Serializable;

public class AuthorizeResponse extends Response implements Serializable {
    private final int userId;

    public AuthorizeResponse(int userId){
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}
