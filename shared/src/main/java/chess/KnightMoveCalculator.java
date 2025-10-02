package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMoveCalculator implements PieceMoveCalculator{


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



        //CHeck top left
        col -= 1;
        row += 2;

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

        //Reset Values
        row = old_position.getRow();
        col = old_position.getColumn();


        //Check top right
        col += 1;
        row += 2;

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



        //Reset Values
        row = old_position.getRow();
        col = old_position.getColumn();

        //Check middle top right
        col += 2;
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



        //Reset Values
        row = old_position.getRow();
        col = old_position.getColumn();

        //Check middle bottom right
        col += 2;
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



        //Reset Values
        row = old_position.getRow();
        col = old_position.getColumn();

        //Check down right
        col += 1;
        row -= 2;

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



        //Reset Values
        row = old_position.getRow();
        col = old_position.getColumn();

        //Check down left
        col -= 1;
        row -= 2;

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


        //Reset Values
        row = old_position.getRow();
        col = old_position.getColumn();

        //Check middle bottom left
        col -= 2;
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


        //Reset Values
        row = old_position.getRow();
        col = old_position.getColumn();

        //Check middle top left
        col -= 2;
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
        return possible_moves;
    }
}


