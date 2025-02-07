package Session;

import model.User;
import java.util.ArrayList;
import java.util.Arrays;

public class SessionManager {
    private static SessionManager instance;
    private  User user;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void loggout() {
        if (user != null) {
            user.setID(-1);
            Arrays.fill(user.getKeyLogin(), (byte) 0);
            user.setLogins(new ArrayList<>());
        }
        user = null;
    }
}
