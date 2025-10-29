package model;

import chess.ChessGame;

import java.util.Objects;

public record GameData (String gameID,  String gameName, String whiteUsername, String blackUsername, ChessGame game){

    public GameData newUserWhite(String newWhiteUsername) {
        //Make sure you are checking for open space outside the model
        if (!Objects.equals(this.whiteUsername, null)) {
            return null;
        } else {
            return new GameData(this.gameID, this.gameName, newWhiteUsername, this.blackUsername, this.game);
        }
    }

    public GameData newUserBlack(String newBlackUsername) {
        // Make sure you are checking for open space outside the model
        if (!Objects.equals(this.blackUsername, null)) {
            return null;
        } else {
            return new GameData(this.gameID, this.gameName, this.whiteUsername, newBlackUsername, this.game);
        }
    }







    }