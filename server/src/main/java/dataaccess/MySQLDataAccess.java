package dataaccess;

import chess.ChessGame;
import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.UnauthorizedException;
import model.GameData;
import model.UserData;

public class MySQLDataAccess implements UserDAO, AuthDAO, GameDAO{
    @Override
    public String createAuth(String username) throws UnauthorizedException {
        return "";
    }

    @Override
    public String getAuthToken(String authToken) throws UnauthorizedException {
        return "";
    }

    @Override
    public void deleteAuthToken(String authToken) throws UnauthorizedException {

    }

    @Override
    public void clearAuthTokens() {

    }

    @Override
    public String createGame(String gameName) {
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
    public UserData getUser(String username) throws UnauthorizedException {
        return null;
    }

    @Override
    public UserData createUser(String username, String password, String email) throws AlreadyTakenException {
        return null;
    }

    @Override
    public void clearUsers() {
            }





}
