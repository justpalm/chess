package dataaccess;


import chess.ChessGame;
import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.BadRequestException;
import dataaccess.exceptions.UnauthorizedException;
import model.GameData;

import java.util.*;

public class MemoryGameDAO implements GameDAO {

    final private HashMap<String, GameData> games = new HashMap<>();


    @Override
    public String createGame(String game_name) {
    String game_id = String.valueOf(games.size() + 1);
    String whiteUsername = null;
    String blackUsername = null;

    GameData new_game = new GameData(game_id, game_name, whiteUsername, blackUsername, new ChessGame());

    games.put(game_id, new_game);

    return String.valueOf(game_id);
    }

    @Override
    public GameData getGame(String game_id) throws UnauthorizedException {
        if (games.get(game_id) == null) {
            throw new UnauthorizedException("Error: This games does not exists");
        }

        else {
            return games.get(game_id);
        }
    }

    @Override
    public void joinGame(String new_username, ChessGame.TeamColor playerColor, String game_id) throws UnauthorizedException, AlreadyTakenException{
        if (games.get(game_id) == null) {
            throw new UnauthorizedException("Error: This games does not exists");
        }

        GameData the_game = games.get(game_id);

        if (playerColor == ChessGame.TeamColor.WHITE) {
            if (Objects.equals(the_game.whiteUsername(), null)) {
                the_game = the_game.new_user_white(new_username);
            } else {
                throw new AlreadyTakenException("Error: Username already filled");
            }
        }

        if (playerColor == ChessGame.TeamColor.BLACK) {
            if (Objects.equals(the_game.blackUsername(), null)) {
                the_game = the_game.new_user_black(new_username);
            } else {
                throw new AlreadyTakenException("Error: Username already filled");
            }
        }

        games.put(game_id, the_game);
    }


    public void clearGames() {games.clear();}

    public HashMap<String, GameData> listGame() {
        return games;
    }


    public Collection<GameData> listGames() throws UnauthorizedException, BadRequestException{

        Collection<GameData> return_games = new ArrayList<GameData>();

        return_games = games.values();

        return return_games;
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

    public int size() {
        return games.size();
    }
}
