package dataaccess;

import dataaccess.exceptions.AlreadyTakenException;
import model.*;
import service.RequestsandResults.*;

import java.util.HashMap;
import java.util.Objects;

public class MemoryUserDAO implements UserDAO {

    final private HashMap<String, UserData> users = new HashMap<>();


    @Override
    public UserData getUser(RegisterRequest registerRequest) {
        return users.get(registerRequest.username());
    }

    @Override
    public UserData createUser(RegisterRequest registerRequest) throws AlreadyTakenException {
        UserData u = new UserData(registerRequest.username(), registerRequest.password(), registerRequest.email());

        if (users.containsKey(registerRequest.username())) {
            throw new AlreadyTakenException("The user " + registerRequest.username() + " already exists");
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
