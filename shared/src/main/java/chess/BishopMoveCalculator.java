package chess;

import java.util.Collection;
import java.util.HashSet;

public class BishopMoveCalculator extends PieceMovesCalculator {
    public BishopMoveCalculator() {
        this.possible = new HashSet<>();
    }
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        bishopMoves(board, myPosition, this.possible);
        return this.possible;
    }
    public void bishopMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> possible){
        this.myPiece = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        // Getting all possible moves moving to the top left corner
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
    }


}
