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
            return;
        }

        if (CurrentTeam == TeamColor.WHITE) {
            CurrentTeam = TeamColor.BLACK;
            return;
        }

        if (CurrentTeam == TeamColor.BLACK) {
            CurrentTeam = TeamColor.WHITE;
            return;
        }
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    private Collection<ChessMove> check_check(ChessPiece piece, Collection<ChessMove> og_valid_moves) {

        Collection<ChessMove> new_valid_moves = new ArrayList<>();

        //Go through every move in the valid set to see the hypotheticals of moving that piece
        for (ChessMove move : og_valid_moves) {
            //Make a copy of the
            ChessBoard copy_of_og_board = game_board;

            //move the piece according to the move on the HYPOTHETICAL board
            copy_of_og_board.addPiece(move.getEndPosition(), piece);
            copy_of_og_board.addPiece(move.getStartPosition(), null);



            //Setting variable
            boolean move_is_valid = true;

            //Here's us checking if this will cause the King to be in check
            //Check every spot on the board.
            outerLoop:
            for (int row = 1; row < 9; row++) {
                for (int col = 1; col < 9; col++) {
                    //Check the position on the board
                    ChessPosition new_position = new ChessPosition(row, col);
                    //If there's a piece, and it's an enemy piece

                    //Make a piece object of the enemy piece
                    ChessPiece enemy_piece = copy_of_og_board.getPiece(new_position);

                    if (enemy_piece != null) {
                        if (enemy_piece.getTeamColor() != CurrentTeam) {
                            //Here's a list of all it's possible moves
                            Collection<ChessMove> enemy_piece_moves = enemy_piece.pieceMoves(copy_of_og_board, new_position);
                            //Check the end result of every move of the enemy piece
                            for (ChessMove enemy_piece_move : enemy_piece_moves) {
                                //If team is black
                                if (CurrentTeam == TeamColor.BLACK) {
                                    //If it can get the King, it's not a valid move
                                    if (enemy_piece_move.getEndPosition() == BlackKing) {
                                        move_is_valid = false;
                                    }
                                }
                                //If team is white
                                if (CurrentTeam == TeamColor.WHITE) {
                                    //If it can get the King, it's not a valid move
                                    if (enemy_piece_move.getEndPosition() == WhiteKing) {
                                        move_is_valid = false;
                                    }
                                }

                                //If it's an invalid move, break the lop checking moves.
                                if (!move_is_valid) {
                                    break outerLoop;
                                }

                            }
                        }
                    }
                }
            }

            //If the move is valid, add it to the new_valid_move list
            if (move_is_valid) {
                new_valid_moves.add(move);
            }
        }
        return new_valid_moves;
    }


// Old comment code

// Make a copy of the old board, and then on the old board, move the piece
// From there, have it run through all the piece calculator stuff
// Every time it generates, check to see if it any of those valid spaces
// Check with the King Positions.
// If any of those new moves don't mess with the King, return og
// If it does put the Same teams color in check, return edited.




    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */


public Collection<ChessMove> validMoves(ChessPosition startPosition) {
    //If that position has a piece
    if (game_board.getPiece(startPosition) != null) {
        //Make a piece object
        ChessPiece piece = game_board.getPiece(startPosition);
        //Here's the old valid moves
        Collection<ChessMove> og_valid_moves = piece.pieceMoves(game_board, startPosition);
        //Here are the new valid moves.
        Collection<ChessMove> new_valid_moves = check_check(piece, og_valid_moves);


        //Return the valid move list
        return new_valid_moves;
    }
        else {
            return null;
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

        //Do the move by adding and removing whatever pieces we have to
        game_board.addPiece(move.getEndPosition(), game_board.getPiece(move.getStartPosition()));
        game_board.addPiece(move.getStartPosition(), null);

        //This is to check to update our King Position
        ChessPiece enemy
        if (game_board.getPiece(move.getEndPosition()).getPieceType() == ChessPiece.PieceType.KING)
            if (CurrentTeam == TeamColor.WHITE) {
                WhiteKing = move.getEndPosition();
            }
            else {
                BlackKing = move.getEndPosition();
            }


    }
    else {
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
                ChessPosition new_position = new ChessPosition(row, col);
                ChessPiece enemy_piece = game_board.getPiece(new_position);
                if (enemy_piece != null) {
                    if (enemy_piece.getTeamColor() != CurrentTeam) {
                        ChessPiece new_piece = game_board.getPiece(new_position);
                        Collection<ChessMove> new_piece_moves = new_piece.pieceMoves(game_board, new_position);
                        for (ChessMove new_piece_move : new_piece_moves) {
                            if (CurrentTeam != TeamColor.WHITE) {
                                if (new_piece_move.getEndPosition() == WhiteKing) {
                                    state = true;
                                }
                            }
                            if (CurrentTeam != TeamColor.BLACK) {
                                if (new_piece_move.getEndPosition() == BlackKing) {
                                    state = true;
                                }
                            }
                        }
                    }
                }
            }
        }
    return true;
}


/**
 * Determines if the given team is in checkmate
 *
 * @param teamColor which team to check for checkmate
 * @return True if the specified team is in checkmate
 */

public boolean isInCheckmate(TeamColor teamColor) {
    if (isInCheck(teamColor)) {
        return helper_isInStalemate(teamColor);

            //I think from this we could just gather the list from above,
            //And say that "Hey, here are all the illegal moves that can't be made
            //We'll also have to say 'hey, if the King moves does that change things

    }
    return false;

}


private boolean helper_isInStalemate(TeamColor teamColor) {

    //I had to create this for checking checkmate, as a checkmate is a stalemeate but also already
    //In check. So, this is the slightly modifiend function. Couldn't think of a more elegant way to do it.


    ChessPiece King = new ChessPiece(teamColor, ChessPiece.PieceType.KING);
    Collection<ChessMove> KingMoves = new ArrayList<>();

    if (teamColor == TeamColor.WHITE) {
        KingMoves = King.pieceMoves(game_board, WhiteKing);
    }
    else {
        KingMoves = King.pieceMoves(game_board, BlackKing);
    }

//    if (!isInCheck(teamColor)) {
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition new_position = new ChessPosition(row, col);
                ChessPiece enemy_piece = game_board.getPiece(new_position);
                if (enemy_piece != null) {
                    if (enemy_piece.getTeamColor() != CurrentTeam) {
                        Collection<ChessMove> enemy_piece_moves = enemy_piece.pieceMoves(game_board, new_position);
                        for (ChessMove enemy_piece_move : enemy_piece_moves) {
                            KingMoves.remove(enemy_piece_move);
                        }
                    }
                }
            }
        }
//    }
    return KingMoves.isEmpty();


}

/**
 * Determines if the given team is in stalemate, which here is defined as having
 * no valid moves while not in check.
 *
 * @param teamColor which team to check for stalemate
 * @return True if the specified team is in stalemate, otherwise false
 */
public boolean isInStalemate(TeamColor teamColor) {

    ChessPiece King = new ChessPiece(teamColor, ChessPiece.PieceType.KING);
    Collection<ChessMove> KingMoves = new ArrayList<>();

    if (teamColor == TeamColor.WHITE) {
    KingMoves = King.pieceMoves(game_board, WhiteKing);
    }

    else {
    KingMoves = King.pieceMoves(game_board, BlackKing);
    }

    if (!isInCheck(teamColor)) {
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition new_position = new ChessPosition(row, col);
                ChessPiece enemy_piece = game_board.getPiece(new_position);
                if (enemy_piece != null) {
                    if (enemy_piece.getTeamColor() != CurrentTeam) {;
                        Collection<ChessMove> enemy_piece_moves = enemy_piece.pieceMoves(game_board, new_position);
                        for (ChessMove enemy_piece_move : enemy_piece_moves) {
                            KingMoves.remove(enemy_piece_move);
                        }
                    }
                }
            }
        }
    }
    return KingMoves.isEmpty();
}

/**
 * Sets this game's chessboard with a given board
 *
 * @param board the new board to use
 */
public void setBoard(ChessBoard board) {
    game_board = board;
    game_board.resetBoard();
    WhiteKing = new ChessPosition(1,5);
    BlackKing = new ChessPosition(8, 5);

}

/**
 * Gets the current chessboard
 *
 * @return the chessboard
 */
public ChessBoard getBoard() {
    return game_board;
}


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return CurrentTeam == chessGame.CurrentTeam && Objects.equals(game_board, chessGame.game_board) && Objects.equals(WhiteKing, chessGame.WhiteKing) && Objects.equals(BlackKing, chessGame.BlackKing);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CurrentTeam, game_board, WhiteKing, BlackKing);
    }
}


