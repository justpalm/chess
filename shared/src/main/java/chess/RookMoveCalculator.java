package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoveCalculator implements PieceMoveCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition oldPositionRow) {

        Collection<ChessMove> possibleMoves = new ArrayList<>();
        ChessPiece piece = board.getPiece(oldPositionRow);
        ChessGame.TeamColor color = piece.getTeamColor();

        ChessMove newMove;
        ChessPosition chessPosition;

        //Set Values
        int row = oldPositionRow.getRow();
        int col = oldPositionRow.getColumn();
        boolean n = true;


        //Check Up

        while (n) {
            row += 1;

            if (row < 9 && col < 9 && row > 0 && col > 0) {

                chessPosition = new ChessPosition(row, col);


                if (board.getPiece(chessPosition) != null) {
                    if (board.getPiece(chessPosition).getTeamColor() != color) { //enemy

                        newMove = new ChessMove(oldPositionRow, chessPosition, null);
                        possibleMoves.add(newMove);
                        break;
                    } else { //friend
                        break;
                    }
                } else {
                    newMove = new ChessMove(oldPositionRow, chessPosition, null);
                    possibleMoves.add(newMove);
                }
            }
            else {
                break;
            }
        }
        //Reset
        row = oldPositionRow.getRow();
        col = oldPositionRow.getColumn();



        //Check Right
        while (n) {
            col += 1;

            if (row < 9 && col < 9 && row > 0 && col > 0) {

                chessPosition = new ChessPosition(row, col);


                if (board.getPiece(chessPosition) != null) {
                    if (board.getPiece(chessPosition).getTeamColor() != color) { //enemy

                        newMove = new ChessMove(oldPositionRow, chessPosition, null);
                        possibleMoves.add(newMove);
                        break;
                    } else { //friend
                        break;
                    }
                } else {
                    newMove = new ChessMove(oldPositionRow, chessPosition, null);
                    possibleMoves.add(newMove);
                }
            }
            else {
                break;
            }
        }
        //Reset
        row = oldPositionRow.getRow();
        col = oldPositionRow.getColumn();


        //Check Down

        while (n) {
            row -= 1;

            if (row < 9 && col < 9 && row > 0 && col > 0) {

                chessPosition = new ChessPosition(row, col);


                if (board.getPiece(chessPosition) != null) {
                    if (board.getPiece(chessPosition).getTeamColor() != color) { //enemy

                        newMove = new ChessMove(oldPositionRow, chessPosition, null);
                        possibleMoves.add(newMove);
                        break;
                    } else { //friend
                        break;
                    }
                } else {
                    newMove = new ChessMove(oldPositionRow, chessPosition, null);
                    possibleMoves.add(newMove);
                }
            }
            else {
                break;
            }
        }
        //Reset
        row = oldPositionRow.getRow();
        col = oldPositionRow.getColumn();



        //Check Left

        while (n) {
            col -= 1;

            if (row < 9 && col < 9 && row > 0 && col > 0) {

                chessPosition = new ChessPosition(row, col);


                if (board.getPiece(chessPosition) != null) {
                    if (board.getPiece(chessPosition).getTeamColor() != color) { //enemy

                        newMove = new ChessMove(oldPositionRow, chessPosition, null);
                        possibleMoves.add(newMove);
                        break;
                    } else { //friend
                        break;
                    }
                } else {
                    newMove = new ChessMove(oldPositionRow, chessPosition, null);
                    possibleMoves.add(newMove);
                }
            }
            else {
                break;
            }
        }
        return possibleMoves;
    }
}
