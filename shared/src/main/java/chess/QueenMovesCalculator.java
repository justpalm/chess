package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalculator<E> implements PieceMoveCalculator<E> {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece<E> piece = board.getPiece(myPosition);
        Collection<ChessMove> possible_moves = new ArrayList<ChessMove>();
        ChessGame.TeamColor color = piece.getTeamColor();


        if (piece.getPieceType() == ChessPiece.PieceType.QUEEN) {

            int row = myPosition.getRow();
            int col = myPosition.getColumn();

            boolean n = true;
            //Check Bottom Left Side
            // Go up a row, minus a column
            while (n) {
                //If out of bounds, invalid and STOP.
                row -= 1;
                col -= 1;
                if (row > 8 || col > 8 || row < 1 || col < 1) {
                    break;
                }

                ChessPosition new_pos = new ChessPosition(row, col);

                if (board.getPiece(new_pos) != null) {
                    //If Enemy piece, Valid and STOP.
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                        break;
                    }
                    //If Friendly pawn Invalid and STOP.
                    else {
                        break;
                    }
                } else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
            }

            //Reset
            row = myPosition.getRow();
            col = myPosition.getColumn();


            //Check Bottom Right Side
            while (n) {
                //If out of bounds, invalid and STOP.
                row -= 1;
                col += 1;
                if (row > 8 || col > 8 || row < 1 || col < 1) {
                    break;
                }

                ChessPosition new_pos = new ChessPosition(row, col);

                if (board.getPiece(new_pos) != null) {
                    //If Enemy piece, Valid and STOP.
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                        break;
                    }
                    //If Friendly pawn Invalid and STOP.
                    else {
                        break;
                    }
                } else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
            }
            //Reset
            row = myPosition.getRow();
            col = myPosition.getColumn();


            //Check Up Right Side
            while (n) {
                //If out of bounds, invalid and STOP.
                row += 1;
                col += 1;

                if (row > 8 || col > 8 || row < 1 || col < 1) {
                    break;
                }

                ChessPosition new_pos = new ChessPosition(row, col);

                if (board.getPiece(new_pos) != null) {
                    //If Enemy piece, Valid and STOP.
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                        break;
                    }
                    //If Friendly pawn Invalid and STOP.
                    else {
                        break;
                    }
                } else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
            }
            //Reset
            row = myPosition.getRow();
            col = myPosition.getColumn();

            //Check Up Left Side
            while (n) {
                //If out of bounds, invalid and STOP.
                row += 1;
                col -= 1;

                if (row > 8 || col > 8 || row < 1 || col < 1) {
                    break;
                }

                ChessPosition new_pos = new ChessPosition(row, col);

                if (board.getPiece(new_pos) != null) {
                    //If Enemy piece, Valid and STOP.
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                        break;
                    }
                    //If Friendly pawn Invalid and STOP.
                    else {
                        break;
                    }
                } else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
            }

            //Reset
            row = myPosition.getRow();
            col = myPosition.getColumn();

            //Check Straight Left
            while (n) {
                //If out of bounds, invalid and STOP.
                col -= 1;

                if (row > 8 || col > 8 || row < 1 || col < 1) {
                    break;
                }

                ChessPosition new_pos = new ChessPosition(row, col);

                if (board.getPiece(new_pos) != null) {
                    //If Enemy piece, Valid and STOP.
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                        break;
                    }
                    //If Friendly pawn Invalid and STOP.
                    else {
                        break;
                    }
                } else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
            }

            //Reset
            row = myPosition.getRow();
            col = myPosition.getColumn();

            //Check Straight Right

            while (n) {
                //If out of bounds, invalid and STOP.
                col += 1;

                if (row > 8 || col > 8 || row < 1 || col < 1) {
                    break;
                }

                ChessPosition new_pos = new ChessPosition(row, col);

                if (board.getPiece(new_pos) != null) {
                    //If Enemy piece, Valid and STOP.
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                        break;
                    }
                    //If Friendly pawn Invalid and STOP.
                    else {
                        break;
                    }
                } else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
            }


            //Reset
            row = myPosition.getRow();
            col = myPosition.getColumn();

            //Check Straight Down

            while (n) {
                //If out of bounds, invalid and STOP.
                row -= 1;

                if (row > 8 || col > 8 || row < 1 || col < 1) {
                    break;
                }

                ChessPosition new_pos = new ChessPosition(row, col);

                if (board.getPiece(new_pos) != null) {
                    //If Enemy piece, Valid and STOP.
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                        break;
                    }
                    //If Friendly pawn Invalid and STOP.
                    else {
                        break;
                    }
                } else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
            }

            //Reset
            row = myPosition.getRow();
            col = myPosition.getColumn();

            //Check Straight Up

            while (n) {
                //If out of bounds, invalid and STOP.
                row += 1;

                if (row > 8 || col > 8 || row < 1 || col < 1) {
                    break;
                }

                ChessPosition new_pos = new ChessPosition(row, col);

                if (board.getPiece(new_pos) != null) {
                    //If Enemy piece, Valid and STOP.
                    if (board.getPiece(new_pos).getTeamColor() != color) {
                        possible_moves.add(new ChessMove(myPosition, new_pos, null));
                        break;
                    }
                    //If Friendly pawn Invalid and STOP.
                    else {
                        break;
                    }
                } else {
                    possible_moves.add(new ChessMove(myPosition, new_pos, null));
                }
            }


        }

        return possible_moves;
    }
}