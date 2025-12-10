package service;

import dataaccess.*;
import dataaccess.exceptions.*;
import service.requestsandresults.*;


public class GameService {

    private final AuthDAO memoryAuthData;
    private final GameDAO memoryGameData;

    public GameService(GameDAO memoryGameData, AuthDAO memoryAuthData) {

        this.memoryAuthData = memoryAuthData;
        this.memoryGameData = memoryGameData;
    }


    public CreateGameResult createGame(CreateGameRequest createGameRequest)
            throws BadRequestException, UnauthorizedException, DataAccessException  {
        //Checks validity of authToken
        try {
            memoryAuthData.getAuthToken(createGameRequest.authToken());
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException("Error: " + e.getMessage());
        }

        String newGameId = memoryGameData.createGame(createGameRequest.gameName());

        return new CreateGameResult(newGameId);
    }

    public JoinGameResult joinGame(JoinGameRequest joinGameRequest) throws BadRequestException,
            UnauthorizedException, AlreadyTakenException, DataAccessException  {

        //Checks null

        //Checks validity of authToken
        try {
            memoryAuthData.getAuthToken(joinGameRequest.authToken());
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException("Error: " + e.getMessage());
        }


        //Get the list to check for username
        String newUsername = memoryAuthData.getUsername(joinGameRequest.authToken());

        //Tries to join game
        try {
            memoryGameData.joinGame(newUsername, joinGameRequest.playerColor(), joinGameRequest.gameID());
        } catch (AlreadyTakenException e) {
            throw new AlreadyTakenException("Error: " + e.getMessage());
        }

        return new JoinGameResult();

    }

    public ListGamesResult listGames(ListGamesRequest listGamesRequest) throws BadRequestException, UnauthorizedException, DataAccessException {

        try {
            memoryAuthData.getAuthToken(listGamesRequest.authToken());
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException("Error: " + e.getMessage());
        }

        return new ListGamesResult(memoryGameData.listGames());

    }


    //GameData updateGame method

}














