package service;

import dataaccess.*;

import dataaccess.exceptions.*;
import model.UserData;
import service.RequestsandResults.*;

import java.util.Objects;


public class UserService {

    private final MemoryUserDAO MemoryUserData;
    private final MemoryAuthDAO MemoryAuthData;
    private final MemoryGameDAO MemoryGameData;

    public UserService(MemoryUserDAO memoryUserData, MemoryAuthDAO memoryAuthData, MemoryGameDAO memoryGameData) {

        this.MemoryUserData = memoryUserData;
        this.MemoryAuthData = memoryAuthData;
        this.MemoryGameData = memoryGameData;
    }

    public RegisterResult register(RegisterRequest registerRequest) throws AlreadyTakenException, BadRequestException, UnauthorizedException {
        try {
            MemoryUserData.createUser(registerRequest.username(), registerRequest.password(), registerRequest.email());
        } catch (AlreadyTakenException e ) {
            throw new AlreadyTakenException(e.getMessage());
        }
        String new_authToken;
        new_authToken = MemoryAuthData.createAuth(registerRequest.username());

        return (new RegisterResult(registerRequest.username(), new_authToken));
    }


//    public void clear() {
//        MemoryUserData.clearUsers();
//    }
//}

    public LoginResult login(LoginRequest loginRequest) throws BadRequestException, UnauthorizedException, DataAccessException {

        //Check if user exists
        MemoryUserData.getUser(loginRequest.username());


//      //Check password is valid
        String authToken;
        var login_user = this.MemoryUserData.getUser(loginRequest.username());
        if (!Objects.equals(login_user.password(), loginRequest.password())) {
            throw new UnauthorizedException("Error: Password incorrect");
        }

        // Get new authToken
        try {
            authToken = this.MemoryAuthData.createAuth(loginRequest.username());
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException(e.getMessage());
        }

        return new LoginResult(loginRequest.username(), authToken);
    }

    public LogoutResult logout(String authToken) throws UnauthorizedException {

        try {
            this.MemoryAuthData.deleteAuthToken(authToken);
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


