package control.response;

import data.User;

import java.io.Serializable;

public class AuthorizeResponse extends Response implements Serializable {
    private final User user;

    public AuthorizeResponse(User user){
        super(false);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
