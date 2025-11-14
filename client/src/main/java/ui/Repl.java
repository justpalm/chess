package ui;

import java.util.Scanner;
import static ui.EscapeSequences.*;

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


    private void checkState () {
        if (this.state == State.SIGNEDIN) {
            client = new Postlogin(server);
        }
    }


    public void run() {
        checkState();
        System.out.println(WHITE_KING + " Welcome to the Chess Game" + BLACK_KING);
        System.out.print(client.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = eval(line);
                System.out.print(BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }












}
