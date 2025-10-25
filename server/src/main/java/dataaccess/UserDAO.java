package dataaccess;

import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.DataAccessException;
import model.*;
import service.RequestsandResults.RegisterRequest;

public interface UserDAO {

    public UserData getUser(RegisterRequest registerRequest) throws DataAccessException;

    public UserData createUser(RegisterRequest registerRequest) throws AlreadyTakenException;

    public void clearUsers();

}
