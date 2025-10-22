package dataaccess;

import model.*;

public interface UserDAO {

    UserData getUser(String username) throws DataAccessException;

    UserData createUser(String username, String password, String email) throws AlreadyExistsException;

    UserData clearUsers();

}
