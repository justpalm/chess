package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import dataaccess.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.RequestsandResults.*;

import static org.junit.jupiter.api.Assertions.*;


public class ServiceTests {

    MemoryUserDAO memoryUserData = new MemoryUserDAO();
    MemoryAuthDAO memoryAuthData = new MemoryAuthDAO();
    MemoryGameDAO memoryGameData = new MemoryGameDAO();

    private final MainService service = new MainService();
//    private final UserService service = new UserService(memoryUserData, memoryAuthData, memoryGameData);

    @BeforeEach
    void clear() throws DataAccessException {

        memoryUserData.clearUsers();
        memoryAuthData.clearAuthTokens();
        memoryGameData.clearGames();

        service.clear();
    }


    @Test
    void clearTest() throws AlreadyTakenException, BadRequestException {

        RegisterRequest request = new RegisterRequest("Megan Hoopes", "estarbien", "megoonoopes");

        service.register(request);


        AlreadyTakenException exception = assertThrows(
                AlreadyTakenException.class,
                () -> service.register(request) // Example call
        );
    }


    @Test
    void registerUsersTest() throws AlreadyTakenException, BadRequestException {

        //

        RegisterRequest request = new RegisterRequest("Hey", "Password", "email");

        memoryUserData.createUser(request.username(), request.password(), request.email());

        service.register(request);

        assertEquals(memoryUserData, service.getUserDAO());
    }


    @Test
    void registerUsersBadRequest() {

        RegisterRequest request = new RegisterRequest(null, null, "palmerjustins");

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> service.register(request) // Example call
        );
    }

    @Test
    void registerUserAlreadyTaken() throws AlreadyTakenException, BadRequestException {

        RegisterRequest request = new RegisterRequest("Megan Hoopes", "estarbien", "megoonoopes");

        service.register(request);


        AlreadyTakenException exception = assertThrows(
                AlreadyTakenException.class,
                () -> service.register(request) // Example call
        );
    }

    @Test
    void loginTest() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {

        RegisterRequest register_request = new RegisterRequest("Hey", "Password", "email");

        service.register(register_request);

        LoginRequest loginRequest = new LoginRequest(register_request.username(), register_request.password());

        service.login(loginRequest);

        LoginResult loginResult = service.login(loginRequest);

        assertEquals(loginRequest.username(), loginResult.username());

        UnauthorizedException exception = assertThrows(
                UnauthorizedException.class,
                () -> memoryAuthData.getAuthToken(loginResult.authToken()) // Example call
        );



    }

    @Test
    void loginBadRequestTest() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {

        RegisterRequest request = new RegisterRequest("Hey", "Password", "email");

        memoryUserData.createUser(request.username(), request.password(), request.email());

        service.register(request);


        LoginRequest loginRequest = new LoginRequest("", "");

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> service.login(loginRequest)
        );
    }
    @Test
    void loginUnauthorizedTest() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {

        //I'm not sure how best to factor in this one/


        RegisterRequest request = new RegisterRequest("Hey", "Password", "email");

        memoryUserData.createUser(request.username(), request.password(), request.email());

        service.register(request);


        LoginRequest loginRequest = new LoginRequest("Hey", "Pass");

        UnauthorizedException exception = assertThrows(
                UnauthorizedException.class,
                () -> service.login(loginRequest));

        }

    @Test
    void logoutPositiveTest() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {

        RegisterRequest request = new RegisterRequest("Hey", "Password", "email");

        memoryUserData.createUser(request.username(), request.password(), request.email());


        RegisterResult registerResult = service.register(request);



        LogoutRequest logoutRequest = new LogoutRequest(registerResult.authToken());

        try {
            service.logout(logoutRequest);
        } catch UnauthorizedException {

        }




        MemoryAuthDAO Test = service.getAuthDAO();

        System.out.println(Test);
        System.out.println(logResult.username());
        System.out.println(loginResult.authToken());

    }







}










//
//
//    @Test
//    void registerUsers () {}
//
//    @Test
//    void registerUsers () {}











