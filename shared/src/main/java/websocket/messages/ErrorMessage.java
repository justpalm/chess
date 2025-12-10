package websocket.messages;

public class ErrorMessage extends ServerMessage {
    String errorMessage;

    public ErrorMessage(ServerMessageType type, String notification) {
        super(type);
        this.errorMessage = notification;
    }

    public String getErrorMessage() {
        return errorMessage;
    }





}
