package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMovesCalculator<E> implements PieceMoveCalculator<E> {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece<E> piece = board.getPiece(myPosition);
        Collection<ChessMove> possible_moves = new ArrayList<ChessMove>();
        ChessGame.TeamColor color = piece.getTeamColor();


        if (piece.getPieceType() == ChessPiece.PieceType.KING) {

            int row = myPosition.getRow();
            int col = myPosition.getColumn();

            //Check Bottom Left Side
            // Go up a row, minus a column
            //If out of bounds, invalid and STOP.
            row -= 1;
            col -= 1;
            if (row <= 8 && col <= 8 && row >= 1 && col >= 1) {

                ChessPosition new_pos = new ChessPosition(row, col);

                if (board.getPiece(new_pos) != null) {
                    //If Enemy piece, Valid
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                    }
                    //If Friendly pawn Invalid .

                    //If empty totally fine
                } else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
            }

            //Reset
            row = myPosition.getRow();
            col = myPosition.getColumn();


            //Check Bottom Right Side
            //If out of bounds, invalid and STOP.
            row -= 1;
            col += 1;
            if (row <= 8 && col <= 8 && row >= 1 && col >= 1) {

                ChessPosition new_pos = new ChessPosition(row, col);

                if (board.getPiece(new_pos) != null) {
                    //If Enemy piece, Valid
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                    }
                    //If Friendly pawn Invalid .

                    //If empty totally fine
                } else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
            }
            //Reset
            row = myPosition.getRow();
            col = myPosition.getColumn();


            //Check Top Right Side
            //If out of bounds, invalid and STOP.
            row += 1;
            col += 1;

            if (row <= 8 && col <= 8 && row >= 1 && col >= 1) {

                ChessPosition new_pos = new ChessPosition(row, col);

                if (board.getPiece(new_pos) != null) {
                    //If Enemy piece, Valid
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                    }
                    //If Friendly pawn Invalid .

                    //If empty totally fine
                } else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
            }
            //Reset
            row = myPosition.getRow();
            col = myPosition.getColumn();

            //Check Top Left Side
            //If out of bounds, invalid and STOP.
            row += 1;
            col -= 1;

            if (row <= 8 && col <= 8 && row >= 1 && col >= 1) {

                ChessPosition new_pos = new ChessPosition(row, col);

                if (board.getPiece(new_pos) != null) {
                    //If Enemy piece, Valid
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                    }
                    //If Friendly pawn Invalid .

                    //If empty totally fine
                } else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
            }


            //Reset
            row = myPosition.getRow();
            col = myPosition.getColumn();

            //Check Straight Down

            row -= 1;

            if (row <= 8 && col <= 8 && row >= 1 && col >= 1) {

                ChessPosition new_pos = new ChessPosition(row, col);

                if (board.getPiece(new_pos) != null) {
                    //If Enemy piece, Valid
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                    }
                    //If Friendly pawn Invalid .

                    //If empty totally fine
                } else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
            }


            //Reset
            row = myPosition.getRow();
            col = myPosition.getColumn();

            //Check Straight Up

            //If out of bounds, invalid and STOP.
            row += 1;

            if (row <= 8 && col <= 8 && row >= 1 && col >= 1) {

                ChessPosition new_pos = new ChessPosition(row, col);

                if (board.getPiece(new_pos) != null) {
                    //If Enemy piece, Valid
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                    }
                    //If Friendly pawn Invalid .

                    //If empty totally fine
                } else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
            }

            //Reset
            row = myPosition.getRow();
            col = myPosition.getColumn();

            //Check Straight Left
            //If out of bounds, invalid and STOP.
            col -= 1;

            if (row <= 8 && col <= 8 && row >= 1 && col >= 1) {

                ChessPosition new_pos = new ChessPosition(row, col);

                if (board.getPiece(new_pos) != null) {
                    //If Enemy piece, Valid
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                    }
                    //If Friendly pawn Invalid .

                    //If empty totally fine
                } else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
            }


        //Reset
        row = myPosition.getRow();
        col = myPosition.getColumn();

            //Check Straight Right
            //If out of bounds, invalid and STOP.
            col += 1;

            if (row <= 8 && col <= 8 && row >= 1 && col >= 1) {

                ChessPosition new_pos = new ChessPosition(row, col);

                if (board.getPiece(new_pos) != null) {
                    //If Enemy piece, Valid
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                    }
                    //If Friendly pawn Invalid .

                    //If empty totally fine
                } else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
            }
        }
        return possible_moves;

    }
}





