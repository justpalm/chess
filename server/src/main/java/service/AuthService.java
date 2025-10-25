package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.BadRequestException;
import service.RequestsandResults.RegisterRequest;
import service.RequestsandResults.RegisterResult;

import java.util.Objects;


public class AuthService {

    private final MemoryUserDAO MemoryUserData;
    private final MemoryAuthDAO MemoryAuthData;
    private final MemoryGameDAO MemoryGameData;

    public AuthService(MemoryUserDAO memoryUserData, MemoryAuthDAO memoryAuthData, MemoryGameDAO memoryGameData) {

        this.MemoryUserData = memoryUserData;
        this.MemoryAuthData = memoryAuthData;
        this.MemoryGameData = memoryGameData;
    }


    public RegisterResult register(RegisterRequest registerRequest) throws AlreadyTakenException, BadRequestException {

        if (Objects.equals(registerRequest.username(), "")
                | Objects.equals(registerRequest.password(), "")
                | Objects.equals(registerRequest.email(), "")) {
            throw new BadRequestException("Fields not filled correctly.");
        }

        if (this.MemoryUserData.getUser(registerRequest.username()) != null) {
            throw new AlreadyTakenException("User already exists with this username");
        }

        try {
            MemoryUserData.createUser(registerRequest.username(), registerRequest.password(), registerRequest.email());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String new_authToken;
        try {
            new_authToken = MemoryAuthData.createAuth(registerRequest.username());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return (new RegisterResult(registerRequest.username(), new_authToken));
    }

    }


//    public void clear() {
//        MemoryUserData.clearUsers();
//    }
//}

//    public LoginResult login(LoginRequest loginRequest) {
//
//
//    }
//    public void logout(LogoutRequest logoutRequest) {
//
//
//    }



