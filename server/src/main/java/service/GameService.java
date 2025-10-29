package service;

import dataaccess.*;
import dataaccess.exceptions.*;
import service.requestsandresults.*;


public class GameService {

    private final MemoryUserDAO MemoryUserData;
    private final MemoryAuthDAO MemoryAuthData;
    private final MemoryGameDAO MemoryGameData;

    public GameService(MemoryUserDAO memoryUserData, MemoryAuthDAO memoryAuthData, MemoryGameDAO memoryGameData) {

        this.MemoryUserData = memoryUserData;
        this.MemoryAuthData = memoryAuthData;
        this.MemoryGameData = memoryGameData;
    }


    public CreateGameResult create_game(CreateGameRequest createGameRequest) throws BadRequestException, UnauthorizedException {
        //Checks validity of authToken
        try {
            MemoryAuthData.getAuthToken(createGameRequest.authToken());
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException(e.getMessage());
        }

        String new_game_id = MemoryGameData.createGame(createGameRequest.gameName());

        return new CreateGameResult(new_game_id);
    }

    public JoinGameResult join_game(JoinGameRequest joinGameRequest) throws BadRequestException, UnauthorizedException, AlreadyTakenException {

        //Checks null

        //Checks validity of authToken
        try {
            MemoryAuthData.getAuthToken(joinGameRequest.authToken());
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException(e.getMessage());
        }


        //Get the list to check for username
        var listAuthData = MemoryAuthData.listAuthdata();
        var authData = listAuthData.get(joinGameRequest.authToken());
        String new_username = authData.username();

        //Tries to join game
        try {
            MemoryGameData.joinGame(new_username, joinGameRequest.playerColor(), joinGameRequest.gameID());
        } catch (AlreadyTakenException e) {
            throw new AlreadyTakenException(e.getMessage());
        }

        return new JoinGameResult();

    }

    public ListGamesResult listGames(ListGamesRequest listGamesRequest) throws BadRequestException, UnauthorizedException{

        try {
            MemoryAuthData.getAuthToken(listGamesRequest.authToken());
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException(e.getMessage());
        }

        return new ListGamesResult(MemoryGameData.listGames());

    }

}














