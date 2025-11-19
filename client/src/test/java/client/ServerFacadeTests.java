package client;

import chess.ChessGame;
import dataaccess.*;
import dataaccess.exceptions.*;
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
        var port = server.run(0);
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
        var request = new RegisterRequest("player", "password", "p1@email.com");
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

        var joinGameRequest = new JoinGameRequest(authToken, null,
                createGameResult.gameID());

        try {
            facade.joinGame(joinGameRequest);
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


    @Test
    void logout() throws Exception {
        var request = new RegisterRequest("player1", "password", "p1@email.com");
        var authData = facade.register(request);
        String authToken = authData.authToken();

        var createGameRequest = new CreateGameRequest(authToken, "new_game");
        var createGameResult = facade.createGame(createGameRequest);

        var logoutRequest = new LogoutRequest(authToken);
        try {
            facade.logout(logoutRequest);
        } catch (Exception e) {
            assertTrue(false);
            return;
        }

        assertTrue(true);
        return;
    }

    @Test
    void failLogout() throws Exception {
        var request = new RegisterRequest("player1", "password", "p1@email.com");
        var authData = facade.register(request);
        String authToken = authData.authToken();
        assertTrue(authToken.length() > 10);

        //Invalid Token
        var logoutRequest = new LogoutRequest("a_string");
        assertThrows(
                DataAccessException.class,
                () -> facade.logout(logoutRequest));

    }





}







