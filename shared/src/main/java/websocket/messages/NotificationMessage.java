package websocket.messages;

import javax.management.Notification;

public class NotificationMessage extends ServerMessage {
    String message;

    public NotificationMessage(ServerMessageType type, String notification) {
        super(type);
        this.message = notification;
    }

    public String getMessage() {
        return message;
    }
}
