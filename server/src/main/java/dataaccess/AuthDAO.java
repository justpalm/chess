package dataaccess;

import dataaccess.exceptions.*;
import model.AuthData;

import java.util.HashMap;

public interface AuthDAO {

    public String createAuth (String username) throws UnauthorizedException, DataAccessException;

    public String getAuthToken(String authToken) throws UnauthorizedException, DataAccessException;

    public void deleteAuthToken(String authToken) throws UnauthorizedException, DataAccessException;

    public void clearAuthTokens() throws DataAccessException;

    public String getUsername (String authToken) throws DataAccessException, UnauthorizedException;




}
