package service;

import dataaccess.*;

import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.BadRequestException;
import dataaccess.exceptions.DataAccessException;
import dataaccess.exceptions.UnauthorizedException;
import service.requestsandresults.*;

import java.util.Objects;


public class MainService {

    private final GameService gameService;
    private final UserService userService;
    private MemoryUserDAO memoryUserDAO;
    private MemoryAuthDAO memoryAuthDAO;
    private MemoryGameDAO memoryGameDAO;


    public MainService() {
        this.memoryUserDAO = new MemoryUserDAO();
        this.memoryAuthDAO = new MemoryAuthDAO();
        this.memoryGameDAO = new MemoryGameDAO();
        this.userService = new UserService(memoryUserDAO, memoryAuthDAO, memoryGameDAO);
        this.gameService = new GameService(memoryUserDAO, memoryAuthDAO, memoryGameDAO);
    }


    public MemoryUserDAO getUserDAO() {
        return memoryUserDAO;
    }

    public MemoryAuthDAO getAuthDAO() {
        return memoryAuthDAO;
    }

    public MemoryGameDAO getGameDAO() {
        return memoryGameDAO;
    }

    public void clear() {
        memoryUserDAO.clearUsers();
        memoryAuthDAO.clearAuthTokens();
        memoryGameDAO.clearGames();
    }


    public RegisterResult register(RegisterRequest registerRequest) throws AlreadyTakenException, BadRequestException, UnauthorizedException {

        if (registerRequest.password() == null | registerRequest.username() == null | registerRequest.email() == null) {
            throw new BadRequestException("Error: 1 or more fields not filled");
        }

        return this.userService.register(registerRequest);

    }

    public LoginResult login(LoginRequest loginRequest)
            throws UnauthorizedException, BadRequestException, DataAccessException {

        if (loginRequest.username() == null | loginRequest.password() == null) {
            throw new BadRequestException("Error: 1 or more fields not filled");
        }
        return this.userService.login(loginRequest);

    }

    public LogoutResult logout(LogoutRequest logoutRequest) throws UnauthorizedException, BadRequestException {

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

