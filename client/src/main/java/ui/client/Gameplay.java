package ui.client;

import dataaccess.exceptions.DataAccessException;
import serverfacade.ServerFacade;

public class Gameplay implements Client{

    public Gameplay(String authToken, int gameID, ServerFacade serverFacade) {
        this.sf = serverFacade;
        this.authToken = authToken;
        this.gameID = gameID;
    }



    @Override
    public String help() {
        return "";
    }

    @Override
    public String eval(String input) throws DataAccessException {
        return "";
    }

    @Override
    public Client switchClient() throws DataAccessException {
        return null;
    }

    @Override
    public String specialHelp() {
        return "";
    }

    @Override
    public String bgTheme() {
        return "";
    }

    @Override
    public String quit() {
        return "";
    }
}
