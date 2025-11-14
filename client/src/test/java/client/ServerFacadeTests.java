package client;

import chess.ChessGame;
import dataaccess.*;
import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.BadRequestException;
import dataaccess.exceptions.DataAccessException;
import dataaccess.exceptions.UnauthorizedException;
import model.GameData;
import org.junit.jupiter.api.*;
import server.Server;
import service.requestsandresults.*;

import serverfacade.ServerFacade;
import server.Server;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() throws DataAccessException{
        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(String.format("http://localhost:%d", port));
    }

    @AfterEach
    void clearServer() throws DataAccessException {
        facade.clear();
    }

    @AfterAll
    static void stopServer() throws DataAccessException {
        facade.clear();
        server.stop();
    }



    @Test
    void clear() throws Exception {
        try {
            facade.clear();
        } catch (Exception e) {
            assertTrue(false);
        }
            assertTrue(true);
    }



    @Test
    void register() throws Exception {
        var request = new RegisterRequest("player1", "password", "p1@email.com");
        var authData = facade.register(request);
        assertTrue(authData.authToken().length() > 10);
    }

    @Test
    void failRegister() throws DataAccessException {
        var request = new RegisterRequest(null, "password", "p1@email.com");
        assertThrows(
                DataAccessException.class,
                () -> facade.register(request)
        );

    }

    @Test
    void login() throws Exception {

        var request = new RegisterRequest("player", "password", "p1@email.com");
        var authData = facade.register(request);
        assertTrue(authData.authToken().length() > 10);
    }


    @Test
    void failLogin() throws Exception {

        var request = new RegisterRequest("player1", "password", "p1@email.com");

        var loginRequest = new LoginRequest(null, "password");

        //No username given
        assertThrows(
                DataAccessException.class,
                () -> facade.login(loginRequest)
        );
    }

    @Test
    void createGame() throws Exception {
        var request = new RegisterRequest("player1", "password", "p1@email.com");
        var authData = facade.register(request);
        String authToken = authData.authToken();
        assertTrue(authToken.length() > 10);

        var createGameRequest = new CreateGameRequest(authToken, "new_game");
        var createGameResult = facade.createGame(createGameRequest);

        assertEquals("1", createGameResult.gameID());

        }

    @Test
    void failCreateGame() throws Exception {
        var request = new RegisterRequest("player1", "password", "p1@email.com");
        var authData = facade.register(request);
        String authToken = authData.authToken();
        assertTrue(authToken.length() > 10);

        //Invalid Token
        var createGameRequest = new CreateGameRequest("Hello", "new_game");
        assertThrows(
                DataAccessException.class,
                () -> facade.createGame(createGameRequest));
    }

    @Test
    void joinGame() throws Exception {
        var request = new RegisterRequest("player1", "password", "p1@email.com");
        var authData = facade.register(request);
        String authToken = authData.authToken();
        assertTrue(authToken.length() > 10);

        var createGameRequest = new CreateGameRequest(authToken, "new_game");
        var createGameResult = facade.createGame(createGameRequest);

        var joingGameRequest = new JoinGameRequest(authToken, ChessGame.TeamColor.WHITE,
                createGameResult.gameID());

        try {
            facade.joinGame(joingGameRequest);
        } catch (Exception e) {
            fail();
            return;
        }

        assertTrue(true);


    }


    @Test
    void failToJoinGame() throws Exception {
        var request = new RegisterRequest("player1", "password", "p1@email.com");
        var authData = facade.register(request);
        String authToken = authData.authToken();
        assertTrue(authToken.length() > 10);

        var createGameRequest = new CreateGameRequest(authToken, "new_game");
        var createGameResult = facade.createGame(createGameRequest);

        var joingGameRequest = new JoinGameRequest(authToken, null,
                createGameResult.gameID());

        try {
            facade.joinGame(joingGameRequest);
        } catch (Exception e) {
            assertTrue(true);
            return;
        }

        fail();
    }


    @Test
    void listGame() throws Exception {
        var request = new RegisterRequest("player1", "password", "p1@email.com");
        var authData = facade.register(request);
        String authToken = authData.authToken();
        assertTrue(authToken.length() > 10);

        var createGameRequest = new CreateGameRequest(authToken, "new_game");
        var createGameResult = facade.createGame(createGameRequest);

        var listGameRequest = new ListGamesRequest(authToken);
        var listGameResult = facade.listGames(listGameRequest);

        assertEquals(1, listGameResult.games().size());
    }

    @Test
    void failListGame() throws Exception {
        var request = new RegisterRequest("player1", "password", "p1@email.com");
        var authData = facade.register(request);
        String authToken = authData.authToken();
        assertTrue(authToken.length() > 10);

        //Invalid Token
        var createGameRequest = new CreateGameRequest("Hello", "new_game");
        assertThrows(
                DataAccessException.class,
                () -> facade.createGame(createGameRequest));
    }
}














//    @Test
//    void listgamesPositive() throws AlreadyTakenException, BadRequestException, UnauthorizedException, DataAccessException {
//        //Create User
//        RegisterRequest request = new RegisterRequest("He", "Passwor", "emai");
//        RegisterResult registerResult = service.register(request);
//
//        //Create Game with AuthToken
//        CreateGameRequest createGameRequest = new CreateGameRequest(registerResult.authToken(), "new_gam");
//        CreateGameResult createGameResult = service.createGame(createGameRequest);
//
//        //Get positive list
//        var list = service.getGameDAO().listGames();
//
//        var testList = new ArrayList<GameData>();
//        testList.add(service.getGameDAO().getGame("1"));
//
//        assertEquals(testList, list);
//
//    }
//
//    @Test
//    void listgamesNegative() throws AlreadyTakenException, BadRequestException, UnauthorizedException, DataAccessException {
//        //Create User
//        RegisterRequest request = new RegisterRequest("He", "Passwor", "emai");
//        RegisterResult registerResult = service.register(request);
//
//        //Get an empty list
//        var list = service.getGameDAO().listGames();
//
//        var testList = new ArrayList<GameData>();
//
//        testList.add(new GameData("ajsdksdjk", "ajsdksjd","", "", new ChessGame()));
//
//        assertNotEquals(testList, list);
//
//    }
//
//
//
//
//
//    @Test
//    void clearTest() throws AlreadyTakenException, BadRequestException, UnauthorizedException, DataAccessException {
//        {
//
//            RegisterRequest request = new RegisterRequest("Megan Hoopes", "estarbien", "megoonoopes");
//            service.register(request);
//            assertThrows(
//                    AlreadyTakenException.class,
//                    () -> service.register(request) // Example call
//            );
//        }
//    }
//    @Test
//    void registerUsersTest() throws AlreadyTakenException, DataAccessException{
//
//        //
//
//        RegisterRequest request = new RegisterRequest("Hi", "Pass", "mail");
//        memoryUserData.createUser(request.username(), request.password(), request.email());
//        assertEquals(memoryUserData, service.getUserDAO());
//    }
//
//
//    @Test
//    void registerUsersBadRequest() {
//
//        RegisterRequest request = new RegisterRequest(null, null, "palmerjust");
//
//        assertThrows(
//                BadRequestException.class,
//                () -> service.register(request) // Example call
//        );
//    }
//
//    @Test
//    void registerUserAlreadyTaken() throws AlreadyTakenException, BadRequestException, UnauthorizedException, DataAccessException {
//        {
//
//            RegisterRequest request = new RegisterRequest("Just Palm", "hello", "new_email");
//            service.register(request);
//            assertThrows(
//                    AlreadyTakenException.class,
//                    () -> service.register(request) // Example call
//            );
//        }
//    }
//
//    @Test
//    void loginTest() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {
//
//        //Register a new user
//        RegisterRequest registerRequest = new RegisterRequest("Phi", "Phie", "pho");
//        RegisterResult registerResult = service.register(registerRequest);
//
//        //Logout the User
//        LogoutRequest logoutRequest = new LogoutRequest(registerResult.authToken());
//        service.logout(logoutRequest);
//
//        //Attempt new login
//
//        LoginRequest loginRequest = new LoginRequest(registerRequest.username(), registerRequest.password());
//        LoginResult loginResult = service.login(loginRequest);
//
//        assertEquals(loginRequest.username(), loginResult.username());
//    }
//
//    @Test
//    void loginBadRequestTest() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {
//
//        RegisterRequest request = new RegisterRequest("Phi", "Phie", "pho");
//
//        service.register(request);
//
//
//        LoginRequest loginRequest = new LoginRequest(null, null);
//
//        assertThrows(
//                BadRequestException.class,
//                () -> service.login(loginRequest)
//        );
//    }
//    @Test
//    void loginUnauthorizedTest() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {
//
//        //I'm not sure how best to factor in this one/
//
//
//        RegisterRequest request = new RegisterRequest("Phi", "Password", "email");
//
//        service.register(request);
//
//
//        LoginRequest loginRequest = new LoginRequest("Phi", "Pass");
//
//        assertThrows(
//                UnauthorizedException.class,
//                () -> service.login(loginRequest));
//
//    }
//
//    @Test
//    void logoutPositiveTest() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {
//
//
//        //Create User
//        RegisterRequest request = new RegisterRequest("Phi", "Passwor", "emai");
//        RegisterResult registerResult = service.register(request);
//
//
//
//        LogoutRequest logoutRequest = new LogoutRequest(registerResult.authToken());
//
//        service.logout(logoutRequest);
//
//        assertThrows(
//                UnauthorizedException.class,
//                () -> service.getAuthDAO().getAuthToken(registerResult.authToken()));
//
//    }
//
//    @Test
//    void logoutNegativeTest() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {
//
//        //Create User
//        RegisterRequest request = new RegisterRequest("He", "Passwor", "emai");
//        service.register(request);
//
//
//        //Bad Logout Request
//        LogoutRequest logoutRequest = new LogoutRequest("1213546515");
//
//        assertThrows(
//                UnauthorizedException.class,
//                () -> service.logout(logoutRequest));
//
//    }
//
//
//    @Test
//    void createGamePositiveTest() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {
//
//        //Create User
//        RegisterRequest request = new RegisterRequest("He", "Passwor", "emai");
//        RegisterResult registerResult = service.register(request);
//
//        //Create Game with AuthToken
//        CreateGameRequest createGameRequest = new CreateGameRequest(registerResult.authToken(), "new_gam");
//        CreateGameResult createGameResult = service.createGame(createGameRequest);
//
//
//        //First Game assertion
////        assertEquals(1, service.getGameDAO().size());
//
//        GameData newGame = service.getGameDAO().getGame("1");
//
//        assertEquals("new_gam", newGame.gameName());
//
//        //Second Assertion
//        createGameRequest = new CreateGameRequest(registerResult.authToken(), "new_game_2");
//
//        service.createGame(createGameRequest);
//
////        assertEquals(2, service.getGameDAO().size());
//
//        newGame = service.getGameDAO().getGame("2");
//
//        assertEquals("new_game_2", newGame.gameName());
//
//    }
//
//    @Test
//    void createGameBadTokenTest() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {
//
//        //Create User
//        RegisterRequest request = new RegisterRequest("He", "Passwor", "emai");
//        RegisterResult registerResult = service.register(request);
//
//        String authToken = registerResult.authToken();
//
//        //Create Game with Invalid AuthToken
//        CreateGameRequest createGameRequest = new CreateGameRequest("ajsdahjfhdf", "new_game");
//        assertThrows(
//                UnauthorizedException.class,
//                () -> service.createGame(createGameRequest));
//    }
//
//    @Test
//    void createGameNoInfoTest() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {
//
//        //Create User
//        RegisterRequest request = new RegisterRequest("He", "Passwor", "emai");
//        RegisterResult registerResult = service.register(request);
//
//        String authToken = registerResult.authToken();
//
//        //Create Game with Invalid AuthToken
//        CreateGameRequest createGameRequest = new CreateGameRequest(registerResult.authToken(), null);
//        assertThrows(
//                BadRequestException.class,
//                () -> service.createGame(createGameRequest));
//    }
//
//
//    @Test
//    void joinGamePositive() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {
//
//        //Create User
//        RegisterRequest request = new RegisterRequest("He", "Passwor", "emai");
//        RegisterResult registerResult = service.register(request);
//
//        String authToken = registerResult.authToken();
//
//        //Create Game with AuthToken
//        CreateGameRequest createGameRequest = new CreateGameRequest(registerResult.authToken(), "new_gam");
//
//        CreateGameResult createGameResult = service.createGame(createGameRequest);
//
//
//        //Join the game
//        JoinGameRequest joinGameRequest = new JoinGameRequest(registerResult.authToken(), ChessGame.TeamColor.WHITE, "1");
//        JoinGameResult joinGameResult = service.joinGame(joinGameRequest);
//
//        GameData testGame = service.getGameDAO().getGame("1");
//
//
//
//        assertEquals(testGame.whiteUsername(), request.username());
//
//    }
//
//    @Test
//    void joinGameNegativeNullField() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {
//
//        //Create User
//        RegisterRequest request = new RegisterRequest("He", "Passwor", "emai");
//        RegisterResult registerResult = service.register(request);
//
//        String authToken = registerResult.authToken();
//
//        //Create Game with AuthToken
//        CreateGameRequest createGameRequest = new CreateGameRequest(registerResult.authToken(), "new_gam");
//
//        CreateGameResult createGameResult = service.createGame(createGameRequest);
//
//        JoinGameRequest joinGameRequest = new JoinGameRequest(registerResult.authToken(), null, "1");
//
//        //Join the game
//        BadRequestException exception = assertThrows(
//                BadRequestException.class,
//                () -> service.joinGame(joinGameRequest));
//
//        //
//
//
//    }
//
//    @Test
//    void joinGameNegativeNoSpaceWhite() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {
//
//        //Create User
//        RegisterRequest request = new RegisterRequest("He", "Passwor", "emai");
//        RegisterResult registerResult = service.register(request);
//
//        String authToken = registerResult.authToken();
//
//        //Create Game with AuthToken
//        CreateGameRequest createGameRequest = new CreateGameRequest(registerResult.authToken(), "new_gam");
//
//        CreateGameResult createGameResult = service.createGame(createGameRequest);
//
//        //Join the game
//        JoinGameRequest joinGameRequest = new JoinGameRequest(registerResult.authToken(), ChessGame.TeamColor.WHITE, "1");
//        JoinGameResult joinGameResult = service.joinGame(joinGameRequest);
//
//
//        //Try a new user join the same team
//        request = new RegisterRequest("Hey", "Pass", "email");
//        registerResult = service.register(request);
//
//        joinGameRequest = new JoinGameRequest(registerResult.authToken(), ChessGame.TeamColor.WHITE, "1");
//
//        JoinGameRequest finalJoinGameRequest = joinGameRequest;
//        assertThrows(
//                AlreadyTakenException.class,
//                () -> service.joinGame(finalJoinGameRequest));
//    }
//
//
//    @Test
//    void joinGameNegativeNoSpaceBlack() throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {
//
//        //Create User
//        RegisterRequest request = new RegisterRequest("He", "Passwor", "emai");
//        RegisterResult registerResult = service.register(request);
//
//        String authToken = registerResult.authToken();
//
//        //Create Game with AuthToken
//        CreateGameRequest createGameRequest = new CreateGameRequest(registerResult.authToken(), "new_gam");
//
//        CreateGameResult createGameResult = service.createGame(createGameRequest);
//
//        //Join the game
//        JoinGameRequest joinGameRequest = new JoinGameRequest(registerResult.authToken(), ChessGame.TeamColor.BLACK, "1");
//        JoinGameResult joinGameResult = service.joinGame(joinGameRequest);
//
//
//        //Try a new user join the same team
//        request = new RegisterRequest("Hey", "Password", "email");
//        registerResult = service.register(request);
//
//        joinGameRequest = new JoinGameRequest(registerResult.authToken(), ChessGame.TeamColor.BLACK, "1");
//
//        JoinGameRequest finalJoinGameRequest = joinGameRequest;
//        assertThrows(
//                AlreadyTakenException.class,
//                () -> service.joinGame(finalJoinGameRequest));
//    }
//    }





