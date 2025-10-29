package service;

import chess.ChessGame;
import dataaccess.GameDAO;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import dataaccess.exceptions.*;
import model.GameData;
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
    void clearTest() throws AlreadyTakenException, BadRequestException, UnauthorizedException{

        RegisterRequest request = new RegisterRequest("Megan Hoopes", "estarbien", "megoonoopes");

        service.register(request);


        AlreadyTakenException exception = assertThrows(
                AlreadyTakenException.class,
                () -> service.register(request) // Example call
        );
    }


    @Test
    void registerUsersTest() throws AlreadyTakenException, BadRequestException, UnauthorizedException{

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
    void registerUserAlreadyTaken() throws AlreadyTakenException, BadRequestException, UnauthorizedException {

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


        //This test is dubious, should check



    }

    @Test
    void loginBadRequestTest() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {

        RegisterRequest request = new RegisterRequest("Hey", "Password", "email");

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

        service.register(request);


        LoginRequest loginRequest = new LoginRequest("Hey", "Pass");

        UnauthorizedException exception = assertThrows(
                UnauthorizedException.class,
                () -> service.login(loginRequest));

        }

    @Test
    void logoutPositiveTest() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {

        RegisterRequest request = new RegisterRequest("Hey", "Password", "email");
                RegisterResult registerResult = service.register(request);



        LogoutRequest logoutRequest = new LogoutRequest(registerResult.authToken());

        service.logout(logoutRequest);

        }

    @Test
    void logoutNegativeTest() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {

        //Create User
        RegisterRequest request = new RegisterRequest("Hey", "Password", "email");
        RegisterResult registerResult = service.register(request);


        //Bad Logout Request
        LogoutRequest logoutRequest = new LogoutRequest("1213546515");

        UnauthorizedException exception = assertThrows(
                UnauthorizedException.class,
                () -> service.logout(logoutRequest);

    }


    @Test
    void createGamePositiveTest() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {

        //Create User
        RegisterRequest request = new RegisterRequest("Hey", "Password", "email");
        RegisterResult registerResult = service.register(request);

        String authToken = registerResult.authToken();

        //Create Game with AuthToken
        CreateGameRequest createGameRequest = new CreateGameRequest(registerResult.authToken(), "new_game");

        CreateGameResult createGameResult = service.createGame(createGameRequest);


        //First Game assertion
        assertEquals(1, service.getGameDAO().size());

        GameData new_game = service.getGameDAO().getGame(1);

        assertEquals("new_game", new_game.gameName());

        //Second Assertion
        createGameRequest = new CreateGameRequest(registerResult.authToken(), "new_game_2");

        createGameResult = service.createGame(createGameRequest);

        assertEquals(2, service.getGameDAO().size());

        new_game = service.getGameDAO().getGame(2);

        assertEquals("new_game_2", new_game.gameName());

    }



    @Test
    void createGameBadTokenTest() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {

        //Create User
        RegisterRequest request = new RegisterRequest("Hey", "Password", "email");
        RegisterResult registerResult = service.register(request);

        String authToken = registerResult.authToken();

        //Create Game with Invalid AuthToken
        CreateGameRequest createGameRequest = new CreateGameRequest("ajsdahjfhdf", "new_game");
        UnauthorizedException exception = assertThrows(
                UnauthorizedException.class,
                () -> service.createGame(createGameRequest));
    }

    @Test
    void createGameNoInfoTest() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {

        //Create User
        RegisterRequest request = new RegisterRequest("Hey", "Password", "email");
        RegisterResult registerResult = service.register(request);

        String authToken = registerResult.authToken();

        //Create Game with Invalid AuthToken
        CreateGameRequest createGameRequest = new CreateGameRequest(registerResult.authToken(), "");
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> service.createGame(createGameRequest));
    }


    @Test
    void joinGame() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {

        //Create User
        RegisterRequest request = new RegisterRequest("Hey", "Password", "email");
        RegisterResult registerResult = service.register(request);

        String authToken = registerResult.authToken();

        //Create Game with AuthToken
        CreateGameRequest createGameRequest = new CreateGameRequest(registerResult.authToken(), "new_game");

        CreateGameResult createGameResult = service.createGame(createGameRequest);


        //Join the game
        JoinGameRequest joinGameRequest = new JoinGameRequest(registerResult.authToken(), ChessGame.TeamColor.WHITE, "1");
        JoinGameResult joinGameResult = service.joinGame(joinGameRequest);


    }










}



//
//        MemoryAuthDAO Test = service.getAuthDAO();
//
//        System.out.println(Test);
//        System.out.println(logResult.username());
//        System.out.println(loginResult.authToken());



















//
//
//    @Test
//    void registerUsers () {}
//
//    @Test
//    void registerUsers () {}











