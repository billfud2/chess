package chess;
import java.util.Collection;
import java.util.HashSet;

public class KingMovesCalculator extends PieceMovesCalculator{
    public KingMovesCalculator() {
        this.possible = new HashSet<>();
    }
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        this.myPiece = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        // Check to see if it can move up
        checkOne(1, 0, row, col, board, myPosition);
        // Check if it can move to the right
        row = myPosition.getRow();
        col = myPosition.getColumn();
        checkOne(0, 1, row, col, board, myPosition);
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // check if it can move down
        checkOne(-1, 0, row, col, board, myPosition);
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // check if it can move to left
        checkOne(0, -1, row, col, board, myPosition);
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // check if it can move up and to the left
        checkOne(1, -1, row, col, board, myPosition);
        // check if it can move up and to the right
        row = myPosition.getRow();
        col = myPosition.getColumn();
        checkOne(1, 1, row, col, board, myPosition);
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // chick if it can move down and to the right
        checkOne(-1, 1, row, col, board, myPosition);
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // check if it can move down and to the left
        checkOne(-1, -1, row, col, board, myPosition);
        return this.possible;
    }
}
