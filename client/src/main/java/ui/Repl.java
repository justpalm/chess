package ui;

import java.util.Scanner;
import static ui.EscapeSequences.*;

public class Repl {



    public Repl () {





    }









    public void run() {
        System.out.println(WHITE_KING + " Welcome to the GAMES");
        System.out.print(help());

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
