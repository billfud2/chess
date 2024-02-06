package chess;

import java.util.Collection;
import java.util.HashSet;

public class QueenMovesCalculator implements PieceMovesCalculator{
    Collection<ChessMove> possible;
    ChessPosition position;
    ChessPiece myPiece;
    public QueenMovesCalculator() {
        this.possible = new HashSet<>();
    }
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        myPiece = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        // Getting all possible moves moving to the top
        while(row<8){
            row++;
            System.out.println("Queen Position: {" + row + "," + col + "}");
            position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() == myPiece.getTeamColor()) {
                break;
            } else{
                this.possible.add(new ChessMove(myPosition,position,null));
                break;
            }
        }
        // Getting all possible moves moving to the right
        row = myPosition.getRow();
        col = myPosition.getColumn();
        while(col<8){
            col++;
            System.out.println("Queen Position: {" + row + "," + col + "}");
            position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() == myPiece.getTeamColor()) {
                break;
            } else{
                this.possible.add(new ChessMove(myPosition,position,null));
                break;
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // Getting all possible moves moving to bottom
        while(row>1){
            row--;
            System.out.println("Queen Position: {" + row + "," + col + "}");
            position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() == myPiece.getTeamColor()) {
                break;
            } else{
                this.possible.add(new ChessMove(myPosition,position,null));
                break;
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // Getting all possible moves moving to left
        while(col>1){
            col--;
            System.out.println("Queen Position: {" + row + "," + col + "}");
            position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() == myPiece.getTeamColor()) {
                break;
            } else{
                this.possible.add(new ChessMove(myPosition,position,null));
                break;
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // Getting all possible moves moving to the top left corner
        while(row<8 && col>1){
            row++;
            col--;
            System.out.println("Queen Position: {" + row + "," + col + "}");
            position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() == myPiece.getTeamColor()) {
                break;
            } else{
                this.possible.add(new ChessMove(myPosition,position,null));
                break;
            }
        }
        // Getting all possible moves moving to the top right corner
        row = myPosition.getRow();
        col = myPosition.getColumn();
        while(row<8 && col<8){
            row++;
            col++;
            System.out.println("Queen Position: {" + row + "," + col + "}");
            position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() == myPiece.getTeamColor()) {
                break;
            } else{
                this.possible.add(new ChessMove(myPosition,position,null));
                break;
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // Getting all possible moves moving to bottom right corner
        while(row>1 && col<8){
            row--;
            col++;
            System.out.println("Queen Position: {" + row + "," + col + "}");
            position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() == myPiece.getTeamColor()) {
                break;
            } else{
                this.possible.add(new ChessMove(myPosition,position,null));
                break;
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // Getting all possible moves moving to bottom left corner
        while(row>1 && col>1){
            row--;
            col--;
            System.out.println("Queen Position: {" + row + "," + col + "}");
            position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() == myPiece.getTeamColor()) {
                break;
            } else{
                this.possible.add(new ChessMove(myPosition,position,null));
                break;
            }
        }
        return this.possible;
    }
}
