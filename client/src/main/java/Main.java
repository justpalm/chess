


import chess.*;
import dataaccess.exceptions.DataAccessException;
import ui.Repl;
import ui.client.BaseChessBoardDrawing;
import ui.client.BlackChessBoardDrawing;
import ui.client.WhiteChessBoardDrawing;

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