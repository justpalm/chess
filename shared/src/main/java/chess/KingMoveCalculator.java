package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMoveCalculator implements PieceMoveCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition old_position) {

        Collection<ChessMove> possible_moves = new ArrayList<>();
        ChessPiece piece = board.getPiece(old_position);
        ChessGame.TeamColor color = piece.getTeamColor();



        //Set Values
        int row = old_position.getRow();
        int col = old_position.getColumn();

        ChessMove new_move;
        ChessPosition new_position;


        //Check Up
        row += 1;

        new_position = new ChessPosition(row, col);


        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(new_position) == null) {
                new_move = new ChessMove(old_position, new_position, null);
                possible_moves.add(new_move);
            }
            else {
                if (board.getPiece(new_position).getTeamColor() != color) {
                    new_move = new ChessMove(old_position, new_position, null);
                    possible_moves.add(new_move);
                }
            }
        }

        //Reset
        row = old_position.getRow();
        col = old_position.getColumn();

        //Check Right Up
        row += 1;
        col += 1;

        new_position = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(new_position) == null) {
                new_move = new ChessMove(old_position, new_position, null);
                possible_moves.add(new_move);
            }
            else {
                if (board.getPiece(new_position).getTeamColor() != color) {
                    new_move = new ChessMove(old_position, new_position, null);
                    possible_moves.add(new_move);
                }
            }
        }


        //Reset
        row = old_position.getRow();
        col = old_position.getColumn();

        //Check Right
        col += 1;

        new_position = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(new_position) == null) {
                new_move = new ChessMove(old_position, new_position, null);
                possible_moves.add(new_move);
            }
            else {
                if (board.getPiece(new_position).getTeamColor() != color) {
                    new_move = new ChessMove(old_position, new_position, null);
                    possible_moves.add(new_move);
                }
            }
        }


        //Reset
        row = old_position.getRow();
        col = old_position.getColumn();

        //Check Down Right
        col += 1;
        row -= 1;

        new_position = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(new_position) == null) {
                new_move = new ChessMove(old_position, new_position, null);
                possible_moves.add(new_move);
            }
            else {
                if (board.getPiece(new_position).getTeamColor() != color) {
                    new_move = new ChessMove(old_position, new_position, null);
                    possible_moves.add(new_move);
                }
            }
        }


        //Reset
        row = old_position.getRow();
        col = old_position.getColumn();

        //Check Down
        row -= 1;

        new_position = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(new_position) == null) {
                new_move = new ChessMove(old_position, new_position, null);
                possible_moves.add(new_move);
            }
            else {
                if (board.getPiece(new_position).getTeamColor() != color) {
                    new_move = new ChessMove(old_position, new_position, null);
                    possible_moves.add(new_move);
                }
            }
        }


        //Reset
        row = old_position.getRow();
        col = old_position.getColumn();

        //Check Down Left
        row -= 1;
        col -= 1;

        new_position = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(new_position) == null) {
                new_move = new ChessMove(old_position, new_position, null);
                possible_moves.add(new_move);
            }
            else {
                if (board.getPiece(new_position).getTeamColor() != color) {
                    new_move = new ChessMove(old_position, new_position, null);
                    possible_moves.add(new_move);
                }
            }
        }

        //Reset
        row = old_position.getRow();
        col = old_position.getColumn();

        //Check Left
        col -= 1;

        new_position = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(new_position) == null) {
                new_move = new ChessMove(old_position, new_position, null);
                possible_moves.add(new_move);
            }
            else {
                if (board.getPiece(new_position).getTeamColor() != color) {
                    new_move = new ChessMove(old_position, new_position, null);
                    possible_moves.add(new_move);
                }
            }
        }

        //Reset
        row = old_position.getRow();
        col = old_position.getColumn();


        //Check Left Up
        row += 1;
        col -= 1;

        new_position = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(new_position) == null) {
                new_move = new ChessMove(old_position, new_position, null);
                possible_moves.add(new_move);
            }
            else {
                if (board.getPiece(new_position).getTeamColor() != color) {
                    new_move = new ChessMove(old_position, new_position, null);
                    possible_moves.add(new_move);
                }
            }
        }


        return possible_moves;
    }
}





