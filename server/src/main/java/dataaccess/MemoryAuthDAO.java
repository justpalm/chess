package dataaccess;

import dataaccess.exceptions.*;
import dataaccess.exceptions.*;
import model.*;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{

    final private HashMap<String, AuthData> authTokens = new HashMap<>();

    @Override
    public String createAuth(String username) throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        AuthData new_authdata = new AuthData(authToken, username);
        authTokens.put(authToken, new_authdata);

        return authToken;
    }


    @Override
    public AuthData getAuth(String authToken) throws DoesNotExistException {

        AuthData authdata = authTokens.get(authToken);
        if (authdata == null) {
            throw new DoesNotExistException("AuthToken does not exist.");
        } else {
            return authdata;
        }
    }

    @Override
    public void clearAuthTokens() throws DataAccessException {
    authTokens.clear();
    }
}
