package ui.client.websocket;

import com.google.gson.Gson;
import dataaccess.exceptions.DataAccessException;
import jakarta.websocket.*;
import commands.UserGameCommand;
import jdk.jshell.spi.ExecutionControl;
import messages.ServerMessage;
import service.requestsandresults.JoinGameRequest;
import chess.ChessMove;
import service.requestsandresults.LogoutRequest;
import service.requestsandresults.LogoutResult;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


//SENDS MESSAGES FROM CLIENT TO SERVER
//RESPONISBLE FOR OPENING A CONNECTION

//need to extend Endpoint for websocket to work properly
public class WebSocketFacade extends Endpoint {

    Session session;
    NotificationHandler notificationHandler;

    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws DataAccessException {
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
                    ServerMessage notification = new Gson().fromJson(message, ServerMessage.class);
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
            var command = new UserGameCommand(UserGameCommand.CommandType.LEAVE, authToken, gameID);
            //how does the double serialization work here?
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (IOException ex){
            throw new DataAccessException(ex.getMessage());
        }
    }


}


    public void enterPetShop(String visitorName) throws ResponseException {
        try {
            var action = new Action(Action.Type.ENTER, visitorName); //CLIENT TO SERVER MESSAGE, GAMECOMMAND
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
        }
    }

    public void leavePetShop(String visitorName) throws ResponseException {
        try {
            var action = new Action(Action.Type.EXIT, visitorName);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
        }
    }
    }

