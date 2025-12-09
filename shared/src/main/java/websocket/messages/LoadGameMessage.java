package websocket.messages;

import chess.ChessGame;

public class LoadGameMessage extends ServerMessage{

    ChessGame chessGame;
    public LoadGameMessage(ServerMessageType type) {
        super(type);
    }
}
