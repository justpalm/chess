package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoveCalculator implements PieceMoveCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition oldPositionRow) {


        Collection<ChessMove> possibleMoves = new ArrayList<>();
        ChessPiece piece = board.getPiece(oldPositionRow);
        ChessGame.TeamColor color = piece.getTeamColor();

        ChessMove newMove;
        ChessPosition newPosition;

        //Set Values
        int row = oldPositionRow.getRow();
        int col = oldPositionRow.getColumn();


        //Check Team Color
        int move = 0;
        int promotionRow = 0;
        int startRow = 0;

        if (color == ChessGame.TeamColor.WHITE) {
            move = 1;
            promotionRow = 8;
            startRow = 2;
        }

        if (color == ChessGame.TeamColor.BLACK) {
            move = -1;
            promotionRow = 1;
            startRow = 7;
        }


        //If double move
        ChessPosition checkPlayers = new ChessPosition(row+move, col);
        if (row == startRow && board.getPiece(checkPlayers) == null) {
            row += 2 * (move);

            newPosition = new ChessPosition(row, col);
            if (board.getPiece(newPosition) == null) {
                newMove = new ChessMove(oldPositionRow, newPosition, null);
                possibleMoves.add(newMove);
            }
        }

        //Reset
        row = oldPositionRow.getRow();
        col = oldPositionRow.getColumn();

        //Check for foes LEFT
        row += move;
        col -= 1;

        checkingFoes(board, oldPositionRow, row, col, color, possibleMoves, promotionRow);

        //Reset
        row = oldPositionRow.getRow();
        col = oldPositionRow.getColumn();


        //Check for foes RIGHT
        row += move;
        col += 1;

        checkingFoes(board, oldPositionRow, row, col, color, possibleMoves, promotionRow);

        //Reset
        row = oldPositionRow.getRow();
        col = oldPositionRow.getColumn();


        // Check forward normal
        row += move;

        newPosition = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(newPosition) == null) {
                putPromotionPieces(oldPositionRow, possibleMoves, newPosition, row, promotionRow);
            }
        }

        return possibleMoves;
    }

    private void checkingFoes(ChessBoard board, ChessPosition oldPositionRow, int row, int col, ChessGame.TeamColor color, Collection<ChessMove> possibleMoves, int promotionRow) {
        ChessPosition newPosition = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() != color) {
                putPromotionPieces(oldPositionRow, possibleMoves, newPosition, row, promotionRow);
            }
        }
    }

    private void putPromotionPieces(ChessPosition oldPositionRow, Collection<ChessMove> possibleMoves, ChessPosition newPosition, int row, int promotionRow) {
        ChessMove newMove;
        if (row == promotionRow) {
            newMove = new ChessMove(oldPositionRow, newPosition, ChessPiece.PieceType.QUEEN);
            possibleMoves.add(newMove);
            newMove = new ChessMove(oldPositionRow, newPosition, ChessPiece.PieceType.ROOK);
            possibleMoves.add(newMove);
            newMove = new ChessMove(oldPositionRow, newPosition, ChessPiece.PieceType.BISHOP);
            possibleMoves.add(newMove);
            newMove = new ChessMove(oldPositionRow, newPosition, ChessPiece.PieceType.KNIGHT);
            possibleMoves.add(newMove);
        } else {
            newMove = new ChessMove(oldPositionRow, newPosition, null);
            possibleMoves.add(newMove);
        }
    }
}