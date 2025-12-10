package dataaccess;

import chess.ChessGame;
import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.DataAccessException;
import dataaccess.exceptions.UnauthorizedException;
import model.*;

import java.util.Collection;

public interface GameDAO {


    String createGame (String gameName) throws DataAccessException;

    GameData getGame(String gameId) throws UnauthorizedException, DataAccessException;

    void joinGame (String newUsername, ChessGame.TeamColor playerColor, String gameId)
            throws AlreadyTakenException, UnauthorizedException, DataAccessException;
    //It's void because this result is empty

    void clearGames () throws DataAccessException, UnauthorizedException;

    Collection<GameData> listGames() throws DataAccessException, UnauthorizedException;

    void updateGame (String gameId, GameData gameData) throws DataAccessException, UnauthorizedException;

}
