package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator<E> implements PieceMoveCalculator<E> {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        ChessPiece<E> piece = board.getPiece(myPosition);
        Collection<ChessMove> possible_moves = new ArrayList<ChessMove>();
        ChessGame.TeamColor color = piece.getTeamColor();

        int move = 0;

        //White is at the bottom, moving up. They add rows (+)
        if (color == ChessGame.TeamColor.WHITE) {
            move = 1;
        }

        //Black is at the top, moving down. They subtract  rows (-)
        if (color == ChessGame.TeamColor.BLACK) {
            move = -1;
        }

        //Initiate Pawn
        if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {

            int row = myPosition.getRow();
            int col = myPosition.getColumn();

            //If Initial Move & White
            if (row == 2 && piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                //Check Double Move

                row += move;
                row += move;

                ChessPosition new_pos = new ChessPosition(row, col);
                ChessPosition checking_piece = new ChessPosition((row - move), col);
                //If no piece, can move
                if (board.getPiece(new_pos) == null && board.getPiece(checking_piece) == null) {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }

                //If there's a piece, not a move

                //Reset
                row = myPosition.getRow();
                col = myPosition.getColumn();

            }

            //If Initial move & Black
            if (row == 7 && piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                //Check Double Move
                row += move;
                row += move;


                ChessPosition new_pos = new ChessPosition(row, col);
                ChessPosition checking_piece = new ChessPosition((row - move), col);
                //If no piece in that space and in front, can move
                if (board.getPiece(new_pos) == null && board.getPiece(checking_piece) == null) {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }

                //If there's a piece, we add nothing

                //Reset
                row = myPosition.getRow();
                col = myPosition.getColumn();

            }


            //Check forward

            row += move;
            //If in bounds
            if (row <= 8 && col <= 8 && row >= 1 && col >= 1) {

                ChessPosition new_pos = new ChessPosition(row, col);

                //If no piece, can move
                if (board.getPiece(new_pos) == null) {
                    if (row == 1 || row == 8) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, ChessPiece.PieceType.QUEEN));
                        possible_moves.add(new ChessMove(myPosition, new_pos, ChessPiece.PieceType.KNIGHT));
                        possible_moves.add(new ChessMove(myPosition, new_pos, ChessPiece.PieceType.ROOK));
                        possible_moves.add(new ChessMove(myPosition, new_pos, ChessPiece.PieceType.BISHOP));
                    } else {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));

                        //If there's a piece, we add nothing
                    }
                }
            }


            //Reset
            row = myPosition.getRow();
            col = myPosition.getColumn();


            //Check Sideways Right

            col += 1;
            row += move;

            if (row <= 8 && col <= 8 && row >= 1 && col >= 1) {

                ChessPosition new_pos = new ChessPosition(row, col);


                if (board.getPiece(new_pos) != null) {
                    //If Enemy piece, Valid.
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        //Check for promotion
                        if (row == 1 || row == 8) {
                            possible_moves.add(new ChessMove(myPosition, new_pos, ChessPiece.PieceType.QUEEN));
                            possible_moves.add(new ChessMove(myPosition, new_pos, ChessPiece.PieceType.KNIGHT));
                            possible_moves.add(new ChessMove(myPosition, new_pos, ChessPiece.PieceType.ROOK));
                            possible_moves.add(new ChessMove(myPosition, new_pos, ChessPiece.PieceType.BISHOP));
                        } else {
                            possible_moves.add(new ChessMove(myPosition, new_pos, null));
                        }
                        //If Friendly pawn, Invalid. So nothing
                    }
                }
            }

            //Reset
            row = myPosition.getRow();
            col = myPosition.getColumn();


            //Check sideways Left
            col -= 1;
            row += move;

            if (row <= 8 && col <= 8 && row >= 1 && col >= 1) {

                ChessPosition new_pos = new ChessPosition(row, col);

                if (board.getPiece(new_pos) != null) {
                    //If Enemy piece, Valid.
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        //Check for promotion
                        if (row == 1 || row == 8) {
                            possible_moves.add(new ChessMove(myPosition, new_pos, ChessPiece.PieceType.QUEEN));
                            possible_moves.add(new ChessMove(myPosition, new_pos, ChessPiece.PieceType.KNIGHT));
                            possible_moves.add(new ChessMove(myPosition, new_pos, ChessPiece.PieceType.ROOK));
                            possible_moves.add(new ChessMove(myPosition, new_pos, ChessPiece.PieceType.BISHOP));
                        } else {
                            possible_moves.add(new ChessMove(myPosition, new_pos, null));
                        }
                    }
                    //If Friendly pawn, Invalid. So nothing
                }

            }

        }
        return possible_moves;
    }
}