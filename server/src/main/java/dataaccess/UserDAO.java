package dataaccess;

import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.DataAccessException;
import dataaccess.exceptions.UnauthorizedException;
import model.*;
import service.RequestsandResults.RegisterRequest;

public interface UserDAO {

    public UserData getUser(String username) throws UnauthorizedException;

    public UserData createUser(String username, String password, String email) throws AlreadyTakenException;

    public void clearUsers();

}
