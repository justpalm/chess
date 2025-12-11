package ui.client;

import chess.ChessGame;
import dataaccess.exceptions.DataAccessException;
import model.GameData;
import serverfacade.ServerFacade;
import service.requestsandresults.*;

import javax.xml.crypto.Data;

import static ui.EscapeSequences.*;

import java.util.Arrays;
import java.util.HashMap;


public class Postlogin implements Client{

    ServerFacade sf;
    String authToken;
    String username;
    Integer gameId;
    HashMap<Integer, GameData> gameNumTogameId = null;

    public Postlogin(String authToken, String username, ServerFacade serverFacade) {
        this.sf = serverFacade;
        this.username = username;
        this.authToken = authToken;
    }


    @Override
    public String eval(String input) throws DataAccessException{
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
                case "quit" -> quit();
                case "help" -> help();
                default -> specialHelp();
            };
        } catch (Exception ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    @Override
    public Client switchClient(String mode) throws DataAccessException {
        if (mode == "prelogin") {
            return new Prelogin(this.sf);
        }
        if (mode == "gameplay") {
            return new Gameplay(this.authToken, this.gameNumTogameId, this.gameId, this.sf);
        }

        return null;

    }

    @Override
    public String specialHelp() {
        return """
            Input unrecognized. Here's the help screen again to double-check!
            
            - create <GAMENAME>
            - list
            - join <ID> [WHITE|BLACK]
            - observe <ID>
            - logout
            - quit 
            - help 
            """;
    }

    @Override
    public String bgTheme() {
        return SET_BG_COLOR_LIGHT_GREY;
    }

    @Override
    public String quit() throws DataAccessException {
        if (logout().equals("Logout successful")) {
            return "quit";
        }
        else {
            return "Quit unsuccessful. Try logging out first?";
        }
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
            return String.format("New game created called '%s'", createGameRequest.gameName());
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
        String theString = "";
        gameNumTogameId = new HashMap<Integer, GameData>();
        for (var game : listGamesResult.games()) {
            n++;
            theString += (String.format("#" + n + " ---> Game name: " + game.gameName() + "\n" +
                    "White User: " + game.whiteUsername() + "\n" +
                    "Black User: " + game.blackUsername() + "\n"));
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
                teamColor = setTeamColor(params[1]);
            } catch (DataAccessException e) {
                throw new DataAccessException(e.getMessage());
            }

            GameData game = gameNumTogameId.get(Integer.valueOf(params[0]));
            if (game == null) {
                throw new DataAccessException("Game number invalid, please consult the list of games");
            }
            this.gameId = Integer.valueOf(params[0]);

            var joinGameRequest = new JoinGameRequest(authToken, teamColor, game.gameID());
            try {
                sf.joinGame(joinGameRequest);
            } catch (Exception e) {
                throw new DataAccessException(e.getMessage());
            }

            if (teamColor == ChessGame.TeamColor.BLACK) {
                var blackChess = new BlackChessBoardDrawing();
                blackChess.main();
            }
            if (teamColor == ChessGame.TeamColor.WHITE) {
                var whiteChess = new WhiteChessBoardDrawing();
                whiteChess.main();
            }

            return String.format("Game %s joined!", game.gameName());
        }
        throw new DataAccessException("Expected: <team color> <game number> ");
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


    private String observe(String... params) throws DataAccessException {
        if (gameNumTogameId == null) {
            throw new DataAccessException("Call 'list' first to know the game numbers. (List must be recalled " +
                    "after a new game is created.");
        }

        GameData game = gameNumTogameId.get(Integer.valueOf(params[0]));
        if (game == null) {
            throw new DataAccessException("Game number invalid, please consult the list of games");
        }

        var whiteChess = new WhiteChessBoardDrawing();
        whiteChess.main();
        return String.format("Now observing game: '%s' !", game.gameName());

    }

    private String logout() throws DataAccessException {
            var logoutRequest = new LogoutRequest(authToken);
            try {
                sf.logout(logoutRequest);
            } catch (Exception e) {
                throw new DataAccessException(e.getMessage());
            }
            return String.format("Logout successful");
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
