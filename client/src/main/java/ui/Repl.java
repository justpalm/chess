package ui;

import java.util.Scanner;
import static ui.EscapeSequences.*;

import serverfacade.ServerFacade;
import serverfacade.ServerFacade.*;
import ui.client.*;

public class Repl {
    Client client;

    public Repl (String serverUrl) {
        ServerFacade server = new ServerFacade(serverUrl);
        client = new Prelogin(server);
//        server.clear();
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

                String[] tokens = line.toLowerCase().split(" ");

                if (tokens[0].equals("logout")) {
                    client = client.switchClient("help");
                }

                if (tokens[0].equals("login") | (tokens[0].equals("register"))) {
                    client = client.switchClient("help");
                }

                if (tokens[0].equals("join")) {
                    client = client.switchClient("gameplay");
                }

                if (tokens[0].equals("leave")){
                    client = client.switchClient("help");
                }

                if(!result.equals("quit")) {
                    System.out.print(client.bgTheme() + SET_TEXT_COLOR_BLACK + SET_TEXT_BOLD + result + "\n");
                }

                } catch (Exception e) {
                var msg = e.getMessage();
                System.out.print(msg);
            }
        }
        System.out.println("Goodbye!");
    }

}
