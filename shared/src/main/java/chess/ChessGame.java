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
    private TeamColor CurrentTeam;
    ChessBoard game_board = new ChessBoard();
    ChessPosition WhiteKing;
    ChessPosition BlackKing;


    public ChessGame() {
        game_board.resetBoard();
        CurrentTeam = TeamColor.WHITE;
        WhiteKing = new ChessPosition(1,4);
        BlackKing = new ChessPosition(8,5);
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
        CurrentTeam = team;
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


            ChessPosition hypo_white_king = new ChessPosition(WhiteKing.getRow(), WhiteKing.getColumn());
            ChessPosition hypo_black_king = new ChessPosition(BlackKing.getRow(), BlackKing.getColumn());
            //Make a copy of the board
            ChessBoard copy_of_og_board = game_board.clone();

            //move the piece according to the move on the HYPOTHETICAL board
            copy_of_og_board.addPiece(move.getEndPosition(), piece);
            copy_of_og_board.addPiece(move.getStartPosition(), null);

            //Resetting King Positions
            if (piece.getPieceType() == ChessPiece.PieceType.KING) {

                if (piece.getTeamColor() == TeamColor.WHITE) {
                    hypo_white_king = move.getEndPosition();
                }

                if (piece.getTeamColor() == TeamColor.BLACK) {
                    hypo_black_king = move.getEndPosition();
                }

            }

            if (!helper_isInCheck(copy_of_og_board, piece.getTeamColor(), hypo_white_king, hypo_black_king)) {
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

    private boolean helper_isInCheck(ChessBoard copy_board, TeamColor teamColor, ChessPosition hypo_white_king, ChessPosition hypo_black_king) {
        boolean state = false;
        //This just lets me check for checks on other hypothetical boards.


        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {

                ChessPosition new_position = new ChessPosition(row, col);
                ChessPiece enemy_piece = copy_board.getPiece(new_position);

                if (enemy_piece != null) {
                    if (enemy_piece.getTeamColor() != teamColor) {
                        Collection<ChessMove> enemy_piece_moves = enemy_piece.pieceMoves(copy_board, new_position);
                        for (ChessMove enemy_piece_move : enemy_piece_moves) {
                            if (teamColor == TeamColor.WHITE) {
                                if (enemy_piece_move.getEndPosition().equals(hypo_white_king)) {
                                    state = true;
                                }
                            }
                            if (teamColor == TeamColor.BLACK) {
                                if (enemy_piece_move.getEndPosition().equals(hypo_black_king)) {
                                    state = true;
                                }
                            }
                        }
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
        Collection<ChessMove> new_valid_moves = new ArrayList<>();

        ChessPiece piece_in_question = game_board.getPiece(startPosition);

        if (piece_in_question != null) {
            //Make a piece object
            ChessPiece piece = game_board.getPiece(startPosition);
            //Here's the old valid moves
            Collection<ChessMove> og_valid_moves = piece.pieceMoves(game_board, startPosition);
            //Here are the new valid moves.
            new_valid_moves = check_check(piece, og_valid_moves);
        }

        //Return the valid move list
        return new_valid_moves;
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

//            if (game_board.getPiece(move.getStartPosition()) != null) {
            //Do the move by adding and removing whatever pieces we have to
            ChessPiece moving_piece = game_board.getPiece(move.getStartPosition());

            if (moving_piece.getTeamColor() != CurrentTeam) {
                throw new InvalidMoveException("Invalid move requested");
            }

            //If there is a promotion
            if (move.getPromotionPiece() != null) {
                moving_piece = new ChessPiece(CurrentTeam, move.getPromotionPiece());
            }

            game_board.addPiece(move.getEndPosition(), moving_piece);
            game_board.addPiece(move.getStartPosition(), null);



            //This is to check to update our King Position
            ChessPiece piece_moved = game_board.getPiece(move.getEndPosition());
            if (piece_moved != null) {
                if (piece_moved.getPieceType() == ChessPiece.PieceType.KING)
                    if (CurrentTeam == TeamColor.WHITE) {
                        WhiteKing = move.getEndPosition();
                    } else {
                        BlackKing = move.getEndPosition();
                    }
            }
            if (CurrentTeam == TeamColor.WHITE) {
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

                ChessPosition new_position = new ChessPosition(row, col);
                ChessPiece enemy_piece = game_board.getPiece(new_position);

                if (enemy_piece != null) {
                    if (enemy_piece.getTeamColor() != teamColor) {
                        Collection<ChessMove> enemy_piece_moves = enemy_piece.pieceMoves(game_board, new_position);
                        for (ChessMove enemy_piece_move : enemy_piece_moves) {
                            if (teamColor == TeamColor.WHITE) {
                                if (enemy_piece_move.getEndPosition().equals(WhiteKing)) {
                                    state = true;
                                }
                            }
                            if (teamColor == TeamColor.BLACK) {
                                if (enemy_piece_move.getEndPosition().equals(BlackKing)) {
                                    state = true;
                                }
                            }
                        }
                    }
                }
            }
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
            return helper_isInStalemate(teamColor);
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

        if (teamColor == TeamColor.WHITE) {
            if (!validMoves(WhiteKing).isEmpty()) {
                return false;
            }
        } else {
            if (!validMoves(BlackKing).isEmpty()) {
                return false;
            }
        }

        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition new_position = new ChessPosition(row, col);
                ChessPiece my_piece = game_board.getPiece(new_position);

                if (my_piece != null && my_piece.getTeamColor() == teamColor && !validMoves(new_position).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean helper_isInStalemate(TeamColor teamColor) {

        if (teamColor == TeamColor.WHITE) {
            if (!validMoves(WhiteKing).isEmpty()) {
                return false;
            }
        } else {
            if (!validMoves(BlackKing).isEmpty()) {
                return false;
            }
        }

        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition new_position = new ChessPosition(row, col);
                ChessPiece my_piece = game_board.getPiece(new_position);

                if (my_piece != null && my_piece.getTeamColor() == teamColor && !validMoves(new_position).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {


        game_board = board;


        //Sets the positions for the Kings.
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition check_position = new ChessPosition(row, col);
                ChessPiece piece_in_question = game_board.getPiece(check_position);
                if (piece_in_question != null) {
                    if (piece_in_question.getPieceType() == ChessPiece.PieceType.KING &&
                            piece_in_question.getTeamColor() == TeamColor.WHITE) {

                        WhiteKing = check_position;
                    }
                    if (piece_in_question.getPieceType() == ChessPiece.PieceType.KING &&
                            piece_in_question.getTeamColor() == TeamColor.BLACK) {

                        BlackKing = check_position;
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






