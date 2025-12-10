package websocket;

import chess.ChessMove;
import com.google.gson.Gson;
import dataaccess.exceptions.UnauthorizedException;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsCloseHandler;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsConnectHandler;
import io.javalin.websocket.WsMessageContext;
import io.javalin.websocket.WsMessageHandler;
import org.eclipse.jetty.websocket.api.Session;
import websocket.ConnectionManager;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;


//Getting UserGameCommands
//Sending out Server Messages

import java.io.IOException;

public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    private final ConnectionManager connections = new ConnectionManager();

    @Override
    public void handleConnect(WsConnectContext ctx) {
        System.out.println("Websocket connected");
        ctx.enableAutomaticPings();
    }

    @Override
    public void handleMessage(WsMessageContext ctx) throws IOException {
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
//        } catch (Exception ex) { //Very unsure what this might be
//            clientMessage(ctx.session, new ErrorMessage(ServerMessage.ServerMessageType.ERROR,
//                    "Error: Unauthorized")); //Still not sure about this one
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

    private void connect(String authToken, Integer gameId, Session session) throws IOException{
        connections.add(gameId, session);

        var message = String.format("%s joined the game!");
        var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(session, notification);
    }

    private void makeMove(String authToken, Integer gameId, ChessMove move, Session session) throws IOException{
        //get game
        //get move
        //change game
        //update the game in the DAO
    }


    private void leave(String authToken, String username, Integer gameId, Session session) throws IOException{
        connections.remove(gameId, session);

        var message = String.format("%s left the game", username);
        var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(session, notification);
    }


    private void resign(String authToken, Integer gameId, Session session) throws IOException{
        connections.remove(gameId, session);


        var message = String.format("%s resigned");
        var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(session, notification);
    }


}
