package service;

import dataaccess.*;

import dataaccess.exceptions.*;
import org.mindrot.jbcrypt.BCrypt;
import service.requestsandresults.*;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.Objects;


public class UserService {

    private final UserDAO memoryUserData;
    private final AuthDAO memoryAuthData;

    public UserService(UserDAO memoryUserData, AuthDAO memoryAuthData) {

        this.memoryUserData = memoryUserData;
        this.memoryAuthData = memoryAuthData;
    }

    public RegisterResult register(RegisterRequest registerRequest) throws AlreadyTakenException, UnauthorizedException, DataAccessException {
        try {
            memoryUserData.createUser(registerRequest.username(), registerRequest.password(), registerRequest.email());
        } catch (AlreadyTakenException e ) {
            throw new AlreadyTakenException("Error: " + e.getMessage());
        }
        String newAuthToken;
        newAuthToken = memoryAuthData.createAuth(registerRequest.username());

        return (new RegisterResult(registerRequest.username(), newAuthToken));
    }

    public LoginResult login(LoginRequest loginRequest) throws UnauthorizedException, DataAccessException {


        String authToken;
        if ((memoryUserData.getUser(loginRequest.username())) == null) {
            throw new UnauthorizedException("Error: User not found");
        }

        var loginUser = memoryUserData.getUser(loginRequest.username());

        if (!BCrypt.checkpw(loginRequest.password(), loginUser.password())) {
            throw new UnauthorizedException("Error: Password incorrect");
        }

        try {
            authToken = this.memoryAuthData.createAuth(loginRequest.username());
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException("Error: " + e.getMessage());
        }

        return new LoginResult(loginRequest.username(), authToken);
    }

    public LogoutResult logout(String authToken) throws UnauthorizedException, DataAccessException  {

        try {
            this.memoryAuthData.deleteAuthToken(authToken);
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException("Error: " + e.getMessage()); //I am not sure what kind of auth stuff this is, but it is here.
        }

        return new LogoutResult();
    }
}



