package service;

import dataaccess.*;

import dataaccess.exceptions.*;
import service.requestsandresults.*;

import java.util.Objects;


public class UserService {

    private final MemoryUserDAO memoryUserData;
    private final MemoryAuthDAO memoryAuthData;
    private final MemoryGameDAO memoryGameData;

    public UserService(MemoryUserDAO memoryUserData, MemoryAuthDAO memoryAuthData, MemoryGameDAO memoryGameData) {

        this.memoryUserData = memoryUserData;
        this.memoryAuthData = memoryAuthData;
        this.memoryGameData = memoryGameData;
    }

    public RegisterResult register(RegisterRequest registerRequest) throws AlreadyTakenException, BadRequestException, UnauthorizedException {
        try {
            memoryUserData.createUser(registerRequest.username(), registerRequest.password(), registerRequest.email());
        } catch (AlreadyTakenException e ) {
            throw new AlreadyTakenException(e.getMessage());
        }
        String newAuthToken;
        newAuthToken = memoryAuthData.createAuth(registerRequest.username());

        return (new RegisterResult(registerRequest.username(), newAuthToken));
    }


//    public void clear() {
//        MemoryUserData.clearUsers();
//    }
//}

    public LoginResult login(LoginRequest loginRequest) throws BadRequestException, UnauthorizedException, DataAccessException {

        //Check if user exists
        memoryUserData.getUser(loginRequest.username());


//      //Check password is valid
        String authToken;
        var loginUser = this.memoryUserData.getUser(loginRequest.username());
        if (!Objects.equals(loginUser.password(), loginRequest.password())) {
            throw new UnauthorizedException("Error: Password incorrect");
        }

        // Get new authToken
        try {
            authToken = this.memoryAuthData.createAuth(loginRequest.username());
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException(e.getMessage());
        }

        return new LoginResult(loginRequest.username(), authToken);
    }

    public LogoutResult logout(String authToken) throws UnauthorizedException {

        try {
            this.memoryAuthData.deleteAuthToken(authToken);
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException(e.getMessage()); //I am not sure what kind of auth stuff this is, but it is here.
        }

        return new LogoutResult();
    }
}


//    public void logout(LogoutRequest logoutRequest) {
//
//
//    }


