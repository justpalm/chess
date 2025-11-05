package dataaccess;

import chess.ChessGame;
import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.DataAccessException;
import dataaccess.exceptions.UnauthorizedException;
import model.*;

import java.util.Collection;

public interface GameDAO {


    public String createGame (String gameName) throws DataAccessException;

    public GameData getGame(String gameId) throws UnauthorizedException, DataAccessException;

    public void joinGame (String newUsername, ChessGame.TeamColor playerColor, String gameId) throws AlreadyTakenException, UnauthorizedException, DataAccessException;
    //It's void because this result is empty

    public void clearGames () throws DataAccessException, UnauthorizedException;

    public Collection<GameData> listGames() throws DataAccessException, UnauthorizedException;

}
