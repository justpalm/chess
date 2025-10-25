package dataaccess;

import dataaccess.exceptions.*;
import model.*;

public interface AuthDAO {

    public String createAuth (String username) throws DataAccessException;

    public AuthData getAuth(String authToken) throws DoesNotExistException;

    public void clearAuthTokens() throws DataAccessException;




}
