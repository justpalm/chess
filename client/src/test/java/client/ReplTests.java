package client;

import dataaccess.*;
import dataaccess.exceptions.*;
import org.junit.jupiter.api.*;
import server.Server;
import service.requestsandresults.*;
import ui.Repl;

import serverfacade.ServerFacade;
import server.Server;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class ReplTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() throws DataAccessException {
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
    void testRepl() throws DataAccessException {
        int port = 8080;
        String url = (String.format("http://localhost:%d", port));
        Repl repl = new Repl(url);
    }
}