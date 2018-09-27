package co.esprit.bemymenuapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Bassem on 14/11/2016.
 */

public class User {


    @SerializedName("id")
    private String id;

    @SerializedName("login")
    private String login;
    @SerializedName("password")
    private String password;

    @SerializedName("flagRole")
    private String flagRole;

    public User(String id, String login, String password, String flagRole) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.flagRole = flagRole;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFlagRole() {
        return flagRole;
    }

    public void setFlagRole(String flagRole) {
        this.flagRole = flagRole;
    }


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", flagRole='" + flagRole + '\'' +
                '}';
    }
}
