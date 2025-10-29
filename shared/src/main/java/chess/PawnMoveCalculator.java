package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoveCalculator implements PieceMoveCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition oldPositionRow) {


        Collection<ChessMove> possible_moves = new ArrayList<>();
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
        ChessPosition check_players = new ChessPosition(row+move, col);
        if (row == startRow && board.getPiece(check_players) == null) {
            row += 2 * (move);

            newPosition = new ChessPosition(row, col);
            if (board.getPiece(newPosition) == null) {
                newMove = new ChessMove(oldPositionRow, newPosition, null);
                possible_moves.add(newMove);
            }
        }

        //Reset
        row = oldPositionRow.getRow();
        col = oldPositionRow.getColumn();

        //Check for foes LEFT
        row += move;
        col -= 1;

        newPosition = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() != color) {
                if (row == promotionRow) {

                    newMove = new ChessMove(oldPositionRow, newPosition, ChessPiece.PieceType.QUEEN);
                    possible_moves.add(newMove);
                    newMove = new ChessMove(oldPositionRow, newPosition, ChessPiece.PieceType.ROOK);
                    possible_moves.add(newMove);
                    newMove = new ChessMove(oldPositionRow, newPosition, ChessPiece.PieceType.BISHOP);
                    possible_moves.add(newMove);
                    newMove = new ChessMove(oldPositionRow, newPosition, ChessPiece.PieceType.KNIGHT);
                    possible_moves.add(newMove);
                } else {
                    newMove = new ChessMove(oldPositionRow, newPosition, null);
                    possible_moves.add(newMove);
                }
            }
        }

        //Reset
        row = oldPositionRow.getRow();
        col = oldPositionRow.getColumn();


        //Check for foes RIGHT
        row += move;
        col += 1;

        newPosition = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() != color) {
                if (row == promotionRow) {
                    newMove = new ChessMove(oldPositionRow, newPosition, ChessPiece.PieceType.QUEEN);
                    possible_moves.add(newMove);
                    newMove = new ChessMove(oldPositionRow, newPosition, ChessPiece.PieceType.ROOK);
                    possible_moves.add(newMove);
                    newMove = new ChessMove(oldPositionRow, newPosition, ChessPiece.PieceType.BISHOP);
                    possible_moves.add(newMove);
                    newMove = new ChessMove(oldPositionRow, newPosition, ChessPiece.PieceType.KNIGHT);
                    possible_moves.add(newMove);
                } else {
                    newMove = new ChessMove(oldPositionRow, newPosition, null);
                    possible_moves.add(newMove);
                }
            }
        }

        //Reset
        row = oldPositionRow.getRow();
        col = oldPositionRow.getColumn();


        // Check forward normal
        row += move;

        newPosition = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(newPosition) == null) {
                if (row == promotionRow) {
                    newMove = new ChessMove(oldPositionRow, newPosition, ChessPiece.PieceType.QUEEN);
                    possible_moves.add(newMove);
                    newMove = new ChessMove(oldPositionRow, newPosition, ChessPiece.PieceType.ROOK);
                    possible_moves.add(newMove);
                    newMove = new ChessMove(oldPositionRow, newPosition, ChessPiece.PieceType.BISHOP);
                    possible_moves.add(newMove);
                    newMove = new ChessMove(oldPositionRow, newPosition, ChessPiece.PieceType.KNIGHT);
                    possible_moves.add(newMove);
                } else {
                    newMove = new ChessMove(oldPositionRow, newPosition, null);
                    possible_moves.add(newMove);
                }
            }
        }

        return possible_moves;
    }
}
