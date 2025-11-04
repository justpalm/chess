package service;

import chess.ChessGame;
import dataaccess.*;
import dataaccess.exceptions.*;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.requestsandresults.*;

import static org.junit.jupiter.api.Assertions.*;


public class ServiceTests {

    MemoryUserDAO memoryUserData = new MemoryUserDAO();
    MemoryAuthDAO memoryAuthData = new MemoryAuthDAO();
    MemoryGameDAO memoryGameData = new MemoryGameDAO();

    private final MainService service = new MainService(memoryUserData, memoryGameData, memoryAuthData);
//    private final UserService service = new UserService(memoryUserData, memoryAuthData, memoryGameData);

    @BeforeEach
    void clear() {

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

        RegisterRequest request = new RegisterRequest("Justin Palmer", "hello", "new_email");

        service.register(request);


        AlreadyTakenException exception = assertThrows(
                AlreadyTakenException.class,
                () -> service.register(request) // Example call
        );
    }

    @Test
    void loginTest() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {

        //Register a new user
        RegisterRequest registerRequest = new RegisterRequest("Hey", "Password", "email");
        RegisterResult registerResult = service.register(registerRequest);

        //Logout the User
        LogoutRequest logoutRequest = new LogoutRequest(registerResult.authToken());
        service.logout(logoutRequest);

        //Attempt new login

        LoginRequest loginRequest = new LoginRequest(registerRequest.username(), registerRequest.password());
        service.login(loginRequest);
        LoginResult loginResult = service.login(loginRequest);

        assertEquals(loginRequest.username(), loginResult.username());

        //Verifying creation of authToken
        UnauthorizedException exception = assertThrows(
                UnauthorizedException .class,
                () -> memoryAuthData.getAuthToken(loginResult.authToken()) // Example call
        );
    }

    @Test
    void loginBadRequestTest() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {

        RegisterRequest request = new RegisterRequest("Hey", "Password", "email");

        service.register(request);


        LoginRequest loginRequest = new LoginRequest(null, null);

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


        //Create User
        RegisterRequest request = new RegisterRequest("Hey", "Password", "email");
        RegisterResult registerResult = service.register(request);



        LogoutRequest logoutRequest = new LogoutRequest(registerResult.authToken());

        service.logout(logoutRequest);

        UnauthorizedException exception= assertThrows(
                UnauthorizedException.class,
                () -> service.getAuthDAO().getAuthToken(registerResult.authToken()));

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
                () -> service.logout(logoutRequest));

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

        GameData newGame = service.getGameDAO().getGame("1");

        assertEquals("new_game", newGame.gameName());

        //Second Assertion
        createGameRequest = new CreateGameRequest(registerResult.authToken(), "new_game_2");

        createGameResult = service.createGame(createGameRequest);

        assertEquals(2, service.getGameDAO().size());

        newGame = service.getGameDAO().getGame("2");

        assertEquals("new_game_2", newGame.gameName());

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
        CreateGameRequest createGameRequest = new CreateGameRequest(registerResult.authToken(), null);
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> service.createGame(createGameRequest));
    }


    @Test
    void joinGamePositive() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {

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

        GameData testGame = service.getGameDAO().getGame("1");



        assertEquals(testGame.whiteUsername(), request.username());

    }

    @Test
    void joinGameNegativeNullField() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {

        //Create User
        RegisterRequest request = new RegisterRequest("Hey", "Password", "email");
        RegisterResult registerResult = service.register(request);

        String authToken = registerResult.authToken();

        //Create Game with AuthToken
        CreateGameRequest createGameRequest = new CreateGameRequest(registerResult.authToken(), "new_game");

        CreateGameResult createGameResult = service.createGame(createGameRequest);

        JoinGameRequest joinGameRequest = new JoinGameRequest(registerResult.authToken(), null, "1");

        //Join the game
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> service.joinGame(joinGameRequest));

        //


    }

    @Test
    void joinGameNegativeNoSpaceWhite() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {

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


        //Try a new user join the same team
        request = new RegisterRequest("Hey2", "Password", "email");
        registerResult = service.register(request);

        joinGameRequest = new JoinGameRequest(registerResult.authToken(), ChessGame.TeamColor.WHITE, "1");

        JoinGameRequest finalJoinGameRequest = joinGameRequest;
        AlreadyTakenException exception = assertThrows(
                AlreadyTakenException.class,
                () -> service.joinGame(finalJoinGameRequest));
    }


    @Test
    void joinGameNegativeNoSpaceBlack() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {

        //Create User
        RegisterRequest request = new RegisterRequest("Hey", "Password", "email");
        RegisterResult registerResult = service.register(request);

        String authToken = registerResult.authToken();

        //Create Game with AuthToken
        CreateGameRequest createGameRequest = new CreateGameRequest(registerResult.authToken(), "new_game");

        CreateGameResult createGameResult = service.createGame(createGameRequest);

        //Join the game
        JoinGameRequest joinGameRequest = new JoinGameRequest(registerResult.authToken(), ChessGame.TeamColor.BLACK, "1");
        JoinGameResult joinGameResult = service.joinGame(joinGameRequest);


        //Try a new user join the same team
        request = new RegisterRequest("Hey2", "Password", "email");
        registerResult = service.register(request);

        joinGameRequest = new JoinGameRequest(registerResult.authToken(), ChessGame.TeamColor.BLACK, "1");

        JoinGameRequest finalJoinGameRequest = joinGameRequest;
        AlreadyTakenException exception = assertThrows(
                AlreadyTakenException.class,
                () -> service.joinGame(finalJoinGameRequest));





    }










}


















//
//
//    @Test
//    void registerUsers () {}
//
//    @Test
//    void registerUsers () {}











