package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMoveCalculator implements PieceMoveCalculator {

    public static int resetRow(ChessPosition oldPosition) {
        return oldPosition.getRow();
    }

    public static int resetCol(ChessPosition oldPosition) {
        return oldPosition.getColumn();
    }


    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition oldPosition) {

        Collection<ChessMove> possibleMoves = new ArrayList<>();
        ChessPiece piece = board.getPiece(oldPosition);
        ChessGame.TeamColor color = piece.getTeamColor();

        //Set Values
        int row = resetRow(oldPosition);
        int col = resetCol(oldPosition);

        ChessPosition newPosition;


        //Check Up
        row += 1;
        newPosition = new ChessPosition(row, col);
        checkBoard(board, oldPosition, row, col, newPosition, possibleMoves, color);
        //Reset
        row = resetRow(oldPosition);
        col = resetCol(oldPosition);


        //Check Right Up
        row += 1;
        col += 1;
        newPosition = new ChessPosition(row, col);
        checkBoard(board, oldPosition, row, col, newPosition, possibleMoves, color);
        //Reset
        row = resetRow(oldPosition);
        col = resetCol(oldPosition);


        //Check Right
        col += 1;
        newPosition = new ChessPosition(row, col);
        checkBoard(board, oldPosition, row, col, newPosition, possibleMoves, color);
        //Reset
        row = resetRow(oldPosition);
        col = resetCol(oldPosition);


        //Check Down Right
        row -= 1;
        col += 1;
        newPosition = new ChessPosition(row, col);
        checkBoard(board, oldPosition, row, col, newPosition, possibleMoves, color);
        //Reset
        row = resetRow(oldPosition);
        col = resetCol(oldPosition);



        //Check Down
        row -= 1;
        newPosition = new ChessPosition(row, col);
        checkBoard(board, oldPosition, row, col, newPosition, possibleMoves, color);
        //Reset
        row = resetRow(oldPosition);
        col = resetCol(oldPosition);




        //Check Down Left
        row -= 1;
        col -= 1;
        newPosition = new ChessPosition(row, col);
        checkBoard(board, oldPosition, row, col, newPosition, possibleMoves, color);
        //Reset
        row = resetRow(oldPosition);
        col = resetCol(oldPosition);




        //Check Left
        col -= 1;
        newPosition = new ChessPosition(row, col);
        checkBoard(board, oldPosition, row, col, newPosition, possibleMoves, color);
        //Reset
        row = resetRow(oldPosition);
        col = resetCol(oldPosition);


        //Check Left Up
        row += 1;
        col -= 1;
        newPosition = new ChessPosition(row, col);
        checkBoard(board, oldPosition, row, col, newPosition, possibleMoves, color);



        return possibleMoves;
    }

    private static void checkBoard(ChessBoard board, ChessPosition oldPosition, int row, int col, ChessPosition newPosition, Collection<ChessMove> possibleMoves, ChessGame.TeamColor color) {
        cycleBoard(board, oldPosition, row, col, newPosition, possibleMoves, color);
    }

    static void cycleBoard(ChessBoard board, ChessPosition oldPosition, int row, int col, ChessPosition newPosition, Collection<ChessMove> possibleMoves, ChessGame.TeamColor color) {
        ChessMove newMove;
        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(newPosition) == null) {
                newMove = new ChessMove(oldPosition, newPosition, null);
                possibleMoves.add(newMove);
            }
            else {
                if (board.getPiece(newPosition).getTeamColor() != color) {
                    newMove = new ChessMove(oldPosition, newPosition, null);
                    possibleMoves.add(newMove);
                }
            }
        }
    }
}





