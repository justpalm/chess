package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor OppositeTeam;
    private TeamColor CurrentTeam;
    ChessBoard game_board;
    ChessPosition WhiteKing;
    ChessPosition BlackKing;


    public ChessGame() {
        this.CurrentTeam = null;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return CurrentTeam;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        if (CurrentTeam == null) {
            CurrentTeam = TeamColor.WHITE;
            OppositeTeam = TeamColor.BLACK;
            return;
        }

        if (CurrentTeam == TeamColor.WHITE) {
            CurrentTeam = TeamColor.BLACK;
            OppositeTeam = TeamColor.WHITE;
        }

        if (CurrentTeam == TeamColor.BLACK) {
            CurrentTeam = TeamColor.WHITE;
            OppositeTeam = TeamColor.BLACK;
        }
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */

    private Collection<ChessMove> check_check(ChessPiece piece, Collection<ChessMove> og_valid_moves, ChessBoard copy_of_old) {

        Collection<ChessMove> new_valid_moves = new ArrayList<>();


        for (ChessMove move : og_valid_moves) {
            ChessBoard new_old = copy_of_old;
            new_old.addPiece(move.getEndPosition(), piece);
            new_old.addPiece(move.getStartPosition(), null);

            for (int row = 1; row < 9; row++) {
                for (int col = 1; col < 9; col++) {
                    ChessPosition new_position = new ChessPosition(row, col);
                    if (new_old.getPiece(new_position) != null) {
                        ChessPiece new_piece = new_old.getPiece(new_position);
                        Collection<ChessMove> new_piece_moves = new_piece.pieceMoves(new_old, new_position);
                        for (ChessMove new_piece_move : new_piece_moves) {
                            if (CurrentTeam != TeamColor.WHITE) {
                                if (new_piece_move.getEndPosition() != WhiteKing) {
                                    new_valid_moves.add(move);
                                }
                            }
                            if (CurrentTeam != TeamColor.BLACK) {
                                if (new_piece_move.getEndPosition() != BlackKing) {
                                    new_valid_moves.add(move);
                                }
                            }
                        }
                    }
                }
            }
        }
    return new_valid_moves;
    }
// Make a copy of the old board, and then on the old board, move the piece
// From there, have it run through all the piece calculator stuff
// Every time it generates, check to see if it any of those valid spaces
// Check with the King Positions.
// If any of those new moves don't mess with the King, return og
// If it does put the Same teams color in check, return edited.


public Collection<ChessMove> validMoves(ChessPosition startPosition) {
    if (game_board.getPiece(startPosition) != null) {
        ChessPiece piece = game_board.getPiece(startPosition);
        Collection<ChessMove> og_valid_moves = piece.pieceMoves(game_board, startPosition);
        ChessBoard copy_of_old = game_board;
        Collection<ChessMove> new_valid_moves = check_check(piece, og_valid_moves, copy_of_old);

        return new_valid_moves;
        else{
            return null;
        }
    }
}

/**
 * Makes a move in a chess game
 *
 * @param move chess move to perform
 * @throws InvalidMoveException if move is invalid
 */


public void makeMove(ChessMove move) throws InvalidMoveException {
    Collection<ChessMove> valid_moves = validMoves(move.getStartPosition());

    if (valid_moves.contains(move)) {

        game_board.addPiece(move.getEndPosition(), game_board.getPiece(move.getStartPosition()));
        game_board.addPiece(move.getStartPosition(), null);

    }
    else {
        throw new InvalidMoveException("Invalid move");
    }

}

/**
 * Determines if the given team is in check
 *
 * @param teamColor which team to check for check
 * @return True if the specified team is in check
 */
public boolean isInCheck(TeamColor teamColor) {
    // Cycle through every piece on the board
    // If there's an enemy piece, see if they have moves on the King
    // If it goes through the whole board and no one can get him,
    // no in Check.

    //If it can, then check






    throw new RuntimeException("Not implemented");
}

/**
 * Determines if the given team is in checkmate
 *
 * @param teamColor which team to check for checkmate
 * @return True if the specified team is in checkmate
 */
public boolean isInCheckmate(TeamColor teamColor) {

    //I think from this we could just gather the list from above,
    //And say that "Hey, here are all the illegal moves that can't be made
    //We'll also have to say 'hey, if the King moves does that change things




    throw new RuntimeException("Not implemented");
}

/**
 * Determines if the given team is in stalemate, which here is defined as having
 * no valid moves while not in check.
 *
 * @param teamColor which team to check for stalemate
 * @return True if the specified team is in stalemate, otherwise false
 */
public boolean isInStalemate(TeamColor teamColor) {
    throw new RuntimeException("Not implemented");
}

/**
 * Sets this game's chessboard with a given board
 *
 * @param board the new board to use
 */
public void setBoard(ChessBoard board) {
    game_board = board;
    game_board.resetBoard();

}

/**
 * Gets the current chessboard
 *
 * @return the chessboard
 */
public ChessBoard getBoard() {
    return game_board;
}


}


