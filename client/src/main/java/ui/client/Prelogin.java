package ui.client;

import dataaccess.exceptions.DataAccessException;
import org.junit.jupiter.api.AfterEach;
import serverfacade.ServerFacade;
import service.requestsandresults.*;
import ui.EscapeSequences;

import java.util.Arrays;

import static ui.EscapeSequences.*;

public class Prelogin implements Client{

    ServerFacade sf;
    String authToken = null;

    public Prelogin(ServerFacade serverFacade) {
        this.sf = serverFacade;

    }


    @Override
    public String eval(String input) throws DataAccessException {
        try {
            String[] tokens = input.toLowerCase().split(" ");
            String cmd = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "register" -> register(params);
                case "login" -> login(params);
                case "help" -> help();
                case "quit" -> quit();
                default -> specialHelp();
            };
        } catch (DataAccessException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }


    public Client switchClient() {

        return new Postlogin(authToken, sf);

    }

    @Override
    public String specialHelp() {
        return """
                Input unrecognized. Here's the help screen again to double-check!
                
                - register <username> <password> <email>
                - login <username> <password>
                - quit
                - help
                """;
    }

    @Override
    public String bgTheme() {
        return SET_BG_COLOR_WHITE;
    }

    @Override
    public String quit() {
        return "quit";
    }


    private String register(String... params) throws DataAccessException {
        if (params.length == 3) {
            RegisterResult registerResult;
            var registerRequest = new RegisterRequest(params[0], params[1], params[2]);
            try {
                registerResult = sf.register(registerRequest);
            } catch (Exception e) {
                throw new DataAccessException(e.getMessage());
            }
            authToken = registerResult.authToken();
            return String.format("Your signed in as '%s'", registerRequest.username());
        }
        throw new DataAccessException("Expected: <username>, <password>, <email>");
    }

    private String login(String... params) throws DataAccessException {
        if (params.length == 2) {
            var loginRequest = new LoginRequest(params[0], params[1]);
            try {
                sf.login(loginRequest);
            } catch (Exception e) {
                throw new DataAccessException(e.getMessage());
            }
            return String.format("You signed in as %s.", loginRequest.username());
        }
        throw new DataAccessException("Expected: <username>, <password>");
    }




    @Override
    public String help() {
        return """
                - register <username> <password> <email>
                - login <username> <password>
                - quit
                - help
                """;
    }

}
