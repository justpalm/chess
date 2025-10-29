package model;

import chess.ChessGame;

import java.util.Objects;

public record GameData (String gameID,  String gameName, String whiteUsername, String blackUsername, ChessGame game){

    public GameData new_user_white(String new_white_username) {
        //Make sure you are checking for open space outside the model
        if (!Objects.equals(this.whiteUsername, null)) {
            return null;
        } else {
            return new GameData(this.gameID, this.gameName, new_white_username, this.blackUsername, this.game);
        }
    }

    public GameData new_user_black(String new_black_username) {
        // Make sure you are checking for open space outside the model
        if (!Objects.equals(this.blackUsername, null)) {
            return null;
        } else {
            return new GameData(this.gameID, this.gameName, this.whiteUsername, new_black_username, this.game);
        }
    }







    }