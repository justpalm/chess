package service.RequestsandResults;

import chess.ChessGame;

public record JoinGameRequest(String authToken, ChessGame.TeamColor playerColor, String gameID){

    public JoinGameRequest new_authToken (String authToken) {
        return new JoinGameRequest(authToken, this.playerColor, this.gameID);
    }
}
