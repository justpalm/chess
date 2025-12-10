package websocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import dataaccess.exceptions.DataAccessException;
import dataaccess.exceptions.UnauthorizedException;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsCloseHandler;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsConnectHandler;
import io.javalin.websocket.WsMessageContext;
import io.javalin.websocket.WsMessageHandler;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;


//Getting UserGameCommands
//Sending out Server Messages

import java.io.IOException;
import java.util.Objects;

public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {
    AuthDAO auth;
    GameDAO game;
    UserDAO user;

    public WebSocketHandler(UserDAO user, GameDAO game, AuthDAO auth) {

        this.auth = auth;
        this.game = game;
        this.user = user;

    }
    private final ConnectionManager connections = new ConnectionManager();

    @Override
    public void handleConnect(WsConnectContext ctx) {
        System.out.println("Websocket connected");
        ctx.enableAutomaticPings();
    }

    @Override
    public void handleMessage(WsMessageContext ctx) throws IOException, UnauthorizedException, DataAccessException {
        int gameId = -1;




        try {
            UserGameCommand userGameCommand = new Gson().fromJson(ctx.message(), UserGameCommand.class);
            switch (userGameCommand.getCommandType()) {
                case CONNECT-> connect(userGameCommand.getAuthToken(), userGameCommand.getGameID(), ctx.session);
                case LEAVE-> leave(userGameCommand.getAuthToken(), userGameCommand.getGameID(), ctx.session);
                case RESIGN-> resign(userGameCommand.getAuthToken(), userGameCommand.getGameID(), ctx.session);
                case MAKE_MOVE -> {
                    MakeMoveCommand makeMoveCommand = new Gson().fromJson(ctx.message(), MakeMoveCommand.class);
                    makeMove(makeMoveCommand.getAuthToken(), makeMoveCommand.getGameID(),
                            makeMoveCommand.getTheMove(), ctx.session);
                }
            }
        } catch (UnauthorizedException ex) { //Very unsure what this might be
            clientMessage(ctx.session, new ErrorMessage(ServerMessage.ServerMessageType.ERROR,
                    "Error: Unauthorized")); //Still not sure about this one
        } catch (IOException ex) {
            ex.printStackTrace();
            clientMessage(ctx.session, new ErrorMessage(ServerMessage.ServerMessageType.ERROR,
                    "Error: " + ex.getMessage()) );
        }
    }

    @Override
    public void handleClose(WsCloseContext ctx) {
        System.out.println("Websocket closed");
    }

    private void clientMessage(Session session, ErrorMessage stringErrorMessage) throws IOException {
        String errorMes = stringErrorMessage.getErrorMessage();
        var errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, errorMes);
        connections.clientMessage(session, errorMessage);
    }

    private void connect(String authToken, Integer gameId, Session session) throws IOException, UnauthorizedException, DataAccessException {
        //Validate GameID

        try {
            this.game.getGame(String.valueOf(gameId));

        } catch (UnauthorizedException e) {
            throw new UnauthorizedException(e.getMessage());
        }

        connections.add(gameId, session);
        try {
            String username = this.auth.getUsername(authToken);
            var message = String.format("%s joined the game!", username);
            var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(gameId, session, notification);

            //Send the game now
            GameData gameData = this.game.getGame(String.valueOf(gameId));

            var gameString = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, gameData);
            connections.clientMessage(session, gameString);
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException(e.getMessage());
        }
    }

    private void makeMove(String authToken, Integer gameId, ChessMove move, Session session) throws IOException,
            DataAccessException, UnauthorizedException{
        //validate authToken

        try {
            String username = this.auth.getUsername(authToken);


            GameData data = this.game.getGame(String.valueOf(gameId));

            //Validate they are a player
            if (!Objects.equals(username, data.blackUsername()) && !Objects.equals(username, data.whiteUsername()))
            {
                throw new UnauthorizedException("Observer cannot make moves");
            }




            var theGame = data.game();

            if (move == null) {
                throw new UnauthorizedException("invalid move");
            }

            theGame.makeMove(move);

            //Make new gameData

            data = new GameData(String.valueOf(gameId), data.gameName(), data.whiteUsername(), data.blackUsername(), theGame);

            this.game.updateGame(String.valueOf(gameId), data);

            //Broadcast new game to client and everyone
            var message = new Gson().toJson(data);
            LoadGameMessage loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, data);
            connections.broadcast(gameId, session, loadGameMessage);
            connections.clientMessage(session, loadGameMessage);

            //Broadcast the move that was made
//            if (move == null) {
//                return;
//            }
            var startPosition = move.getStartPosition();
            var endPosition = move.getEndPosition();

            message = String.format("%s moved from %s to %s", username, startPosition.toString(),
                    endPosition.toString());
            NotificationMessage notificationMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(gameId, session, notificationMessage);


            //If the move caused the end of the game
            int n = 0;


            // Checkmate
            if (theGame.isInCheckmate(ChessGame.TeamColor.WHITE)) {
                message = "White is in checkmate! Game over!";
                n += 1;
            }
            if (theGame.isInCheckmate(ChessGame.TeamColor.BLACK)) {
                message = "Black is in checkmate! Game over!";
                n += 1;
            }

            //Check
            if (theGame.isInCheck(ChessGame.TeamColor.WHITE)) {
                message = "White is in check! Gasp!";
                n += 1;
            }
            if (theGame.isInCheck(ChessGame.TeamColor.BLACK)) {
                message = "Black is in check! Gasp!";
                n += 1;
            }

            //Stalemate
            if (theGame.isInStalemate(ChessGame.TeamColor.WHITE)) {
                message = "White is in stalemate! Game over!";
                n += 1;
            }
            if (theGame.isInStalemate(ChessGame.TeamColor.BLACK)) {
                message = "Black is in stalemate! Game over!";
                n += 1;
            }


            if (n != 0) {
                var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                connections.broadcast(gameId, session, notification);
                connections.clientMessage(session, notification);
            }

        } catch (UnauthorizedException | InvalidMoveException e) {
            throw new UnauthorizedException(e.getMessage());
        }
    }

    private boolean Checkmate(Integer gameId, Session session, ChessGame theGame, String message, int n) throws IOException {
        if (theGame.isInCheckmate(ChessGame.TeamColor.WHITE)) {
            message = "White is in checkmate! Game over!";
            n += 1;
        }
        if (theGame.isInCheckmate(ChessGame.TeamColor.BLACK)) {
            message = "Black is in checkmate! Game over!";
            n += 1;
        }

        //Check
        if (theGame.isInCheck(ChessGame.TeamColor.WHITE)) {
            message = "White is in check! Gasp!";
            n += 1;
        }
        if (theGame.isInCheck(ChessGame.TeamColor.BLACK)) {
            message = "Black is in check! Gasp!";
            n += 1;
        }

        //Stalemate
        if (theGame.isInStalemate(ChessGame.TeamColor.WHITE)) {
            message = "White is in stalemate! Game over!";
            n += 1;
        }
        if (theGame.isInStalemate(ChessGame.TeamColor.BLACK)) {
            message = "Black is in stalemate! Game over!";
            n += 1;
        }


        if (n != 0) {
            var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(gameId, session, notification);
            connections.clientMessage(session, notification);
        }
        return false;
    }


    private void leave(String authToken, Integer gameId, Session session) throws IOException{
        connections.remove(gameId, session);

        var message = String.format("%s left the game");
        var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(gameId, session, notification);
    }


    private void resign(String authToken, Integer gameId, Session session) throws IOException{
        connections.remove(gameId, session);


        var message = String.format("%s resigned");
        var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(gameId, session, notification);
    }


}
