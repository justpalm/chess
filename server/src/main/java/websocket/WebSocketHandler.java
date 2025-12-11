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

        try {

            GameData gameData = this.game.getGame(String.valueOf(gameId));
            String username = this.auth.getUsername(authToken);
            ChessGame.TeamColor teamcolor = null;
            var game = gameData.game();

            if (Objects.equals(username, gameData.whiteUsername())) {
                teamcolor = ChessGame.TeamColor.WHITE;
            }

            if (Objects.equals(username, gameData.blackUsername())) {
                teamcolor = ChessGame.TeamColor.BLACK;
            }


            if (teamcolor != null) {
                //Make sure it's a move for the right team
                var chessboard = game.getBoard();
                var piece = chessboard.getPiece(move.getStartPosition());
                if (piece.getTeamColor() != teamcolor){
                    throw new UnauthorizedException("Move for the wrong team");
                }
            }


        //Validate if the game is over

        if (gameData.game().checkIsFinished()) {
            var notification = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Game " +
                    "is already over");
            connections.clientMessage(session, notification);
            return;
        }



            //Validate they are a player
            if (!Objects.equals(username, gameData.blackUsername()) && !Objects.equals(username, gameData.whiteUsername()))
            {
                throw new UnauthorizedException("Observer cannot make moves");
            }

            var theGame = gameData.game();

            if (move == null) {
                throw new UnauthorizedException("invalid move");
            }

            theGame.makeMove(move);

            //If the move caused the end of the game
            int n = 0;


            String overMessage = "";
            if (theGame.isInCheckmate(ChessGame.TeamColor.WHITE)) {
                overMessage = "White is in checkmate! Game over!";
                theGame.isFinished();
                n += 1;
            }
            if (theGame.isInCheckmate(ChessGame.TeamColor.BLACK)) {
                overMessage = "Black is in checkmate! Game over!";
                theGame.isFinished();
                n += 1;
            }

            //Check
            if (theGame.isInCheck(ChessGame.TeamColor.WHITE)) {
                overMessage = "White is in check! Gasp!";
                n += 1;
            }
            if (theGame.isInCheck(ChessGame.TeamColor.BLACK)) {
                overMessage = "Black is in check! Gasp!";
                n += 1;
            }

            //Stalemate
            if (theGame.isInStalemate(ChessGame.TeamColor.WHITE)) {
                overMessage = "White is in stalemate! Game over!";
                theGame.isFinished();
                n += 1;
            }
            if (theGame.isInStalemate(ChessGame.TeamColor.BLACK)) {
                overMessage = "Black is in stalemate! Game over!";
                theGame.isFinished();
                n += 1;
            }

            gameData = new GameData(String.valueOf(gameId), gameData.gameName(), gameData.whiteUsername(),
                    gameData.blackUsername(), theGame);

            this.game.updateGame(String.valueOf(gameId), gameData);

            LoadGameMessage loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, gameData);
            connections.broadcast(gameId, session, loadGameMessage);
            connections.clientMessage(session, loadGameMessage);

            //Here's announcing the move
            var startPosition = move.getStartPosition();
            var endPosition = move.getEndPosition();

            String message = String.format("%s moved from %s to %s", username, startPosition.toString(),
                    endPosition.toString());
            NotificationMessage notificationMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(gameId, session, notificationMessage);


            //Here's announcing if there's anything exciting going on!
            if (n != 0) {
                var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, overMessage);
                connections.broadcast(gameId, session, notification);
                connections.clientMessage(session, notification);
            }



        } catch (UnauthorizedException | InvalidMoveException e) {
            throw new UnauthorizedException(e.getMessage());
        }
    }



    private void leave(String authToken, Integer gameId, Session session) throws IOException, UnauthorizedException {


        try {

            GameData newData;

            GameData data = this.game.getGame(String.valueOf(gameId));
            String username = this.auth.getUsername(authToken);

            if (username.equals(data.whiteUsername())) {

                newData = new GameData(String.valueOf(gameId), data.gameName(),null,
                        data.blackUsername(), data.game());
            }


            else if (username.equals(data.blackUsername())) {

                newData = new GameData(String.valueOf(gameId), data.gameName(), data.whiteUsername(),
                        null, data.game());
            }

            else {
                // This means they are an observer

                var message = String.format("%s left the game", username);
                var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                connections.broadcast(gameId, session, notification);
                connections.remove(gameId, session);
                return;
            }



            var message = String.format("%s left the game", username);
            var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(gameId, session, notification);
            connections.remove(gameId, session);

            //update the DAO
            this.game.updateGame(String.valueOf(gameId), newData);


        } catch (Exception e) {
            throw new UnauthorizedException(e.getMessage());
        }
    }


    private void resign(String authToken, Integer gameId, Session session) throws IOException, UnauthorizedException{



        try {
            GameData newData;
            GameData data = this.game.getGame(String.valueOf(gameId));
            String username = this.auth.getUsername(authToken);

            var game = data.game();

            //No observers
            if (!Objects.equals(username, data.blackUsername()) && !Objects.equals(username, data.whiteUsername())) {
                throw new UnauthorizedException("Observer can't resign");
            }



            game.isFinished();

            newData = new GameData(String.valueOf(gameId), data.gameName(), data.whiteUsername(),
                    data.blackUsername(), game);

            var message = String.format("%s resigned", username);
            var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(gameId, session, notification);
            connections.clientMessage(session, notification);
            connections.remove(gameId, session);

            //update the DAO
            this.game.updateGame(String.valueOf(gameId), newData);


        } catch (Exception e) {
            throw new UnauthorizedException(e.getMessage());
        }

    }


}
