package dataaccess;

import model.*;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{

    final private HashMap<String, String> authTokens = new HashMap<>();


    @Override
    public String createAuth() throws DataAccessException {
        authToken
        return UUID.randomUUID().toString();
    }

    @Override
    public String getAuth() throws DataAccessException {
        return
    }

    @Override
    public void deleteAuths() throws DataAccessException {

    }
}
