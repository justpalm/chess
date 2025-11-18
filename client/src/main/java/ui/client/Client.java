package ui.client;

import dataaccess.exceptions.DataAccessException;
import ui.EscapeSequences;

public interface Client {

    public String help();
    public String eval(String input) throws DataAccessException;
    public Client switchClient() throws DataAccessException;
    public String specialHelp();
    public String bgTheme();
    public String quit() throws DataAccessException;

}
