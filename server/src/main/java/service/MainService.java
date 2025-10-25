package service;

import dataaccess.*;

import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.BadRequestException;
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
        return this.userService.register(registerRequest);
    }


//    public LoginResult login(LoginRequest loginRequest) throws

}

