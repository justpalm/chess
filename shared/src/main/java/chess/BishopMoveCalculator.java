package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMoveCalculator implements PieceMoveCalculator {

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition old_position) {

        boolean n = true;


        Collection<ChessMove> possible_moves = new ArrayList<>();
        ChessPiece piece = board.getPiece(old_position);
        ChessGame.TeamColor color = piece.getTeamColor();

        //Set Values
        int row = old_position.getRow();
        int col = old_position.getColumn();
        ChessPosition new_position;
        ChessMove new_move;


        //Check left UP LOOP (minus a column, up a row)
        while (n) {
            col -= 1;
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


        //Check right UP

        while (n) {
            col = col + 1;
            row = row + 1;

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


        //Check left DOWN

        while (n) {
            col = col - 1;
            row = row - 1;

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


        //Check right DOWN


        while (n) {
            col = col + 1;
            row = row - 1;

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

