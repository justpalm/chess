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

    public RegisterResult register(RegisterRequest registerRequest) throws AlreadyTakenException, BadRequestException {

        //Checking for missing fields
        if (Objects.equals(registerRequest.username(), "")
                | Objects.equals(registerRequest.password(), "")
                | Objects.equals(registerRequest.email(), "")) {
            throw new BadRequestException("Fields not filled correctly.");
        }

        try {
            MemoryUserData.createUser(registerRequest.username(), registerRequest.password(), registerRequest.email());
        } catch (AlreadyTakenException e) {
            throw new AlreadyTakenException("Username taken");
        }

        String new_authToken;
        try {
            new_authToken = MemoryAuthData.createAuth(registerRequest.username());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return (new RegisterResult(registerRequest.username(), new_authToken));
    }


//    public void clear() {
//        MemoryUserData.clearUsers();
//    }
//}

    public LoginResult login(LoginRequest loginRequest) throws BadRequestException, UnauthorizedException, DataAccessException {
        //Checking for missing fields
        if (Objects.equals(loginRequest.username(), "")
                | Objects.equals(loginRequest.password(), "")) {
            throw new BadRequestException("Fields not filled correctly.");
        }

        //Check if user exists
        if (this.MemoryUserData.getUser(loginRequest.username()) == null) {
            throw new UnauthorizedException("User does not exist");
        }

//      //Check password exists
        String authToken;
        var login_user = this.MemoryUserData.getUser(loginRequest.username());
        if (login_user.password() != loginRequest.password()) {
            throw new UnauthorizedException("Password incorrect");
        }

//        // Get new authToken
//        try {
            authToken = this.MemoryAuthData.createAuth(loginRequest.username());
//        } catch (DataAccessException e) {
//            throw new UnauthorizedException("");
//        }

        LoginResult loginResult = new LoginResult(loginRequest.username(), authToken);

        return loginResult;
    }

    public LogoutResult logout(LogoutRequest logoutRequest) throws UnauthorizedException {

        try {
            this.MemoryAuthData.deleteAuthToken(logoutRequest.authToken());
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException("authToken does not exist"); //I am not sure what kind of auth stuff this is, but it is here.
        }

        return new LogoutResult();

    }
}









//    public void logout(LogoutRequest logoutRequest) {
//
//
//    }


