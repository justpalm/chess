package websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<Integer, Session> connections = new ConcurrentHashMap<>();

    public void add(Integer gamedId, Session session) {
        connections.put(gamedId, session);
    }

    public void remove(Integer gameId, Session session) {
        connections.remove(gameId, session);
    }

    public void broadcast(Session excludeSession, ServerMessage notification) throws IOException {
        String msg = new Gson().toJson(notification, ServerMessage.class);
        for (Session c : connections.values()) {
            if (c.isOpen()) {
                if (!c.equals(excludeSession)) {
                    c.getRemote().sendString(msg);
                }
            }
        }
    }

    public void clientMessage (Session importantSes, ErrorMessage notification) throws IOException {
        String msg = new Gson().toJson(notification, ServerMessage.class);;// I don't know if I need to have this be string or get message.
        for (Session c : connections.values()) {
            if (c.isOpen()) {
                if (c.equals(importantSes)) {
                    c.getRemote().sendString(msg);
                }
            }
        }


    }
}
