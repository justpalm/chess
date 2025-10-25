package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import dataaccess.UserDAO;
import dataaccess.exceptions.*;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.RequestsandResults.*;

import static org.junit.jupiter.api.Assertions.*;


public class UserServiceTests {

    MemoryUserDAO memoryUserData = new MemoryUserDAO();
    MemoryAuthDAO memoryAuthData = new MemoryAuthDAO();
    MemoryGameDAO memoryGameData = new MemoryGameDAO();

    private final MainService service = new MainService();
//    private final UserService service = new UserService(memoryUserData, memoryAuthData, memoryGameData);

    @BeforeEach
    void clear() throws DataAccessException {

        memoryUserData = new MemoryUserDAO();
        memoryAuthData = new MemoryAuthDAO();
        memoryGameData = new MemoryGameDAO();

        service.clear();
    }


    @Test
    void registerUsers() throws AlreadyTakenException, BadRequestException {

        //

        var user = new UserData("Hey", "password", "email");
        memoryUserData.createUser(user);

        RegisterRequest request = new RegisterRequest("Hey", "Password", "email");
        service.register(request);


        assertEquals(memoryUserData, service.getUserDAO());



        




    }

}