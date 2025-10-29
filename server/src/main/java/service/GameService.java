package service;

import dataaccess.*;
import dataaccess.exceptions.*;
import service.requestsandresults.*;


public class GameService {

    private final MemoryUserDAO memoryUserDAO;
    private final MemoryAuthDAO memoryAuthDAO;
    private final MemoryGameDAO memoryGameDAO;

    public GameService(MemoryUserDAO memoryUserDAO, MemoryAuthDAO memoryAuthDAO, MemoryGameDAO memoryGameDAO) {

        this.memoryUserDAO = memoryUserDAO;
        this.memoryAuthDAO = memoryAuthDAO;
        this.memoryGameDAO = memoryGameDAO;
    }


    public CreateGameResult createGame(CreateGameRequest createGameRequest) throws BadRequestException, UnauthorizedException {
        //Checks validity of authToken
        try {
            memoryAuthDAO.getAuthToken(createGameRequest.authToken());
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException(e.getMessage());
        }

        String newGameId = memoryGameDAO.createGame(createGameRequest.gameName());

        return new CreateGameResult(newGameId);
    }

    public JoinGameResult joinGame(JoinGameRequest joinGameRequest) throws BadRequestException, UnauthorizedException, AlreadyTakenException {

        //Checks null

        //Checks validity of authToken
        try {
            memoryAuthDAO.getAuthToken(joinGameRequest.authToken());
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException(e.getMessage());
        }


        //Get the list to check for username
        var listAuthData = memoryAuthDAO.listAuthdata();
        var authData = listAuthData.get(joinGameRequest.authToken());
        String newUsername = authData.username();

        //Tries to join game
        try {
            memoryGameDAO.joinGame(newUsername, joinGameRequest.playerColor(), joinGameRequest.gameID());
        } catch (AlreadyTakenException e) {
            throw new AlreadyTakenException(e.getMessage());
        }

        return new JoinGameResult();

    }

    public ListGamesResult listGames(ListGamesRequest listGamesRequest) throws BadRequestException, UnauthorizedException{

        try {
            memoryAuthDAO.getAuthToken(listGamesRequest.authToken());
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException(e.getMessage());
        }

        return new ListGamesResult(memoryGameDAO.listGames());

    }

}














