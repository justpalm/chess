package websocket.messages;

public class NotificationMessage extends ServerMessage {
    String message;

    public NotificationMessage(ServerMessageType type) {
        super(type);
    }
}
