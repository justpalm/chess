package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor currentTeam;
    ChessBoard chessBoard = new ChessBoard();
    ChessPosition whiteKing;
    ChessPosition blackKing;


    public ChessGame() {
        chessBoard.resetBoard();
        currentTeam = TeamColor.WHITE;
        whiteKing = new ChessPosition(1,4);
        blackKing = new ChessPosition(8,5);
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return currentTeam;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        currentTeam = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    private Collection<ChessMove> checkCheck(ChessPiece piece, Collection<ChessMove> ogValidMoves) {

        Collection<ChessMove> newValidMoves = new ArrayList<>();

        //Go through every move in the valid set to see the hypotheticals of moving that piece
        for (ChessMove move : ogValidMoves) {


            ChessPosition hypoWhiteKing = new ChessPosition(whiteKing.getRow(), whiteKing.getColumn());
            ChessPosition hypoBlackKing = new ChessPosition(blackKing.getRow(), blackKing.getColumn());
            //Make a copy of the board
            ChessBoard copyOfOgBoard = chessBoard.clone();

            //move the piece according to the move on the HYPOTHETICAL board
            copyOfOgBoard.addPiece(move.getEndPosition(), piece);
            copyOfOgBoard.addPiece(move.getStartPosition(), null);

            //Resetting King Positions
            if (piece.getPieceType() == ChessPiece.PieceType.KING) {

                if (piece.getTeamColor() == TeamColor.WHITE) {
                    hypoWhiteKing = move.getEndPosition();
                }

                if (piece.getTeamColor() == TeamColor.BLACK) {
                    hypoBlackKing = move.getEndPosition();
                }

            }

            if (!helperIsInCheck(copyOfOgBoard, piece.getTeamColor(), hypoWhiteKing, hypoBlackKing)) {
                newValidMoves.add(move);
            }
        }
        return newValidMoves;
    }


// Old comment code

// Make a copy of the old board, and then on the old board, move the piece
// From there, have it run through all the piece calculator stuff
// Every time it generates, check to see if it any of those valid spaces
// Check with the King Positions.
// If any of those new moves don't mess with the King, return og
// If it does put the Same teams color in check, return edited.

    private boolean helperIsInCheck(ChessBoard copyBoard, TeamColor teamColor, ChessPosition hypoWhiteKing, ChessPosition hypoBlackKing) {
        boolean state = false;
        //This just lets me check for checks on other hypothetical boards.


        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {

                ChessPosition chessPosition = new ChessPosition(row, col);
                ChessPiece enemyPiece = copyBoard.getPiece(chessPosition);

                if (enemyPiece != null) {
                    if (enemyPiece.getTeamColor() != teamColor) {
                        state = isState(teamColor, state, chessPosition,
                                enemyPiece, copyBoard, hypoWhiteKing, hypoBlackKing);
                    }
                }
            }
        }
        return state;
    }


    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */


    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        //If that position has a piece
        Collection<ChessMove> newValidMoves = new ArrayList<>();

        ChessPiece pieceInQuestion = chessBoard.getPiece(startPosition);

        if (pieceInQuestion != null) {
            //Make a piece object
            ChessPiece piece = chessBoard.getPiece(startPosition);
            //Here's the old valid moves
            Collection<ChessMove> ogValidMoves = piece.pieceMoves(chessBoard, startPosition);
            //Here are the new valid moves.
            newValidMoves = checkCheck(piece, ogValidMoves);
        }

        //Return the valid move list
        return newValidMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */


    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());

        if (validMoves.contains(move)) {

//            if (game_board.getPiece(move.getStartPosition()) != null) {
            //Do the move by adding and removing whatever pieces we have to
            ChessPiece movingPiece = chessBoard.getPiece(move.getStartPosition());

            if (movingPiece.getTeamColor() != currentTeam) {
                throw new InvalidMoveException("Invalid move requested");
            }

            //If there is a promotion
            if (move.getPromotionPiece() != null) {
                movingPiece = new ChessPiece(currentTeam, move.getPromotionPiece());
            }

            chessBoard.addPiece(move.getEndPosition(), movingPiece);
            chessBoard.addPiece(move.getStartPosition(), null);



            //This is to check to update our King Position
            ChessPiece pieceMoved = chessBoard.getPiece(move.getEndPosition());
            if (pieceMoved != null) {
                if (pieceMoved.getPieceType() == ChessPiece.PieceType.KING) {
                    if (currentTeam == TeamColor.WHITE) {
                        whiteKing = move.getEndPosition();
                    } else {
                        blackKing = move.getEndPosition();
                    }
                }
            }
            if (currentTeam == TeamColor.WHITE) {
                setTeamTurn(TeamColor.BLACK);
            } else {
                setTeamTurn(TeamColor.WHITE);
            }
        } else {
            throw new InvalidMoveException("Invalid move requested");
        }

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        boolean state = false;
        //Copied from check_Check, the logic is slightly different though as it wants a positive rather than a negative
        //And doesn't make a new list
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {

                ChessPosition newPosition = new ChessPosition(row, col);
                ChessPiece enemyPiece = chessBoard.getPiece(newPosition);

                if (enemyPiece != null && enemyPiece.getTeamColor() != teamColor) {
                    state = isState(teamColor, state, newPosition, enemyPiece, chessBoard, whiteKing, blackKing);
                }
            }
        }
        return state;
    }

    private boolean isState(TeamColor teamColor, boolean state,
                            ChessPosition newPosition, ChessPiece enemyPiece,
                            ChessBoard chessBoard, ChessPosition whiteKing, ChessPosition blackKing)
    {Collection<ChessMove> enemyPieceMoves = enemyPiece.pieceMoves(chessBoard, newPosition);
        for (ChessMove enemyPieceMove : enemyPieceMoves) {
            if (teamColor == TeamColor.WHITE) {
                if (enemyPieceMove.getEndPosition().equals(whiteKing)) {state = true;}
            }
            if (teamColor == TeamColor.BLACK) {if (enemyPieceMove.getEndPosition().equals(blackKing)) {
                state = true;
            }}
        }
        return state;
    }


    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */

    public boolean isInCheckmate(TeamColor teamColor) {
        if (isInCheck(teamColor)) {
            return helperIsInStalemate(teamColor);
        }
        return false;
    }


    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (isInCheck(teamColor)) {
            return false;
        }
        return checkColors(teamColor);
    }

    private boolean checkColors(TeamColor teamColor) {
        if (teamColor == TeamColor.WHITE) {
            if (!validMoves(whiteKing).isEmpty()) {
                return false;
            }
        } else {
            if (!validMoves(blackKing).isEmpty()) {
                return false;
            }
        }

        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition newPosition = new ChessPosition(row, col);
                ChessPiece myPiece = chessBoard.getPiece(newPosition);

                if (myPiece != null && myPiece.getTeamColor() == teamColor && !validMoves(newPosition).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean helperIsInStalemate(TeamColor teamColor) {

        return checkColors(teamColor);
    }


    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {


        chessBoard = board;


        //Sets the positions for the Kings.
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition checkPosition = new ChessPosition(row, col);
                ChessPiece pieceInQuestion = chessBoard.getPiece(checkPosition);
                if (pieceInQuestion != null) {
                    if (pieceInQuestion.getPieceType() == ChessPiece.PieceType.KING &&
                            pieceInQuestion.getTeamColor() == TeamColor.WHITE) {

                        whiteKing = checkPosition;
                    }
                    if (pieceInQuestion.getPieceType() == ChessPiece.PieceType.KING &&
                            pieceInQuestion.getTeamColor() == TeamColor.BLACK) {

                        blackKing = checkPosition;
                    }

                }
            }
        }

    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return chessBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return currentTeam == chessGame.currentTeam && Objects.equals(chessBoard, chessGame.chessBoard)
                && Objects.equals(whiteKing, chessGame.whiteKing) && Objects.equals(blackKing, chessGame.blackKing);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentTeam, chessBoard, whiteKing, blackKing);
    }
}






