package service;

import dataaccess.*;
import dataaccess.exceptions.*;
import service.requestsandresults.*;


public class GameService {

    private final UserDAO memoryUserData;
    private final MemoryAuthDAO memoryAuthData;
    private final GameDAO memoryGameData;

    public GameService(MemoryUserDAO memoryUserData, MemoryAuthDAO memoryAuthData, MemoryGameDAO memoryGameData) {

        this.memoryUserData = memoryUserData;
        this.memoryAuthData = memoryAuthData;
        this.memoryGameData = memoryGameData;
    }


    public CreateGameResult createGame(CreateGameRequest createGameRequest) throws BadRequestException, UnauthorizedException {
        //Checks validity of authToken
        try {
            memoryAuthData.getAuthToken(createGameRequest.authToken());
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException(e.getMessage());
        }

        String newGameId = memoryGameData.createGame(createGameRequest.gameName());

        return new CreateGameResult(newGameId);
    }

    public JoinGameResult joinGame(JoinGameRequest joinGameRequest) throws BadRequestException, UnauthorizedException, AlreadyTakenException {

        //Checks null

        //Checks validity of authToken
        try {
            memoryAuthData.getAuthToken(joinGameRequest.authToken());
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException(e.getMessage());
        }


        //Get the list to check for username
        var listAuthData = memoryAuthData.listAuthdata();
        var authData = listAuthData.get(joinGameRequest.authToken());
        String newUsername = authData.username();

        //Tries to join game
        try {
            memoryGameData.joinGame(newUsername, joinGameRequest.playerColor(), joinGameRequest.gameID());
        } catch (AlreadyTakenException e) {
            throw new AlreadyTakenException(e.getMessage());
        }

        return new JoinGameResult();

    }

    public ListGamesResult listGames(ListGamesRequest listGamesRequest) throws BadRequestException, UnauthorizedException{

        try {
            memoryAuthData.getAuthToken(listGamesRequest.authToken());
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException(e.getMessage());
        }

        return new ListGamesResult(memoryGameData.listGames());

    }

}














