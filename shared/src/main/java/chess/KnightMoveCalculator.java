package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMoveCalculator implements PieceMoveCalculator{


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



        //CHeck top left
        col -= 1;
        row += 2;

        chessPosition = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(chessPosition) == null) {
                chessMove = new ChessMove(oldPosition, chessPosition, null);
                possibleMoves.add(chessMove);
            }
            else {
                if (board.getPiece(chessPosition).getTeamColor() != color) {
                    chessMove = new ChessMove(oldPosition, chessPosition, null);
                    possibleMoves.add(chessMove);
                }
            }
        }

        //Reset Values
        row = oldPosition.getRow();
        col = oldPosition.getColumn();


        //Check top right
        col += 1;
        row += 2;

        chessPosition = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(chessPosition) == null) {
                chessMove = new ChessMove(oldPosition, chessPosition, null);
                possibleMoves.add(chessMove);
            }
            else {
                if (board.getPiece(chessPosition).getTeamColor() != color) {
                    chessMove = new ChessMove(oldPosition, chessPosition, null);
                    possibleMoves.add(chessMove);
                }
            }
        }



        //Reset Values
        row = oldPosition.getRow();
        col = oldPosition.getColumn();

        //Check middle top right
        col += 2;
        row += 1;

        chessPosition = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(chessPosition) == null) {
                chessMove = new ChessMove(oldPosition, chessPosition, null);
                possibleMoves.add(chessMove);
            }
            else {
                if (board.getPiece(chessPosition).getTeamColor() != color) {
                    chessMove = new ChessMove(oldPosition, chessPosition, null);
                    possibleMoves.add(chessMove);
                }
            }
        }



        //Reset Values
        row = oldPosition.getRow();
        col = oldPosition.getColumn();

        //Check middle bottom right
        col += 2;
        row -= 1;

        chessPosition = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(chessPosition) == null) {
                chessMove = new ChessMove(oldPosition, chessPosition, null);
                possibleMoves.add(chessMove);
            }
            else {
                if (board.getPiece(chessPosition).getTeamColor() != color) {
                    chessMove = new ChessMove(oldPosition, chessPosition, null);
                    possibleMoves.add(chessMove);
                }
            }
        }



        //Reset Values
        row = oldPosition.getRow();
        col = oldPosition.getColumn();

        //Check down right
        col += 1;
        row -= 2;

        chessPosition = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(chessPosition) == null) {
                chessMove = new ChessMove(oldPosition, chessPosition, null);
                possibleMoves.add(chessMove);
            }
            else {
                if (board.getPiece(chessPosition).getTeamColor() != color) {
                    chessMove = new ChessMove(oldPosition, chessPosition, null);
                    possibleMoves.add(chessMove);
                }
            }
        }



        //Reset Values
        row = oldPosition.getRow();
        col = oldPosition.getColumn();

        //Check down left
        col -= 1;
        row -= 2;

        chessPosition = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(chessPosition) == null) {
                chessMove = new ChessMove(oldPosition, chessPosition, null);
                possibleMoves.add(chessMove);
            }
            else {
                if (board.getPiece(chessPosition).getTeamColor() != color) {
                    chessMove = new ChessMove(oldPosition, chessPosition, null);
                    possibleMoves.add(chessMove);
                }
            }
        }


        //Reset Values
        row = oldPosition.getRow();
        col = oldPosition.getColumn();

        //Check middle bottom left
        col -= 2;
        row -= 1;

        chessPosition = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(chessPosition) == null) {
                chessMove = new ChessMove(oldPosition, chessPosition, null);
                possibleMoves.add(chessMove);
            }
            else {
                if (board.getPiece(chessPosition).getTeamColor() != color) {
                    chessMove = new ChessMove(oldPosition, chessPosition, null);
                    possibleMoves.add(chessMove);
                }
            }
        }


        //Reset Values
        row = oldPosition.getRow();
        col = oldPosition.getColumn();

        //Check middle top left
        col -= 2;
        row += 1;

        chessPosition = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(chessPosition) == null) {
                chessMove = new ChessMove(oldPosition, chessPosition, null);
                possibleMoves.add(chessMove);
            }
            else {
                if (board.getPiece(chessPosition).getTeamColor() != color) {
                    chessMove = new ChessMove(oldPosition, chessPosition, null);
                    possibleMoves.add(chessMove);
                }
            }
        }
        return possibleMoves;
    }
}


