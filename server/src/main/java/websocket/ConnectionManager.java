package websocket;

import com.google.gson.Gson;
import dataaccess.exceptions.UnauthorizedException;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<Integer, ArrayList<Session>> listConnections = new ConcurrentHashMap<>();

    public void add(Integer gameId, Session session) throws UnauthorizedException {


        var list = listConnections.get(gameId);
        if (list == null) {
            list = new ArrayList<>();
            list.add(session);
        }
        else {
            list.add(session);
        }
        listConnections.put(gameId, list);
    }


    public void remove(Integer gameId, Session session) {

        var list = listConnections.get(gameId);
        list.remove(session);

        listConnections.put(gameId, list);



    }

    public void broadcast(Integer gamedId, Session excludeSession, ServerMessage notification) throws IOException {
        String msg = "";

        if (notification.getServerMessageType() == ServerMessage.ServerMessageType.NOTIFICATION) {
            msg = new Gson().toJson(notification, NotificationMessage.class);
        }

        if (notification.getServerMessageType() == ServerMessage.ServerMessageType.LOAD_GAME) {
            msg = new Gson().toJson(notification, LoadGameMessage.class);
        }

        var connections = listConnections.get(gamedId);
        for (Session c : connections) {
            if (c.isOpen()) {
                if (!c.equals(excludeSession)) {
                    c.getRemote().sendString(msg);
                }
            }
        }
    }

    public void clientMessage (Session importantSes, ServerMessage notification) throws IOException {

        if (notification.getServerMessageType() == ServerMessage.ServerMessageType.ERROR) {
            String msg = new Gson().toJson(notification, ErrorMessage.class);
                if (importantSes.isOpen()) {
                    importantSes.getRemote().sendString(msg);
                    }
                }


        if (notification.getServerMessageType() == ServerMessage.ServerMessageType.LOAD_GAME) {
            String msg = new Gson().toJson(notification, LoadGameMessage.class);
                if (importantSes.isOpen()) {
                    importantSes.getRemote().sendString(msg);
                }
            }
            }

        }

