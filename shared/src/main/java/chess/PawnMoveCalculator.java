package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoveCalculator implements PieceMoveCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition old_position) {


        Collection<ChessMove> possible_moves = new ArrayList<>();
        ChessPiece piece = board.getPiece(old_position);
        ChessGame.TeamColor color = piece.getTeamColor();

        ChessMove new_move;
        ChessPosition new_position;

        //Set Values
        int row = old_position.getRow();
        int col = old_position.getColumn();


        //Check Team Color
        int move = 0;
        int promotion_row = 0;
        int start_row = 0;

        if (color == ChessGame.TeamColor.WHITE) {
            move = 1;
            promotion_row = 8;
            start_row = 2;
        }

        if (color == ChessGame.TeamColor.BLACK) {
            move = -1;
            promotion_row = 1;
            start_row = 7;
        }


        //If double move
        ChessPosition check_players = new ChessPosition(row+move, col);
        if (row == start_row && board.getPiece(check_players) == null) {
            row += 2 * (move);

            new_position = new ChessPosition(row, col);
            if (board.getPiece(new_position) == null) {
                new_move = new ChessMove(old_position, new_position, null);
                possible_moves.add(new_move);
            }
        }

        //Reset
        row = old_position.getRow();
        col = old_position.getColumn();

        //Check for foes LEFT
        row += move;
        col -= 1;

        new_position = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(new_position) != null && board.getPiece(new_position).getTeamColor() != color) {
                if (row == promotion_row) {

                    new_move = new ChessMove(old_position, new_position, ChessPiece.PieceType.QUEEN);
                    possible_moves.add(new_move);
                    new_move = new ChessMove(old_position, new_position, ChessPiece.PieceType.ROOK);
                    possible_moves.add(new_move);
                    new_move = new ChessMove(old_position, new_position, ChessPiece.PieceType.BISHOP);
                    possible_moves.add(new_move);
                    new_move = new ChessMove(old_position, new_position, ChessPiece.PieceType.KNIGHT);
                    possible_moves.add(new_move);
                } else {
                    new_move = new ChessMove(old_position, new_position, null);
                    possible_moves.add(new_move);
                }
            }
        }

        //Reset
        row = old_position.getRow();
        col = old_position.getColumn();


        //Check for foes RIGHT
        row += move;
        col += 1;

        new_position = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(new_position) != null && board.getPiece(new_position).getTeamColor() != color) {
                if (row == promotion_row) {
                    new_move = new ChessMove(old_position, new_position, ChessPiece.PieceType.QUEEN);
                    possible_moves.add(new_move);
                    new_move = new ChessMove(old_position, new_position, ChessPiece.PieceType.ROOK);
                    possible_moves.add(new_move);
                    new_move = new ChessMove(old_position, new_position, ChessPiece.PieceType.BISHOP);
                    possible_moves.add(new_move);
                    new_move = new ChessMove(old_position, new_position, ChessPiece.PieceType.KNIGHT);
                    possible_moves.add(new_move);
                } else {
                    new_move = new ChessMove(old_position, new_position, null);
                    possible_moves.add(new_move);
                }
            }
        }

        //Reset
        row = old_position.getRow();
        col = old_position.getColumn();


        // Check forward normal
        row += move;

        new_position = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(new_position) == null) {
                if (row == promotion_row) {
                    new_move = new ChessMove(old_position, new_position, ChessPiece.PieceType.QUEEN);
                    possible_moves.add(new_move);
                    new_move = new ChessMove(old_position, new_position, ChessPiece.PieceType.ROOK);
                    possible_moves.add(new_move);
                    new_move = new ChessMove(old_position, new_position, ChessPiece.PieceType.BISHOP);
                    possible_moves.add(new_move);
                    new_move = new ChessMove(old_position, new_position, ChessPiece.PieceType.KNIGHT);
                    possible_moves.add(new_move);
                } else {
                    new_move = new ChessMove(old_position, new_position, null);
                    possible_moves.add(new_move);
                }
            }
        }

        return possible_moves;
    }
}
