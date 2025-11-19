import chess.*;
import dataaccess.exceptions.DataAccessException;
import server.Server;
import serverfacade.ServerFacade;
import ui.ChessBoardDrawing;
import ui.Repl;

public class Main {

    static Repl repl;
    static Server server;

    public static void main(String[] args) throws DataAccessException{
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece.toString());

        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
        ChessGame.TeamColor playercolor = ChessGame.TeamColor.WHITE;
        ChessBoardDrawing.main(playercolor);
    }

    private static void runRepl() throws DataAccessException{

        repl = new Repl(String.format("http://localhost:%d", 8080));
        repl.run();

    }

}