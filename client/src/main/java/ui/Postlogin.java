package ui;

import java.util.Arrays;

public class Postlogin {







    public String eval(String input) {
        try {
            String[] tokens = input.toLowerCase().split(" ");
            String cmd = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "create" -> create(params);
                case "list" -> list(params);
                case "join" -> join(params);
                case "observe" -> observe(params);
                case "logout" -> logout();
                case "quit" -> quit();
                case "help" -> help();
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }


    public String help() {
        if (state == State.SIGNEDOUT) {
            return """
                - signIn <yourname>
                - quit
                """;
        }
        return """
            - create <NAME>
            - list
            - join <ID> [WHITE|BLACK]
            - observe <ID>
            - logout
            - quit 
            - help 
            """;
    }







}
