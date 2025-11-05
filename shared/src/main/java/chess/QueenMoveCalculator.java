package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMoveCalculator implements PieceMoveCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition oldPosition) {


        Collection<ChessMove> possibleMoves = new ArrayList<>();
        ChessPiece piece = board.getPiece(oldPosition);
        ChessGame.TeamColor color = piece.getTeamColor();

        ChessMove chessMove;
        ChessPosition chessPosition;

        //Set Values
        int row = oldPosition.getRow();
        int col = oldPosition.getColumn();


        //Check Up

        while (true) {
            row += 1;

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



        //Check Right
        while (true) {
            col += 1;

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


        //Check Down

        while (true) {
            row -= 1;

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



        //Check Left

        while (true) {
            col -= 1;

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







        //Check left UP LOOP (minus a column, up a row)
        while (true) {
            col -= 1;
            row += 1;

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


        //Check right UP

        while (true) {
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

        while (true) {
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


        while (true) {
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
