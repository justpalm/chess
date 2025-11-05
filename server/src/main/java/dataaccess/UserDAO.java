package dataaccess;

import dataaccess.exceptions.*;
import dataaccess.exceptions.UnauthorizedException;
import model.*;

public interface UserDAO {

    public UserData getUser(String username) throws UnauthorizedException, DataAccessException;

    public UserData createUser(String username, String password, String email) throws AlreadyTakenException, DataAccessException;

    public void clearUsers()throws DataAccessException;

}
