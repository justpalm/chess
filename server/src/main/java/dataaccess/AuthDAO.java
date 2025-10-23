package dataaccess;

import dataaccess.exceptions.DataAccessException;
import dataaccess.exceptions.DoesNotExistException;
import model.*;

public interface AuthDAO {

    public String createAuth(UserData userData) throws DataAccessException;

    public AuthData getAuth(String authToken) throws DoesNotExistException;

    public void deleteAuthTokens() throws DataAccessException;




}
