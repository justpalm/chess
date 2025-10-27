package dataaccess;

import dataaccess.exceptions.*;

public interface AuthDAO {

    public String createAuth (String username) throws DataAccessException;

    public String getAuthToken(String authToken) throws UnauthorizedException;

    public void deleteAuthToken(String authToken) throws UnauthorizedException;

    public void clearAuthTokens();




}
