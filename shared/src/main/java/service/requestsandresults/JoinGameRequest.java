package service.requestsandresults;

import chess.ChessGame;

public record JoinGameRequest(String authToken, ChessGame.TeamColor playerColor, String gameID){

    public JoinGameRequest newAuthToken(String authToken) {

        if (this.authToken != null) {
            return new JoinGameRequest(this.authToken, this.playerColor, this.gameID);
        } else {
            return new JoinGameRequest(authToken, this.playerColor, this.gameID);
        }
    }
}
