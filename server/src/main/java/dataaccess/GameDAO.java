package dataaccess;

import chess.ChessGame;
import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.BadRequestException;
import dataaccess.exceptions.UnauthorizedException;
import dataaccess.exceptions.UnauthorizedException;
import model.*;
import service.RequestsandResults.JoinGameResult;

public interface GameDAO {


    public String createGame (String game_name);

    public GameData getGame(int game_id) throws UnauthorizedException;

    public void joinGame (String new_username, ChessGame.TeamColor playerColor, String game_id) throws UnauthorizedException, BadRequestException;
    //It's void because this result is empty


    public void clearGames ();

}
