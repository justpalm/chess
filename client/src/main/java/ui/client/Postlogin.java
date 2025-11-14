package ui.client;

import chess.ChessGame;
import dataaccess.exceptions.DataAccessException;
import jdk.jshell.spi.ExecutionControl;
import model.GameData;
import serverfacade.ServerFacade;
import service.requestsandresults.*;
import ui.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Postlogin implements Client{

    ServerFacade sf;
    String authToken;
    HashMap<Integer, GameData> gameNumTogameId = null;

    public Postlogin(String authToken, ServerFacade serverFacade) {
        this.sf = serverFacade;
        this.authToken = authToken;


    }




    @Override
    public String eval(String input) {
        try {
            String[] tokens = input.toLowerCase().split(" ");
            String cmd = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "create" -> create(params);
                case "list" -> list();
                case "join" -> join(params);
                case "observe" -> observe(params);
                case "logout" -> logout();
                case "help" -> help();
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    @Override
    public Client switchClient() throws DataAccessException {
        return null;
    }

    private String create(String... params) throws DataAccessException {
        if (params.length == 1) {
            var createGameRequest = new CreateGameRequest(authToken, params[0]);
            try {
                sf.createGame(createGameRequest);
            } catch (Exception e) {
                throw new DataAccessException(e.getMessage());
            }
            //THis just resets the list since a new game is created and the numbers are out of whack now
            gameNumTogameId = null;
            return String.format("Game created called %s", createGameRequest.gameName());
        }
        throw new DataAccessException("Expected: <GAMENAME>");
    }


    private String list() throws DataAccessException {
        ListGamesResult listGamesResult;
        var listGamesRequest = new ListGamesRequest(authToken);
        try {
            listGamesResult = sf.listGames(listGamesRequest);
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
        int n = 0;
        String theString = null;
        gameNumTogameId = new HashMap<Integer, GameData>();
        for (var game : listGamesResult.games()) {
            n++;
            theString += String.format(String.valueOf(n) + "--->" + game.gameName() + "\n" +
                    "White User: " + game.whiteUsername() + "\n" +
                    "Black User: " + game.blackUsername()) + "\n";
            gameNumTogameId.put(n, game);
        }

        if (theString == null) {
            return ("There are no games to list. Try creating a game!");
        }
        else {
            return theString;
        }
    }

    private String join(String... params) throws DataAccessException {
        if (gameNumTogameId == null) {
            throw new DataAccessException("Call 'list' first to know the game numbers. (List must be recalled" +
                    "after a new game is created.");
        }
        ChessGame.TeamColor teamColor;
        if (params.length == 2) {
            try {
                teamColor = setTeamColor(params[0]);
            } catch (DataAccessException e) {
                throw new DataAccessException(e.getMessage());
            }

            GameData game = gameNumTogameId.get(params[1]);
            if (game == null)
                throw new DataAccessException("Game number invalid, please consult the list of games");

            var joinGameRequest = new JoinGameRequest(authToken, teamColor, game.gameID());
            try {
                sf.joinGame(joinGameRequest);
            } catch (Exception e) {
                throw new DataAccessException(e.getMessage());
            }
            drawChessboard(game);
            return String.format("Game %s joined!", game.gameName());
        }
        throw new DataAccessException("Expected: <teamcolor> <gamenumber> ");
    }

    private ChessGame.TeamColor setTeamColor (String input) throws DataAccessException{
        input = input.toLowerCase();
        if (input.equals("white")) {
            return ChessGame.TeamColor.WHITE;
        }
        if (input.equals("black")) {
            return ChessGame.TeamColor.BLACK;
        }
        throw new DataAccessException("Team Color not correctly specified");
        }

        private void drawChessboard(GameData game) throws DataAccessException {
            return;
    }

    private String observe(String... params) throws DataAccessException {
        if (gameNumTogameId == null) {
            throw new DataAccessException("Call 'list' first to know the game numbers. (List must be recalled" +
                    "after a new game is created.");
        }

        GameData game = gameNumTogameId.get(params[0]);
        if (game == null)
            throw new DataAccessException("Game number invalid, please consult the list of games");

        drawChessboard(game);
        return String.format("Now observing game: %s !", game.gameName());

    }

    private String logout() throws DataAccessException {
            var logoutRequest = new LogoutRequest(authToken);
            try {
                sf.logout(logoutRequest);
            } catch (Exception e) {
                throw new DataAccessException(e.getMessage());
            }
            return String.format("Logout succesful");
    }


    @Override
    public String help() {
        return """
            - create <GAMENAME>
            - list
            - join <ID> [WHITE|BLACK]
            - observe <ID>
            - logout
            - quit 
            - help 
            """;
    }




}
