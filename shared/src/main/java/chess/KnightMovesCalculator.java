package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalculator<E> implements PieceMoveCalculator<E> {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        ChessPiece<E> piece = board.getPiece(myPosition);
        Collection<ChessMove> possible_moves = new ArrayList<ChessMove>();
        ChessGame.TeamColor color = piece.getTeamColor();


        if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT) {

            int row = myPosition.getRow();
            int col = myPosition.getColumn();

            //Check Top Left
            row += 2;
            col -= 1;

            if (row <= 8 && col <= 8 && row >= 1 && col >= 1) {

                ChessPosition new_pos = new ChessPosition(row, col);

                //If team piece, no move.

                if (board.getPiece(new_pos) != null) {
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                    }
                }

                else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
                //If there's a piece, we add nothing
            }



        //Reset
        row = myPosition.getRow();
        col = myPosition.getColumn();


            //Check Top Right
            row += 2;
            col += 1;

            if (row <= 8 && col <= 8 && row >= 1 && col >= 1) {

                ChessPosition new_pos = new ChessPosition(row, col);

                //If team piece, no move.

                if (board.getPiece(new_pos) != null) {
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                    }
                }

                else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
                //If there's a piece, we add nothing
            }

        //Reset
        row = myPosition.getRow();
        col = myPosition.getColumn();


            //Check Up Middle Right
            col += 2;
            row += 1;

            if (row <= 8 && col <= 8 && row >= 1 && col >= 1) {

                ChessPosition new_pos = new ChessPosition(row, col);

                //If team piece, no move.

                if (board.getPiece(new_pos) != null) {
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                    }
                }

                else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
                //If there's a piece, we add nothing
            }

        //Reset
        row = myPosition.getRow();
        col = myPosition.getColumn();


            //Check Down Middle Right
            col += 2;
            row -= 1;

            if (row <= 8 && col <= 8 && row >= 1 && col >= 1) {

                ChessPosition new_pos = new ChessPosition(row, col);

                //If team piece, no move.

                if (board.getPiece(new_pos) != null) {
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                    }
                }

                else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
                //If there's a piece, we add nothing
            }

        //Reset
        row = myPosition.getRow();
        col = myPosition.getColumn();

            //Check Bottom Right
            row -= 2;
            col += 1;

            if (row <= 8 && col <= 8 && row >= 1 && col >= 1) {

                ChessPosition new_pos = new ChessPosition(row, col);

                //If team piece, no move.

                if (board.getPiece(new_pos) != null) {
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                    }
                }

                else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
                //If there's a piece, we add nothing
            }

        //Reset
        row = myPosition.getRow();
        col = myPosition.getColumn();


            //Check Bottom Left
            row -= 2;
            col -= 1;

            if (row <= 8 && col <= 8 && row >= 1 && col >= 1) {

                ChessPosition new_pos = new ChessPosition(row, col);

                //If team piece, no move.

                if (board.getPiece(new_pos) != null) {
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                    }
                }

                else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
                //If there's a piece, we add nothing
            }

        //Reset
        row = myPosition.getRow();
        col = myPosition.getColumn();


            //Check Down Middle Left
            col -= 2;
            row -= 1;

            if (row <= 8 && col <= 8 && row >= 1 && col >= 1) {

                ChessPosition new_pos = new ChessPosition(row, col);

                //If team piece, no move.

                if (board.getPiece(new_pos) != null) {
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                    }
                }

                else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
                //If there's a piece, we add nothing
            }


        //Reset
        row = myPosition.getRow();
        col = myPosition.getColumn();

            //Check Up Middle Left
            col -= 2;
            row += 1;

            if (row <= 8 && col <= 8 && row >= 1 && col >= 1) {

                ChessPosition new_pos = new ChessPosition(row, col);

                //If team piece, no move.

                if (board.getPiece(new_pos) != null) {
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                    }
                }

                else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
                //If there's a piece, we add nothing
            }


        }
        return possible_moves;
    }
}

