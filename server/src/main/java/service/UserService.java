package service;

import dataaccess.MemoryUserDAO;

import dataaccess.exceptions.;
import jdk.jfr.Registered;
import org.eclipse.jetty.server.Authentication;

public class UserService {

    MemoryUserDAO MemoryData = new MemoryUserDAO();


    public record RegisterRequest(String username, String password) {};
    public record



    public void register(RegisterRequest registerRequest) throws AlreadyAccessException {

        if (MemoryData.getUser(registerRequest.username) != null);
            throw new AlreadyAccessException  "User already exists with this username";






    }


    public void clear() {

    }

    public LoginResult login(LoginRequest loginRequest) {


    }
    public void logout(LogoutRequest logoutRequest) {


    }






}
