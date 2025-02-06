package model;

import java.util.ArrayList;

public class User {
    private final int ID;
    private final ArrayList<ArrayList<String>> logins;
    private final byte[] keyLogin;


    public User(int ID, ArrayList<ArrayList<String>> logins, byte[] keyLogins) {
        this.ID = ID;
        this.logins = logins;
        this.keyLogin = keyLogins;
    }


    public int getID() {
        return this.ID;
    }


    public ArrayList<ArrayList<String>> getLogins() {
        return this.logins;
    }


    public byte[] getKeyLogin() {
        return keyLogin;
    }
}
