package dataaccess;

import dataaccess.exceptions.*;

public interface AuthDAO {

    public String createAuth (String username) throws UnauthorizedException;

    public String getAuthToken(String authToken) throws BadRequestException;

    public void deleteAuthToken(String authToken) throws UnauthorizedException;

    public void clearAuthTokens();




}
