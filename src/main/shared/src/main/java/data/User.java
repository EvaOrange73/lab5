package data;

import data.description.Data;
import data.description.FieldAnnotation;

import java.io.Serializable;

public class User extends Data implements Serializable {
    @FieldAnnotation(name = "id", ColumnName = "id", printable = false, DBSets = true)
    private Integer id;
    @FieldAnnotation(name = "имя пользователя", ColumnName = "username")
    private String username;
    @FieldAnnotation(name = "пароль", ColumnName = "password", printable = false)
    private String password;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}
