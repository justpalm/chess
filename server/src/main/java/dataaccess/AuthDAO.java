package dataaccess;

import model.*;

import java.util.Collection;

public interface AuthDAO {

    public String createAuth() throws DataAccessException;

    public String getAuth() throws DataAccessException;

    public void deleteAuths() throws DataAccessException;




}
