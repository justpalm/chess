package chess;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece implements PieceMoveCalculator{

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        PieceMoveCalculator calculate;

        if (type == ChessPiece.PieceType.KING) {
            calculate = new KingMovesCalculator();
        }

        if (type == ChessPiece.PieceType.KNIGHT) {
            calculate = new KnightMovesCalculator();
        }

        if (type == ChessPiece.PieceType.QUEEN) {
            calculate = new QueenMocesCalculator();
        }

        if (type == ChessPiece.PieceType.BISHOP) {
            calculate = new BishopMovesCalculator();
        }

        if (type == ChessPiece.PieceType.ROOK) {
            calculate = new RookMovesCalculator();
        }

        if (type == ChessPiece.PieceType.PAWN) {
            calculate = new PawnMovesCalculator();
        }










        //        ChessPiece piece = board.getPiece(myPosition);
//        if (piece.getPieceType() == PieceType.BISHOP) {
//            return List.of(new ChessMove(new ChessPosition(5,4), new ChessPosition(1,8), null));
//        }
//        return List.of();
//    }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
}
