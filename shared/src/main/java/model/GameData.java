package model;

import chess.ChessGame;

import java.util.Objects;

public record GameData (int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game){

    public GameData new_user_white(String new_white_username) {
        //Make sure you are checking for open space outside the model
        if (!Objects.equals(this.whiteUsername, "")) {
            return null;
        } else {
            return new GameData(this.gameID, new_white_username, this.blackUsername, this.gameName, this.game);
        }
    }

    public GameData new_user_black(String new_black_username) {
        // Make sure you are checking for open space outside the model
        if (!Objects.equals(this.blackUsername, "")) {
            return null;
        } else {
            return new GameData(this.gameID, this.whiteUsername, new_black_username, this.gameName, this.game);
        }
    }







    }