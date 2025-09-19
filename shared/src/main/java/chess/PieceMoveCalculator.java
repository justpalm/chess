package chess;


    import java.util.Collection;
    import java.util.List;
    import java.util.Objects;

    public interface PieceMoveCalculator<E> {
        public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);

    }



//public class PieceMoveCalculator {
//
//    private final ChessPiece.PieceType type;
//
//    public PieceMoveCalculator(ChessPiece.PieceType type){
//        this.type = type;
//        calculate =
//
//        if (type == ChessPiece.PieceType.KING) {
//            calcualte = new KingMovesCalculator();
//        }
//
//        if (type == ChessPiece.PieceType.KNIGHT) {
//            calculate = new KnightMovesCalculator();
//        }
//
//        if (type == ChessPiece.PieceType.QUEEN) {
//            calculate = new QueenMocesCalculator();
//        }
//
//        if (type == ChessPiece.PieceType.BISHOP) {
//            calculate = new BishopMovesCalculator();
//        }
//
//        if (type == ChessPiece.PieceType.ROOK) {
//            calculate = new RookMovesCalculator();
//        }
//
//        if (type == ChessPiece.PieceType.PAWN) {
//            calculate = new PawnMovesCalculator();
//        }
//
//
//    }
//
//
//    public possible_moves() {
//        return calculate.collection;
//    }
//}
