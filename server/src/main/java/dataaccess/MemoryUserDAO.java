package dataaccess;

import dataaccess.exceptions.AlreadyTakenException;
import model.*;
import service.RequestsandResults.*;

import java.util.HashMap;
import java.util.Objects;

public class MemoryUserDAO implements UserDAO {

    final private HashMap<String, UserData> users = new HashMap<>();


    @Override
    public UserData getUser(String username) {
        return users.get(username);
    }

    @Override
    public UserData createUser(String username, String password, String email) throws AlreadyTakenException {
        UserData u = new UserData(username, password, email);

        if (users.containsKey(username)) {
            throw new AlreadyTakenException("The user " + username + " already exists");
        }
        else {
            users.put(u.username(), u);
            return null;
        }
    }

    @Override
    public void clearUsers() {users.clear();}


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemoryUserDAO that = (MemoryUserDAO) o;
        return Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(users);
    }

    @Override
    public String toString() {
        return "MemoryUserDAO{" +
                "users=" + users +
                '}';
    }
}
