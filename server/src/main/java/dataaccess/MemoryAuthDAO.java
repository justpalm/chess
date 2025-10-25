package dataaccess;

import dataaccess.exceptions.*;
import model.*;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{

    final private HashMap<String, AuthData> authTokens = new HashMap<>();

    @Override
    public String createAuth(String username) throws DataAccessException {

        if (authTokens.get(username) != null) {
            throw new DataAccessException("User already has token");
        }

        String authToken = UUID.randomUUID().toString();
        AuthData new_authdata = new AuthData(authToken, username);
        authTokens.put(authToken, new_authdata);

        return authToken;
    }


    @Override
    public String getAuthToken(String username) throws UnauthorizedException{


        AuthData authdata = authTokens.get(username);
        if (authdata == null) {
            throw new UnauthorizedException("AuthToken does not exist.");
        } else {
            return authdata.authToken();
        }
    }

    @Override
    public void clearAuthTokens() {
    authTokens.clear();
    }
}
