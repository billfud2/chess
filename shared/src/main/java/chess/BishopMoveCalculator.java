package chess;

import java.util.Collection;
import java.util.HashSet;

public class BishopMoveCalculator extends PieceMovesCalculator {
    Collection<ChessMove> possible;
    ChessPosition position;
    ChessPiece myPiece;

    public BishopMoveCalculator() {
        this.possible = new HashSet<>();
    }
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        bishopMoves(board, myPosition, this.possible);
        return this.possible;
    }
    public void bishopMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> possible){
        myPiece = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        // Getting all possible moves moving to the top left corner
        while(row<8 && col>1){
            row++;
            col--;
            // System.out.println("Bishop Position: {" + row + "," + col + "}");
            position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() == myPiece.getTeamColor()) {
                break;
            } else{
                possible.add(new ChessMove(myPosition,position,null));
                break;
            }
        }
        // Getting all possible moves moving to the top right corner
        row = myPosition.getRow();
        col = myPosition.getColumn();
        while(row<8 && col<8){
            row++;
            col++;
            // System.out.println("Bishop Position: {" + row + "," + col + "}");
            position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() == myPiece.getTeamColor()) {
                break;
            } else{
                possible.add(new ChessMove(myPosition,position,null));
                break;
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // Getting all possible moves moving to bottom right corner
        while(row>1 && col<8){
            row--;
            col++;
            // System.out.println("Bishop Position: {" + row + "," + col + "}");
            position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() == myPiece.getTeamColor()) {
                break;
            } else{
                possible.add(new ChessMove(myPosition,position,null));
                break;
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // Getting all possible moves moving to bottom left corner
        while(row>1 && col>1){
            row--;
            col--;
            // System.out.println("Bishop Position: {" + row + "," + col + "}");
            position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() == myPiece.getTeamColor()) {
                break;
            } else{
                possible.add(new ChessMove(myPosition,position,null));
                break;
            }
        }
    }
}
