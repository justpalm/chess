package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMoveCalculator implements PieceMoveCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition oldPosition) {


        Collection<ChessMove> possibleMoves = new ArrayList<>();
        ChessPiece piece = board.getPiece(oldPosition);
        ChessGame.TeamColor color = piece.getTeamColor();

        //Set Values
        int row = oldPosition.getRow();
        int col = oldPosition.getColumn();

        //Check Up
        do {
            row += 1;

        } while (queenMove(board, oldPosition, row, col, color, possibleMoves));
        //Reset
        row = oldPosition.getRow();
        col = oldPosition.getColumn();

        //Check Right
        do {
            col += 1;

        } while (queenMove(board, oldPosition, row, col, color, possibleMoves));
        //Reset
        row = oldPosition.getRow();
        col = oldPosition.getColumn();


        //Check Down
        do {
            row -= 1;

        } while (queenMove(board, oldPosition, row, col, color, possibleMoves));
        //Reset
        row = oldPosition.getRow();
        col = oldPosition.getColumn();
        //Check Left
        do {
            col -= 1;

        } while (queenMove(board, oldPosition, row, col, color, possibleMoves));
        //Reset
        row = oldPosition.getRow();
        col = oldPosition.getColumn();
        //Check left UP LOOP (minus a column, up a row)
        do {
            col -= 1;
            row += 1;

        } while (queenMove(board, oldPosition, row, col, color, possibleMoves));
        //Reset
        row = oldPosition.getRow();
        col = oldPosition.getColumn();
        //Check right UP
        do {
            col = col + 1;
            row = row + 1;

        } while (queenMove(board, oldPosition, row, col, color, possibleMoves));

        //Reset
        row = oldPosition.getRow();
        col = oldPosition.getColumn();
        //Check left DOWN
        do {
            col = col - 1;
            row = row - 1;

        } while (queenMove(board, oldPosition, row, col, color, possibleMoves));

        //Reset
        row = oldPosition.getRow();
        col = oldPosition.getColumn();
        //Check right DOWN
        do {
            col = col + 1;
            row = row - 1;

        } while (queenMove(board, oldPosition, row, col, color, possibleMoves));

        return possibleMoves;
    }
    public static boolean queenMove(ChessBoard board, ChessPosition oldPosition, int row, int col,
                                    ChessGame.TeamColor color, Collection<ChessMove> possibleMoves) {
        ChessMove chessMove;
        ChessPosition chessPosition;
        if (row < 9 && col < 9 && row > 0 && col > 0) {

            chessPosition = new ChessPosition(row, col);


            if (board.getPiece(chessPosition) != null) {
                if (board.getPiece(chessPosition).getTeamColor() != color) { //enemy

                    chessMove = new ChessMove(oldPosition, chessPosition, null);
                    possibleMoves.add(chessMove);
                    return false;
                } else { //friend
                    return false;
                }
            } else {
                chessMove = new ChessMove(oldPosition, chessPosition, null);
                possibleMoves.add(chessMove);
            }
        }
        else {
            return false;
        }
        return true;
    }


}
