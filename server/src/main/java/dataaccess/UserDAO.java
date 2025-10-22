package dataaccess;

import model.*;

public interface UserDAO {

    public UserData getUser(String username) throws DataAccessException;

    public UserData createUser(String username, String password, String email) throws AlreadyExistsException;

    public void clearUsers();

}
