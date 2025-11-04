package service;

import dataaccess.*;

import dataaccess.exceptions.*;
import org.mindrot.jbcrypt.BCrypt;
import service.requestsandresults.*;

import java.util.Objects;


public class UserService {

    private final UserDAO memoryUserData;
    private final AuthDAO memoryAuthData;

    public UserService(UserDAO memoryUserData, AuthDAO memoryAuthData) {

        this.memoryUserData = memoryUserData;
        this.memoryAuthData = memoryAuthData;
    }

    public RegisterResult register(RegisterRequest registerRequest) throws AlreadyTakenException, UnauthorizedException {
        try {
            memoryUserData.createUser(registerRequest.username(), registerRequest.password(), registerRequest.email());
        } catch (AlreadyTakenException e ) {
            throw new AlreadyTakenException("Error" + e.getMessage());
        }
        String newAuthToken;
        newAuthToken = memoryAuthData.createAuth(registerRequest.username());

        return (new RegisterResult(registerRequest.username(), newAuthToken));
    }

    public LoginResult login(LoginRequest loginRequest) throws UnauthorizedException {


        String authToken;
        if (memoryUserData.getUser(loginRequest.username()) == null) {
            throw new UnauthorizedException("Error: User not found");
        }

        var loginUser = memoryUserData.getUser(loginRequest.username());

        // read the previously hashed password from the database
        //Check password is valid
        if (!BCrypt.checkpw(loginRequest.password(), loginUser.password())) {
            throw new UnauthorizedException("Error: Password incorrect");
        }

        // Not sure if this will need to be reimplemented?
//        if (!Objects.equals(hashedPassword, loginUser.password())) {
//            throw new UnauthorizedException("Error: Password incorrect");
//        }

        // Get new authToken
        try {
            authToken = this.memoryAuthData.createAuth(loginRequest.username());
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException("Error" + e.getMessage());
        }

        return new LoginResult(loginRequest.username(), authToken);
    }

    public LogoutResult logout(String authToken) throws UnauthorizedException {

        try {
            this.memoryAuthData.deleteAuthToken(authToken);
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException("Error: " + e.getMessage()); //I am not sure what kind of auth stuff this is, but it is here.
        }

        return new LogoutResult();
    }
}



