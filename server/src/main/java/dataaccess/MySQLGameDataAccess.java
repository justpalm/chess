package dataaccess;

import chess.ChessGame;
import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.UnauthorizedException;
import model.GameData;

import java.util.Collection;
import java.util.List;

public class MySQLGameDataAccess implements GameDAO{
    @Override
    public String createGame(String gameName) {

//        void insertPet(Connection conn, Pet pet) throws SQLException {
//            try (var preparedStatement = conn.prepareStatement("INSERT INTO pet (name, type, friends) VALUES(?, ?, ?)")) {
//                preparedStatement.setString(1, pet.name);
//                preparedStatement.setString(2, pet.type);
//
//                // Serialize and store the friend JSON.
//                var json = new Gson().toJson(pet.friends);
//                preparedStatement.setString(3, json);
//
//                preparedStatement.executeUpdate();
//            }
//        }





        return "";
    }

    @Override
    public GameData getGame(String gameId) throws UnauthorizedException {
        return null;
    }

    @Override
    public void joinGame(String newUsername, ChessGame.TeamColor playerColor, String gameId) throws AlreadyTakenException, UnauthorizedException {

    }

    @Override
    public void clearGames() {

    }

    @Override
    public Collection<GameData> listGames() {
        return List.of();
    }
}
