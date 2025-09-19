package chess;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece<E> implements PieceMoveCalculator<E> {

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

        if (type == ChessPiece.PieceType.KING) {
            KingMovesCalculator<E> calculate = new KingMovesCalculator<E>();
            return calculate.pieceMoves(board, myPosition);
        }

        if (type == ChessPiece.PieceType.QUEEN) {
            QueenMovesCalculator<E> calculate = new QueenMovesCalculator<E>();
            return calculate.pieceMoves(board, myPosition);
        }

        if (type == ChessPiece.PieceType.KNIGHT) {
            KnightMovesCalculator<E> calculate = new KnightMovesCalculator<E>();
            return calculate.pieceMoves(board, myPosition);
        }


        if (type == ChessPiece.PieceType.BISHOP) {
            BishopMovesCalculator<E> calculate = new BishopMovesCalculator<E>();
            return calculate.pieceMoves(board, myPosition);
        }

        if (type == ChessPiece.PieceType.ROOK) {
            RookMovesCalculator<E> calculate = new RookMovesCalculator<E>();
            return calculate.pieceMoves(board, myPosition);
        }

        if (type == ChessPiece.PieceType.PAWN) {
            PawnMovesCalculator<E> calculate = new PawnMovesCalculator<E>();
            return calculate.pieceMoves(board, myPosition);
        }


        ChessPiece piece = board.getPiece(myPosition);
        if (piece.getPieceType() == PieceType.BISHOP) {
            return List.of(new ChessMove(new ChessPosition(5,4), new ChessPosition(1,8), null));
        }

        else{
            return null;
        }
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
