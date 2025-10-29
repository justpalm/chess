package service;

import dataaccess.*;
import dataaccess.exceptions.*;
import service.RequestsandResults.*;

import java.util.Objects;


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




        //Checks
        try {
            MemoryAuthData.getAuthToken(createGameRequest.authToken());
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }

        String new_game_id = MemoryGameData.createGame(createGameRequest.gameName());

        CreateGameResult createGameResult = new CreateGameResult(new_game_id);

        return createGameResult;

    }

    public JoinGameResult join_game(JoinGameRequest joinGameRequest) throws BadRequestException, UnauthorizedException {

        //Checks null

        //Checks AuthToken validity
        MemoryAuthData.getAuthToken(joinGameRequest.authToken());

        //Tries to join game
        MemoryGameData.joinGame(joinGameRequest.authToken(), joinGameRequest.playerColor(), joinGameRequest.gameID());


        return new JoinGameResult();











    }

}














