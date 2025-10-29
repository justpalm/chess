package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMoveCalculator implements PieceMoveCalculator {


//    private void resetRow(){
//    //Reset row and Column values
//    }
//
//    private void resetCol(){
//
//
//    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition oldPosition) {

        int row = oldPosition.getRow();
        int col = oldPosition.getColumn();

//        void resetValue; {
//        row = oldPosition.getRow();
//        col = oldPosition.getColumn();
//
//        }


        boolean n = true;


        Collection<ChessMove> possibleMoves = new ArrayList<>();
        ChessPiece piece = board.getPiece(oldPosition);
        ChessGame.TeamColor color = piece.getTeamColor();

        //Set Values

        ChessPosition chessPosition;
        ChessMove chessMove;


        //Check left UP LOOP (minus a column, up a row)
        while (n) {
            col -= 1;
            row += 1;

            if (row < 9 && col < 9 && row > 0 && col > 0) {

                chessPosition = new ChessPosition(row, col);


                if (board.getPiece(chessPosition) != null) {
                    if (board.getPiece(chessPosition).getTeamColor() != color) { //enemy

                        chessMove = new ChessMove(oldPosition, chessPosition, null);
                        possibleMoves.add(chessMove);
                    }
                    break;
                } else {
                    chessMove = new ChessMove(oldPosition, chessPosition, null);
                    possibleMoves.add(chessMove);
                }
            }
            else {
                break;
            }
        }
        //Reset
        row = oldPosition.getRow();
        col = oldPosition.getColumn();


        //Check right UP

        while (n) {
            col = col + 1;
            row = row + 1;

            if (row < 9 && col < 9 && row > 0 && col > 0) {

                chessPosition = new ChessPosition(row, col);



                if (board.getPiece(chessPosition) != null) {
                    if (board.getPiece(chessPosition).getTeamColor() != color) { //enemy

                        chessMove = new ChessMove(oldPosition, chessPosition, null);
                        possibleMoves.add(chessMove);
                        break;
                    } else { //friend
                        break;
                    }
                } else {
                    chessMove = new ChessMove(oldPosition, chessPosition, null);
                    possibleMoves.add(chessMove);
                }
            }
            else {
                break;
            }
        }

        //Reset
        row = oldPosition.getRow();
        col = oldPosition.getColumn();


        //Check left DOWN

        while (n) {
            col = col - 1;
            row = row - 1;

            if (row < 9 && col < 9 && row > 0 && col > 0) {

                chessPosition = new ChessPosition(row, col);



                if (board.getPiece(chessPosition) != null) {
                    if (board.getPiece(chessPosition).getTeamColor() != color) { //enemy

                        chessMove = new ChessMove(oldPosition, chessPosition, null);
                        possibleMoves.add(chessMove);
                        break;
                    } else { //friend
                        break;
                    }
                } else {
                    chessMove = new ChessMove(oldPosition, chessPosition, null);
                    possibleMoves.add(chessMove);
                }
            }
            else {
                break;
            }
        }

        //Reset
        row = oldPosition.getRow();
        col = oldPosition.getColumn();


        //Check right DOWN


        while (n) {
            col = col + 1;
            row = row - 1;

            if (row < 9 && col < 9 && row > 0 && col > 0) {

                chessPosition = new ChessPosition(row, col);



                if (board.getPiece(chessPosition) != null) {
                    if (board.getPiece(chessPosition).getTeamColor() != color) { //enemy

                        chessMove = new ChessMove(oldPosition, chessPosition, null);
                        possibleMoves.add(chessMove);
                        break;
                    } else { //friend
                        break;
                    }
                } else {
                    chessMove = new ChessMove(oldPosition, chessPosition, null);
                    possibleMoves.add(chessMove);
                }
            }
            else {
                break;
            }
        }
        return possibleMoves;
    }
}

