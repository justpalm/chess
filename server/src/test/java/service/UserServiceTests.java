package service;

import dataaccess.*;
import dataaccess.exceptions.*;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserServiceTests {

    private final UserService service = new UserService();

    @BeforeEach
    void clear() throws DataAccessException {
        service.clear();
    }


    @Test
    void registerUsers() throws DataAccessException {

        UserService.RegisterRequest request = new UserService.RegisterRequest("Hey", "Password");

        service.register(request);




    }

}