package dataaccess;


import chess.ChessGame;
import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.DoesNotExistException;
import model.GameData;

import java.util.HashMap;
import java.util.Objects;

public class MemoryGameDAO implements GameDAO {

    final private HashMap<Integer, GameData> games = new HashMap<>();


    @Override
    public void createGame(String game_name) throws AlreadyTakenException {
    int game_id = games.size() + 1;
    String whiteUsername = "";
    String blackUsername = "";

    GameData new_game = new GameData(game_id, whiteUsername, blackUsername, game_name, new ChessGame());

    games.put(game_id, new_game);

    }

    @Override
    public GameData getGame(int game_id) throws DoesNotExistException {
        if (games.get(game_id) == null) {
            throw new DoesNotExistException("This games does not exists");
        }

        else {
            return games.get(game_id);
        }
    }


    @Override
    public void joinGame(String new_username, ChessGame.TeamColor playerColor, int game_id) throws DoesNotExistException {

        //Make sure you get the user from the service

        if (games.get(game_id) == null) {
            throw new DoesNotExistException("This games does not exists");
        }

        else {
            GameData the_game= games.get(game_id);
            if (playerColor == ChessGame.TeamColor.WHITE) {
                if (Objects.equals(the_game.whiteUsername(), "")){
                    the_game.new_user_white(new_username);
                }
            }
        }
    }

    public void clearGames() {games.clear();}

    public HashMap<Integer, GameData> listGame() {
        return games;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemoryGameDAO that = (MemoryGameDAO) o;
        return Objects.equals(games, that.games);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(games);
    }
}
