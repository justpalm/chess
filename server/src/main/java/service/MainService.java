package service;

import dataaccess.*;

import dataaccess.exceptions.*;
import service.requestsandresults.*;

import javax.xml.crypto.Data;
import java.util.Objects;


public class MainService {

    private final GameService gameService;
    private final UserService userService;
    private final UserDAO memoryUserData;
    private final GameDAO memoryGameData;
    private final AuthDAO memoryAuthData;


    public MainService(UserDAO userDAO, GameDAO gameDAO, AuthDAO authDAO) {
        this.memoryUserData = userDAO;
        this.memoryGameData = gameDAO;
        this.memoryAuthData = authDAO;
        this.userService = new UserService(userDAO, authDAO);
        this.gameService = new GameService(userDAO, gameDAO, authDAO);
    }


    public UserDAO getUserDAO() {
        return memoryUserData;
    }

    public AuthDAO getAuthDAO() {
        return memoryAuthData;
    }

    public GameDAO getGameDAO() {
        return memoryGameData;
    }

    public void clear()  {
        try {

            memoryUserData.clearUsers();
            memoryAuthData.clearAuthTokens();
            memoryGameData.clearGames();

        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }


    public RegisterResult register(RegisterRequest registerRequest) throws AlreadyTakenException, BadRequestException, UnauthorizedException {

        if (registerRequest.password() == null | registerRequest.username() == null | registerRequest.email() == null) {
            throw new BadRequestException("Error: 1 or more fields not filled");
        }

        return this.userService.register(registerRequest);

    }

    public LoginResult login(LoginRequest loginRequest)
            throws UnauthorizedException, BadRequestException, DataAccessException {

        //Checks if the fields are null or not
        if (loginRequest.username() == null | loginRequest.password() == null) {
            throw new BadRequestException("Error: 1 or more fields not filled");
        }
        return this.userService.login(loginRequest);
    }

    public LogoutResult logout(LogoutRequest logoutRequest) throws UnauthorizedException, BadRequestException {

        //Checks nullity of fields
        if (logoutRequest.authToken() == null) {
            throw new BadRequestException("Error: 1 or more fields not filled");
        }
        return this.userService.logout(logoutRequest.authToken());

    }

    public CreateGameResult createGame(CreateGameRequest createGameRequest) throws BadRequestException, UnauthorizedException {
        if (Objects.equals(createGameRequest.authToken(), null) | Objects.equals(createGameRequest.gameName(), null)) {
            throw new BadRequestException("Error: 1 or more fields not filled correctly.");
        }
        return gameService.createGame(createGameRequest);

    }

    public JoinGameResult joinGame(JoinGameRequest joinGameRequest) throws BadRequestException, UnauthorizedException, AlreadyTakenException {


        if (Objects.equals(joinGameRequest.authToken(), null) | Objects.equals(joinGameRequest.playerColor(), null)
                | Objects.equals(joinGameRequest.gameID(), null)) {
            throw new BadRequestException("Error: 1 or more fields not filled correctly.");
        }



        return gameService.joinGame(joinGameRequest);
    }


    public ListGamesResult listGames(ListGamesRequest listGamesRequest) throws BadRequestException, UnauthorizedException{
        if (Objects.equals(listGamesRequest.authToken(), null)) {
            throw new BadRequestException("Error: 1 or more fields not filled correctly");
        }
        return gameService.listGames(listGamesRequest);
    }
}

