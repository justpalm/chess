package dataaccess;

import chess.ChessGame;
import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.DoesNotExistException;
import model.*;

public interface GameDAO {


    public void createGame (String game_name) throws AlreadyTakenException;

    public GameData getGame(int game_id) throws DoesNotExistException;

    public void joinGame (String new_username, ChessGame.TeamColor playerColor, int game_id) throws DoesNotExistException;

    public void clearGames ();

}
