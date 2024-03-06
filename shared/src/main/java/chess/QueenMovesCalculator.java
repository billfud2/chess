package chess;

import java.util.Collection;
import java.util.HashSet;

public class QueenMovesCalculator extends PieceMovesCalculator{
    Collection<ChessMove> possible;
    ChessPosition position;
    ChessPiece myPiece;
    RookMovesCalculator rook;
    BishopMoveCalculator bishop;
    public QueenMovesCalculator() {
        this.possible = new HashSet<>();
        rook = new RookMovesCalculator();
        bishop = new BishopMoveCalculator();
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        rook.rookMoves(board, myPosition, this.possible);
        bishop.bishopMoves(board, myPosition, this.possible);
        return this.possible;
    }
}
