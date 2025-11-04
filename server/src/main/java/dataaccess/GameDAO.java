package dataaccess;

import chess.ChessGame;
import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.UnauthorizedException;
import model.*;

import java.util.Collection;

public interface GameDAO {


    public String createGame (String gameName);

    public GameData getGame(String gameId) throws UnauthorizedException;

    public void joinGame (String newUsername, ChessGame.TeamColor playerColor, String gameId) throws AlreadyTakenException, UnauthorizedException;
    //It's void because this result is empty

    public void clearGames ();

    public Collection<GameData> listGames();

}
