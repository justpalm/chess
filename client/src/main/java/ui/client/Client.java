package ui.client;

import dataaccess.exceptions.DataAccessException;

public interface Client {

    public String help();
    public String eval(String input) throws DataAccessException;
    public Client switchClient() throws DataAccessException;
    public String specialHelp();
}
