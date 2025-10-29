package dataaccess;

import dataaccess.exceptions.*;
import model.*;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {

    final private HashMap<String, AuthData> authTokens = new HashMap<>();

    @Override
    public String createAuth(String username) throws UnauthorizedException {

        if (authTokens.get(username) != null) {
            throw new UnauthorizedException("Error: User already has token");
        }

        String authToken = UUID.randomUUID().toString();
        AuthData new_authdata = new AuthData(username, authToken);
        authTokens.put(authToken, new_authdata);

        return authToken;
    }


    @Override
    public String getAuthToken(String authToken) throws BadRequestException {

        AuthData authdata = authTokens.get(authToken);
        if (authdata == null) {
            throw new BadRequestException("Error: AuthToken does not exist.");
        } else {
            return authdata.authToken();
        }
    }

    @Override
    public void deleteAuthToken(String authToken) throws UnauthorizedException {

        if (authTokens.get(authToken) == null) {
            throw new UnauthorizedException("Error: Could not find authToken");
        } else {
            authTokens.remove(authToken);
        }
    }


    @Override
    public void clearAuthTokens() {
        authTokens.clear();
    }


    @Override
    public String toString() {
        return "MemoryUserDAO{" +
                "authTokens=" + authTokens +
                '}';
    }
}
