package dataaccess;

import chess.ChessGame;
import model.*;

import java.util.Collection;
import java.util.HashMap;
import chess.ChessGame;

public interface GameDAO {

    final private HashMap<Integer, ChessGame> users = new HashMap<>();

    public void createGame (int game_id) throws AlreadyExistsException;

    public ChessGame getGame(int game_id);

    public HashMap<

    public updateGame





}
