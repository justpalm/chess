package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoveCalculator implements PieceMoveCalculator{
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
        boolean n = true;


        //Check Up

        while (n) {
            row += 1;

            if (row < 9 && col < 9 && row > 0 && col > 0) {

                new_position = new ChessPosition(row, col);


                if (board.getPiece(new_position) != null) {
                    if (board.getPiece(new_position).getTeamColor() != color) { //enemy

                        new_move = new ChessMove(old_position, new_position, null);
                        possible_moves.add(new_move);
                        break;
                    } else { //friend
                        break;
                    }
                } else {
                    new_move = new ChessMove(old_position, new_position, null);
                    possible_moves.add(new_move);
                }
            }
            else {
                break;
            }
        }
        //Reset
        row = old_position.getRow();
        col = old_position.getColumn();



        //Check Right
        while (n) {
            col += 1;

            if (row < 9 && col < 9 && row > 0 && col > 0) {

                new_position = new ChessPosition(row, col);


                if (board.getPiece(new_position) != null) {
                    if (board.getPiece(new_position).getTeamColor() != color) { //enemy

                        new_move = new ChessMove(old_position, new_position, null);
                        possible_moves.add(new_move);
                        break;
                    } else { //friend
                        break;
                    }
                } else {
                    new_move = new ChessMove(old_position, new_position, null);
                    possible_moves.add(new_move);
                }
            }
            else {
                break;
            }
        }
        //Reset
        row = old_position.getRow();
        col = old_position.getColumn();


        //Check Down

        while (n) {
            row -= 1;

            if (row < 9 && col < 9 && row > 0 && col > 0) {

                new_position = new ChessPosition(row, col);


                if (board.getPiece(new_position) != null) {
                    if (board.getPiece(new_position).getTeamColor() != color) { //enemy

                        new_move = new ChessMove(old_position, new_position, null);
                        possible_moves.add(new_move);
                        break;
                    } else { //friend
                        break;
                    }
                } else {
                    new_move = new ChessMove(old_position, new_position, null);
                    possible_moves.add(new_move);
                }
            }
            else {
                break;
            }
        }
        //Reset
        row = old_position.getRow();
        col = old_position.getColumn();



        //Check Left

        while (n) {
            col -= 1;

            if (row < 9 && col < 9 && row > 0 && col > 0) {

                new_position = new ChessPosition(row, col);


                if (board.getPiece(new_position) != null) {
                    if (board.getPiece(new_position).getTeamColor() != color) { //enemy

                        new_move = new ChessMove(old_position, new_position, null);
                        possible_moves.add(new_move);
                        break;
                    } else { //friend
                        break;
                    }
                } else {
                    new_move = new ChessMove(old_position, new_position, null);
                    possible_moves.add(new_move);
                }
            }
            else {
                break;
            }
        }
        return possible_moves;
    }
}
