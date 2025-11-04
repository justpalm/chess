package dataaccess;

import dataaccess.exceptions.UnauthorizedException;

public class MySLQAuthDataAccess implements AuthDAO{
    @Override
    public String createAuth(String username) throws UnauthorizedException {
        return "";
    }

    @Override
    public String getAuthToken(String authToken) throws UnauthorizedException {
        return "";
    }

    @Override
    public void deleteAuthToken(String authToken) throws UnauthorizedException {

    }

    @Override
    public void clearAuthTokens() {

    }

    @Override
    public String getUsername(String authToken) {
        return "";
    }
}
