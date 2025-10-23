package dataaccess;

import dataaccess.exceptions.AlreadyExistsException;
import dataaccess.exceptions.DataAccessException;
import model.*;

public interface UserDAO {

    public UserData getUser(String username) throws DataAccessException;

    public UserData createUser(String username, String password, String email) throws AlreadyExistsException;

    public void clearUsers();

}
