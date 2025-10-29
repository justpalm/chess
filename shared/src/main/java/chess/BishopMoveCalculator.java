package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMoveCalculator implements PieceMoveCalculator {

    public static int resetRow(ChessPosition oldPosition) {
        return oldPosition.getRow();
    }

    public static int resetCol(ChessPosition oldPosition) {
        return oldPosition.getColumn();
    }

    //Reset row and Column values

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition oldPosition) {

        int row = resetRow(oldPosition);
        int col = resetCol(oldPosition);


        Collection<ChessMove> possibleMoves = new ArrayList<>();
        ChessPiece piece = board.getPiece(oldPosition);
        ChessGame.TeamColor color = piece.getTeamColor();

        //Set Values


        //Check left UP LOOP (minus a column, up a row)
        do {
            col -= 1;
            row += 1;

        } while (bishopMoving(board, oldPosition, row, col, possibleMoves, color));
        //Reset
        row = resetRow(oldPosition);
        col = resetCol(oldPosition);


        //Check right UP

        do {
            col = col + 1;
            row = row + 1;

        } while (bishopMoving(board, oldPosition, row, col, possibleMoves, color));

        //Reset
        row = resetRow(oldPosition);
        col = resetCol(oldPosition);


        //Check left DOWN

        do {
            col = col - 1;
            row = row - 1;

        } while (bishopMoving(board, oldPosition, row, col, possibleMoves, color));

        //Reset
        row = resetRow(oldPosition);
        col = resetCol(oldPosition);


        //Check right DOWN


        do {
            col = col + 1;
            row = row - 1;

        } while (bishopMoving(board, oldPosition, row, col, possibleMoves, color));
        return possibleMoves;
    }

    private boolean bishopMoving(ChessBoard board, ChessPosition oldPosition, int row, int col, Collection<ChessMove> possibleMoves, ChessGame.TeamColor color) {
        ChessPosition chessPosition;
        ChessMove chessMove;
        if (row < 9 && col < 9 && row > 0 && col > 0) {

            chessPosition = new ChessPosition(row, col);


            if (board.getPiece(chessPosition) != null) {
                if (board.getPiece(chessPosition).getTeamColor() != color) { //enemy

                    chessMove = new ChessMove(oldPosition, chessPosition, null);
                    possibleMoves.add(chessMove);
                }
                return false;
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

