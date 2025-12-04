package ui.client.websocket;

import messages.ServerMessage;

public interface NotificationHandler {
    void notify(ServerMessage notification);
}
