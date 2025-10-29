package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMoveCalculator implements PieceMoveCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition oldPosition) {

        Collection<ChessMove> possibleMoves = new ArrayList<>();
        ChessPiece piece = board.getPiece(oldPosition);
        ChessGame.TeamColor color = piece.getTeamColor();



        //Set Values
        int row = oldPosition.getRow();
        int col = oldPosition.getColumn();

        ChessMove newMove;
        ChessPosition newPosition;


        //Check Up
        row += 1;

        newPosition = new ChessPosition(row, col);


        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(newPosition) == null) {
                newMove = new ChessMove(oldPosition, newPosition, null);
                possibleMoves.add(newMove);
            }
            else {
                if (board.getPiece(newPosition).getTeamColor() != color) {
                    newMove = new ChessMove(oldPosition, newPosition, null);
                    possibleMoves.add(newMove);
                }
            }
        }

        //Reset
        row = oldPosition.getRow();
        col = oldPosition.getColumn();

        //Check Right Up
        row += 1;
        col += 1;

        newPosition = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(newPosition) == null) {
                newMove = new ChessMove(oldPosition, newPosition, null);
                possibleMoves.add(newMove);
            }
            else {
                if (board.getPiece(newPosition).getTeamColor() != color) {
                    newMove = new ChessMove(oldPosition, newPosition, null);
                    possibleMoves.add(newMove);
                }
            }
        }


        //Reset
        row = oldPosition.getRow();
        col = oldPosition.getColumn();

        //Check Right
        col += 1;

        newPosition = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(newPosition) == null) {
                newMove = new ChessMove(oldPosition, newPosition, null);
                possibleMoves.add(newMove);
            }
            else {
                if (board.getPiece(newPosition).getTeamColor() != color) {
                    newMove = new ChessMove(oldPosition, newPosition, null);
                    possibleMoves.add(newMove);
                }
            }
        }


        //Reset
        row = oldPosition.getRow();
        col = oldPosition.getColumn();

        //Check Down Right
        col += 1;
        row -= 1;

        newPosition = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(newPosition) == null) {
                newMove = new ChessMove(oldPosition, newPosition, null);
                possibleMoves.add(newMove);
            }
            else {
                if (board.getPiece(newPosition).getTeamColor() != color) {
                    newMove = new ChessMove(oldPosition, newPosition, null);
                    possibleMoves.add(newMove);
                }
            }
        }


        //Reset
        row = oldPosition.getRow();
        col = oldPosition.getColumn();

        //Check Down
        row -= 1;

        newPosition = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(newPosition) == null) {
                newMove = new ChessMove(oldPosition, newPosition, null);
                possibleMoves.add(newMove);
            }
            else {
                if (board.getPiece(newPosition).getTeamColor() != color) {
                    newMove = new ChessMove(oldPosition, newPosition, null);
                    possibleMoves.add(newMove);
                }
            }
        }


        //Reset
        row = oldPosition.getRow();
        col = oldPosition.getColumn();

        //Check Down Left
        row -= 1;
        col -= 1;

        newPosition = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(newPosition) == null) {
                newMove = new ChessMove(oldPosition, newPosition, null);
                possibleMoves.add(newMove);
            }
            else {
                if (board.getPiece(newPosition).getTeamColor() != color) {
                    newMove = new ChessMove(oldPosition, newPosition, null);
                    possibleMoves.add(newMove);
                }
            }
        }

        //Reset
        row = oldPosition.getRow();
        col = oldPosition.getColumn();

        //Check Left
        col -= 1;

        newPosition = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(newPosition) == null) {
                newMove = new ChessMove(oldPosition, newPosition, null);
                possibleMoves.add(newMove);
            }
            else {
                if (board.getPiece(newPosition).getTeamColor() != color) {
                    newMove = new ChessMove(oldPosition, newPosition, null);
                    possibleMoves.add(newMove);
                }
            }
        }

        //Reset
        row = oldPosition.getRow();
        col = oldPosition.getColumn();


        //Check Left Up
        row += 1;
        col -= 1;

        newPosition = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(newPosition) == null) {
                newMove = new ChessMove(oldPosition, newPosition, null);
                possibleMoves.add(newMove);
            }
            else {
                if (board.getPiece(newPosition).getTeamColor() != color) {
                    newMove = new ChessMove(oldPosition, newPosition, null);
                    possibleMoves.add(newMove);
                }
            }
        }
        return possibleMoves;
    }
}





