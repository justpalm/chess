package dataaccess;

import model.*;

import java.util.HashMap;

public class MemoryUserDAO implements UserDAO {

    final private HashMap<String, UserData> users = new HashMap<>();


    @Override
    public UserData getUser(String username) throws DataAccessException {
        return users.get(username);
    }

    @Override
    public UserData createUser(String username, String password, String email) throws AlreadyExistsException {
        UserData u = new UserData(username, password, email);

        if (users.containsKey(username)) {
            throw new AlreadyExistsException("The user " + username + " already exists");
        }
        else {
            users.put(u.username(), u);
            return null;
        }
    }

    @Override
    public void clearUsers() {
        users.clear();
    }

}
