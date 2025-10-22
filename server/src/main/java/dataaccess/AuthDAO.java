package dataaccess;

import model.*;

import java.util.Collection;

public interface AuthDAO {

    public String createAuth(UserData userData) throws DataAccessException;

    public AuthData getAuth(String authToken) throws DoesNotExistException;

    public void deleteAuthTokens() throws DataAccessException;




}
