package dataaccess;

import dataaccess.exceptions.DataAccessException;
import dataaccess.exceptions.DoesNotExistException;
import model.*;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{

    final private HashMap<String, AuthData> authTokens = new HashMap<>();

    @Override
    public String createAuth(UserData userData) throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        AuthData new_authdata = new AuthData(authToken, userData.username());
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
    public void deleteAuthTokens() throws DataAccessException {
    authTokens.clear();
    }
}
