package chess;

import java.util.Collection;
import java.util.HashSet;

public class KnightMovesCalculator extends PieceMovesCalculator {
    public KnightMovesCalculator() {
        this.possible = new HashSet<>();
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        this.myPiece = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        // Check to see if it can move up 2 and 1 to the right
        checkJump(2,1,0,0,7,8,row,col,board,myPosition);
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // Check to see if it can move up 1 and 2 to the right
        checkJump(1,2,0,0,8,7,row,col,board,myPosition);
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // check if it can move up 1 and 2 to the left
        checkJump(1,-2,0,2,8,9,row,col,board,myPosition);
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // check if it can move up 2 and 1 to the left.
        checkJump(2,-1,0,1,7,9,row,col,board,myPosition);
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // check if it can move down 1 and 2 to the left.
        checkJump(-2,-2,1,2,9,9,row,col,board,myPosition);
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // check if it can move down 2 and 1 to the left.
        checkJump(-2,-1,2,1,9,9,row,col,board,myPosition);
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // check if it can move down 2 and 1 to the right.
        checkJump(-2,1,2,0,9,8,row,col,board,myPosition);
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // check if it can move down 1 and 2 to the right.
        checkJump(-1,2,1,0,9,7,row,col,board,myPosition);
        return this.possible;
    }
}