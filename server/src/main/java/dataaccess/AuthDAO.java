package dataaccess;

import dataaccess.exceptions.*;
import model.AuthData;

import java.util.HashMap;

public interface AuthDAO {

    public String createAuth (String username) throws UnauthorizedException;

    public String getAuthToken(String authToken) throws UnauthorizedException;

    public void deleteAuthToken(String authToken) throws UnauthorizedException;

    public void clearAuthTokens();

    public String getUsername (String authToken);




}
