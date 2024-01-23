package chess;

import java.util.Collection;
import java.util.HashSet;

public class BishopMoveCalculator implements PieceMovesCalculator {
    Collection<ChessMove> possible;
    ChessPosition position;
    ChessPiece myPiece;

    public BishopMoveCalculator() {
        this.possible = new HashSet<ChessMove>();
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        myPiece = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        // Getting all possible moves moving to the top left corner
        while(row<8 && col>1){
            row++;
            col--;
            position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() == myPiece.getTeamColor()) {
                break;
            } else{
                this.possible.add(new ChessMove(myPosition,position,null));
            }
        }
        // Getting all possible moves moving to the top right corner
        while(row<8 && col<8){
            row++;
            col++;
            position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() == myPiece.getTeamColor()) {
                break;
            } else{
                this.possible.add(new ChessMove(myPosition,position,null));
            }
        }
        // Getting all possible moves moving to bottom right corner
        while(row>1 && col<8){
            row--;
            col++;
            position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() == myPiece.getTeamColor()) {
                break;
            } else{
                this.possible.add(new ChessMove(myPosition,position,null));
            }
        }
        // Getting all possible moves moving to bottom left corner
        while(row>1 && col<1){
            row--;
            col--;
            position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() == myPiece.getTeamColor()) {
                break;
            } else{
                this.possible.add(new ChessMove(myPosition,position,null));
            }
        }
        return null;
    }
}
