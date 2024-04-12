package chess;

import java.util.Collection;
import java.util.HashSet;

public class QueenMovesCalculator extends PieceMovesCalculator{
    public QueenMovesCalculator() {
        this.possible = new HashSet<>();

    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        this.myPiece = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        checkPos(1,-1,row,col,board,myPosition);
        // Getting all possible moves moving to the top right corner
        row = myPosition.getRow();
        col = myPosition.getColumn();
        checkPos(1,1,row,col,board,myPosition);
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // Getting all possible moves moving to bottom right corner
        checkPos(-1,1,row,col,board,myPosition);
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // Getting all possible moves moving to bottom left corner
        checkPos(-1,-1,row,col,board,myPosition);
        // Getting all possible moves moving to the top
        row = myPosition.getRow();
        col = myPosition.getColumn();
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
        return this.possible;
    }
}
