package chess;

import java.util.Collection;
import java.util.HashSet;

public class RookMovesCalculator extends PieceMovesCalculator{

    public RookMovesCalculator() {
        this.possible = new HashSet<>();
    }
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        this.rookMoves(board, myPosition, this.possible);
        return this.possible;
    }
    public void rookMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> possible) {
        this.myPiece = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        // Getting all possible moves moving to the top
        checkPos(1,0,row,col,board,myPosition);
        // Getting all possible moves moving to the right
        row = myPosition.getRow();
        col = myPosition.getColumn();
        checkPos(0,1,row,col,board,myPosition);
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // Getting all possible moves moving to bottom
        checkPos(-1,0,row,col,board,myPosition);
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // Getting all possible moves moving to left
        checkPos(0,-1,row,col,board,myPosition);
    }
}
