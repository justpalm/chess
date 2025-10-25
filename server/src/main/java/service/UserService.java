package service;

import dataaccess.*;

import dataaccess.exceptions.*;
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

        if (this.MemoryUserData.getUser(registerRequest.username()) != null) {
            throw new AlreadyTakenException("User already exists with this username");
        }

        try {
            MemoryUserData.createUser(registerRequest.username(), registerRequest.password(),registerRequest.email());
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
            throw new BadRequestException("User does not exist");
        }

        String authToken;

        try {
            authToken = this.MemoryAuthData.createAuth(loginRequest.username());
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException("AuthToken not accepted ");
        }

        LoginResult loginResult = new LoginResult(loginRequest.username(), authToken);

        return loginResult;
    }











    }



//    public void logout(LogoutRequest logoutRequest) {
//
//
//    }


