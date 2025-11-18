package ui;

import java.util.Scanner;
import static ui.EscapeSequences.*;

import dataaccess.exceptions.DataAccessException;
import serverfacade.ServerFacade;
import serverfacade.ServerFacade.*;
import ui.client.*;

public class Repl {
    private final ServerFacade server;
    private State state = State.SIGNEDOUT;
    Client client;

    public Repl (String serverUrl) throws DataAccessException{
        server = new ServerFacade(serverUrl);
        client = new Prelogin(server);
        server.clear();
    }

    private void checkState () throws DataAccessException{
    }

    private void printPrompt() {
        System.out.print("\n" + SET_TEXT_COLOR_BLACK + ">>> " );
    }

    public void run() {
        System.out.println(SET_TEXT_BOLD + SET_BG_COLOR_MAGENTA + SET_TEXT_COLOR_WHITE +  WHITE_KING +
                " Welcome to the Chess Game" + BLACK_KING);
        System.out.print(SET_BG_COLOR_WHITE + SET_TEXT_BOLD + SET_TEXT_COLOR_BLACK + client.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                if(!result.equals("quit")) {
                    System.out.print(client.bgTheme() + SET_TEXT_COLOR_BLACK + SET_TEXT_BOLD + result + "\n");
                }
                String[] tokens = line.toLowerCase().split(" ");

                if (tokens[0].equals("login") | (tokens[0].equals("register"))) {
                    System.out.println( SET_BG_COLOR_LIGHT_GREY + "We are logged in!");
                    client = client.switchClient();
                }
                if (tokens[0].equals("logout")) {
                    client = client.switchClient();
                    System.out.println( SET_BG_COLOR_WHITE + "We are logged out.");
                }
                } catch (Exception e) {
                var msg = e.getMessage().toString();
                System.out.print(msg);
            }
        }
        System.out.println("Goodbye!");
    }

}
