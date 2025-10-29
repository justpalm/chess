package dataaccess;


import chess.ChessGame;
import dataaccess.exceptions.*;
import model.GameData;

import java.util.*;

public class MemoryGameDAO implements GameDAO {

    final private HashMap<String, GameData> games = new HashMap<>();


    @Override
    public String createGame(String gameName) {
    String gameId = String.valueOf(games.size() + 1);

    GameData newGame = new GameData(gameId, gameName, null, null, new ChessGame());

    games.put(gameId, newGame);

    return gameId;
    }

    @Override
    public GameData getGame(String gameId) throws UnauthorizedException {
        if (games.get(gameId) == null) {
            throw new UnauthorizedException("Error: This games does not exists");
        }

        else {
            return games.get(gameId);
        }
    }

    @Override
    public void joinGame(String newUsername, ChessGame.TeamColor playerColor, String gameId) throws UnauthorizedException, AlreadyTakenException{
        if (games.get(gameId) == null) {
            throw new UnauthorizedException("Error: This games does not exists");
        }

        GameData theGame = games.get(gameId);

        if (playerColor == ChessGame.TeamColor.WHITE) {
            if (Objects.equals(theGame.whiteUsername(), null)) {
                theGame = theGame.newUserWhite(newUsername);
            } else {
                throw new AlreadyTakenException("Error: Username already filled");
            }
        }

        if (playerColor == ChessGame.TeamColor.BLACK) {
            if (Objects.equals(theGame.blackUsername(), null)) {
                theGame = theGame.newUserBlack(newUsername);
            } else {
                throw new AlreadyTakenException("Error: Username already filled");
            }
        }

        games.put(gameId, theGame);
    }


    public void clearGames() {games.clear();}


    public Collection<GameData> listGames() {

        return games.values();
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
