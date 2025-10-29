package service.requestsandresults;

import chess.ChessGame;

public record JoinGameRequest(String authToken, ChessGame.TeamColor playerColor, String gameID){

    public JoinGameRequest newAuthToken(String authToken) {
        return new JoinGameRequest(authToken, this.playerColor, this.gameID);
    }
}
