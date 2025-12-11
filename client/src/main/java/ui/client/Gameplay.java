package ui.client;

import dataaccess.exceptions.DataAccessException;
import model.GameData;
import serverfacade.ServerFacade;
import ui.EscapeSequences;
import ui.client.websocket.NotificationHandler;
import ui.client.websocket.WebSocketFacade;
import websocket.messages.ServerMessage;

import java.util.Arrays;
import java.util.HashMap;

public class Gameplay implements Client, NotificationHandler{

    ServerFacade sf;
    String authToken;
    String username;
    Integer gameId;
    HashMap<Integer, GameData> gameNumTogameId;
    WebSocketFacade ws;

    public Gameplay(String authToken, HashMap<Integer, GameData> gameList, Integer gameId, ServerFacade serverFacade) throws DataAccessException{
        this.sf = serverFacade;
        this.authToken = authToken;
//        new NotificationHandler();
//        this.ws = new WebSocketFacade(sf.getServerUrl(), NotificationHandler)

    }

    /// THIS NEEDS TO HAVE A NOTIFICATION HANDLER



    @Override
    public String help() {
        return """                
                - help
                - redraw Chess Board
                - leave
                - make move
                - resign
                - highlight legal moves
                """;
    }

    @Override
    public String eval(String input) throws DataAccessException {
        try {
            String[] tokens = input.toLowerCase().split(" ");
            String cmd = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "redraw chess board" -> redraw();
                case "leave" -> leave();
                case "make move" -> move(params);
                case "resign" -> resign(params);
                case "Highlight Legal Moves" -> quit();
                case "help" -> help();
                default -> specialHelp();
            };
        } catch (Exception ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    public String redraw() {
        return "chessboard";
    }

    public String leave() throws DataAccessException{
//        ws.leave(this.authToken, this.username, this.gameId, ws);
//        sf.
//        return
        return "hello";

    }

    public String move(String... params) {
        return "moved";

    }

    public String resign(String... params) {
        return "resigned";
    }




    @Override
    public Client switchClient(String mode) throws DataAccessException {
        return new Postlogin(this.authToken, this.username, this.sf);

    }

    @Override
    public String specialHelp() {
        return """
                Input unrecognized. Here's the help screen again to double-check!
                
                - Help
                - Redraw Chess Board
                - Leave
                - Make Move
                - Resign
                - Highlight Legal Moves
                """;

    }

    @Override
    public String bgTheme() {
        return "";
    }

    @Override
    public String quit() {
        return "";
    }


    @Override
    public void notify(ServerMessage notification) {
        System.out.println(EscapeSequences.SET_BG_COLOR_RED + notification.getServerMessageType());
        //This isn't correct, but it's good for now
//        printPrompt();
    }
}
