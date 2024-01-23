package chess;

import java.awt.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Queue;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessPiece.PieceType piece;
    private ChessGame.TeamColor team;
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.piece = type;
        this.team = pieceColor;
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
        return this.team;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.piece;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        if(this.piece == PieceType.BISHOP){
            BishopMoveCalculator moves = new BishopMoveCalculator();
            return moves.pieceMoves(board, myPosition);

        }
        if(this.piece == PieceType.ROOK){
            RookMovesCalculator moves = new RookMovesCalculator();
            return moves.pieceMoves(board, myPosition);

        }
        if(this.piece == PieceType.KNIGHT){
            KnightMovesCalculator moves = new KnightMovesCalculator();
            return moves.pieceMoves(board, myPosition);

        }
        if(this.piece == PieceType.PAWN){
            PawnMovesCalculator moves = new PawnMovesCalculator();
            return moves.pieceMoves(board, myPosition);

        }
        if(this.piece == PieceType.KING){
            KingMovesCalculator moves = new KingMovesCalculator();
            return moves.pieceMoves(board, myPosition);

        }
        if(this.piece == PieceType.QUEEN){
            QueenMovesCalculator moves = new QueenMovesCalculator();
            return moves.pieceMoves(board, myPosition);

        }
        return null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return piece == that.piece && team == that.team;
    }

    @Override
    public int hashCode() {
        return Objects.hash(piece, team);
    }
}
