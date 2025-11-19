import chess.*;
import dataaccess.exceptions.DataAccessException;
import ui.Repl;

public class Main {

    static Repl repl;

    public static void main(String[] args) throws DataAccessException{
        runRepl();
    }


    private static void runRepl() throws DataAccessException{

        repl = new Repl(String.format("http://localhost:%d", 8080));
        repl.run();

    }

}