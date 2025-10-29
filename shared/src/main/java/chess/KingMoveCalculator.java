package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMoveCalculator implements PieceMoveCalculator {

    public static int resetRow(ChessPosition oldPosition) {
        return oldPosition.getRow();
    }

    public static int resetCol(ChessPosition oldPosition) {
        return oldPosition.getColumn();
    }


    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition oldPosition) {

        Collection<ChessMove> possibleMoves = new ArrayList<>();
        ChessPiece piece = board.getPiece(oldPosition);
        ChessGame.TeamColor color = piece.getTeamColor();

        //Set Values
        int row = resetRow(oldPosition);
        int col = resetCol(oldPosition);

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
        row = resetRow(oldPosition);
        col = resetCol(oldPosition);

        //Check Right Up
        row += 1;
        col++;

        newPosition = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(newPosition) == null) {
                newMove = new ChessMove(oldPosition, newPosition, null);
                possibleMoves.add(newMove);
            } else {
                if (board.getPiece(newPosition).getTeamColor() != color) {
                    newMove = new ChessMove(oldPosition, newPosition, null);
                    possibleMoves.add(newMove);
                }
            }
        }


        //Reset
        row = resetRow(oldPosition);
        col = resetCol(oldPosition);

        //Check Right
        col += 1;
        newPosition = new ChessPosition(row, col);

        if (row < 9 && col < 9 && row > 0 && col > 0) {
            if (board.getPiece(newPosition) == null) {
                newMove = new ChessMove(oldPosition, newPosition, null);
                possibleMoves.add(newMove);
            } else {
                if (board.getPiece(newPosition).getTeamColor() != color) {
                    newMove = new ChessMove(oldPosition, newPosition, null);
                    possibleMoves.add(newMove);
                }
            }
        }


        //Reset
        row = resetRow(oldPosition);
        col = resetCol(oldPosition);

        //Check Right
        col += 1;


        //Reset

        //Check Down Right
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
        row = resetRow(oldPosition);
        col = resetCol(oldPosition);

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
        row = resetRow(oldPosition);
        col = resetCol(oldPosition);

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
        row = resetRow(oldPosition);
        col = resetCol(oldPosition);

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
        row = resetRow(oldPosition);
        col = resetCol(oldPosition);


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





