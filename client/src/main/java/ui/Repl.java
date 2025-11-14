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

    public Repl (String serverUrl) {
        server = new ServerFacade(serverUrl);
        client = new Prelogin(server);
    }

    private void checkState () throws DataAccessException{
    }

    private void printPrompt() {
        System.out.print("\n" + RESET_TEXT_COLOR + ">>> " + SET_TEXT_COLOR_MAGENTA);
    }

    public void run() {
        System.out.println(SET_TEXT_BOLD + WHITE_KING + " Welcome to the Chess Game" + BLACK_KING + SET_BG_COLOR_BLUE);
        System.out.print(client.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();
            line = line.toLowerCase();

            try {
                result = client.eval(line);

                System.out.print(SET_BG_COLOR_WHITE + SET_TEXT_COLOR_MAGENTA + result);

                if (line.equals("login") | line.equals("register")) {
                    client = client.switchClient();
                }
                if (line.equals("logout")) {
                    client = client.switchClient();
                }
                } catch (Exception e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

}
