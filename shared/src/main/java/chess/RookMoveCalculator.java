package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoveCalculator implements PieceMoveCalculator {
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

        } while (QueenMoveCalculator.queenMove(board, oldPosition, row, col, color, possibleMoves));
        //Reset
        row = oldPosition.getRow();
        col = oldPosition.getColumn();


        //Check Right
        do {
            col += 1;

        } while (QueenMoveCalculator.queenMove(board, oldPosition, row, col, color, possibleMoves));
        //Reset
        row = oldPosition.getRow();
        col = oldPosition.getColumn();


        //Check Down
        do {
            row -= 1;

        } while (QueenMoveCalculator.queenMove(board, oldPosition, row, col, color, possibleMoves));
        //Reset
        row = oldPosition.getRow();
        col = oldPosition.getColumn();


        //Check Left
        do {
            col -= 1;

        } while (QueenMoveCalculator.queenMove(board, oldPosition, row, col, color, possibleMoves));

        return possibleMoves;
    }
}

