package ui.client;

import dataaccess.exceptions.DataAccessException;

public class Gameplay implements Client{
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
