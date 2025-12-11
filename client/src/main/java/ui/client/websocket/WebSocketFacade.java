package ui.client.websocket;

import com.google.gson.Gson;
import dataaccess.exceptions.DataAccessException;
import jakarta.websocket.*;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;
import chess.ChessMove;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

//Sends UserGameCommands
//Receives Server messages back
//It's asynchronus



//need to extend Endpoint for websocket to work properly
public class WebSocketFacade extends Endpoint {

    Session session;
    ui.client.websocket.NotificationHandler notificationHandler;

    public WebSocketFacade(String url, ui.client.websocket.NotificationHandler notificationHandler) throws DataAccessException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");
            this.notificationHandler = notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    //This recieves the Json string
                    //Here you want to check the message type
                    //

                    ServerMessage notification = new Gson().fromJson(message, ServerMessage.class);

                    var type = notification.getServerMessageType();


                    //Checking the notification type
                    if (type == ServerMessage.ServerMessageType.ERROR) {
                        notification = new Gson().fromJson(message, ErrorMessage.class);
                    }

                    else if (type == ServerMessage.ServerMessageType.LOAD_GAME) {
                        notification = new Gson().fromJson(message, LoadGameMessage.class);
                    }

                    else if (type == ServerMessage.ServerMessageType.NOTIFICATION) {
                        notification = new Gson().fromJson(message, NotificationMessage.class);

                    }

                    notificationHandler.notify(notification);

                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    //Endpoint requires this method, but you don't have to do anything
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void connect(String authToken, int gameID) throws DataAccessException{
//        throw new UnsupportedOperationException("Not implemented yet");
        try {
            var command = new UserGameCommand(UserGameCommand.CommandType.CONNECT,
                    authToken, gameID);
            //This is the part where we change it to int instead of string
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    public void makeMove(String authToken, int gameID, ChessMove move) throws DataAccessException{
//        throw new UnsupportedOperationException("Not implemented yet");
        try {
            var command = new UserGameCommand(UserGameCommand.CommandType.MAKE_MOVE,
                    authToken, gameID);
            //how does the double serialization work here?
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (IOException ex){
            throw new DataAccessException(ex.getMessage());
            }
        }


    public void leave(String authToken, int gameID) throws DataAccessException{
//        throw new UnsupportedOperationException("Not implemented yet");
        try {
            var command = new UserGameCommand(UserGameCommand.CommandType.LEAVE, authToken, gameID);
            //how does the double serialization work here?
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (IOException ex){
            throw new DataAccessException(ex.getMessage());
        }
    }


    public void resign(String authToken, int gameID) throws DataAccessException {
//        throw new UnsupportedOperationException("Not implemented yet");
        try {
            var command = new UserGameCommand(UserGameCommand.CommandType.RESIGN, authToken, gameID);
            //how does the double serialization work here?
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (IOException ex){
            throw new DataAccessException(ex.getMessage());
        }
    }





    }

