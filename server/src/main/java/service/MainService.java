package service;

import dataaccess.*;

import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.BadRequestException;
import dataaccess.exceptions.DataAccessException;
import dataaccess.exceptions.UnauthorizedException;
import service.RequestsandResults.*;


public class MainService {

    private final UserService userService;
    private MemoryUserDAO memoryUserDAO;
    private MemoryAuthDAO memoryAuthDAO;
    private MemoryGameDAO memoryGameDAO;


    public MainService() {
        this.memoryUserDAO = new MemoryUserDAO();
        this.memoryAuthDAO = new MemoryAuthDAO();
        this.memoryGameDAO = new MemoryGameDAO();
        this.userService = new UserService(memoryUserDAO, memoryAuthDAO, memoryGameDAO);
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


    public RegisterResult register(RegisterRequest registerRequest) throws AlreadyTakenException, BadRequestException {

        if (registerRequest.password() == null | registerRequest.username() == null | registerRequest.email() == null) {
            throw new BadRequestException("1 or more fields not filled");
        }

        return this.userService.register(registerRequest);

    }

    public LoginResult login(LoginRequest loginRequest) throws AlreadyTakenException, UnauthorizedException, BadRequestException, DataAccessException {

       if (loginRequest.username() == null | loginRequest.username() == null) {
           throw new BadRequestException("1 or more fields not filled");
       }

       return this.userService.login(loginRequest);

    }

    public LogoutResult logout(LogoutRequest logoutRequest) throws UnauthorizedException, BadRequestException {

        if (logoutRequest.authToken() != null) {
            throw new BadRequestException("1 or more fields not filled");
        }

        return this.userService.logout(logoutRequest);
    }

}

