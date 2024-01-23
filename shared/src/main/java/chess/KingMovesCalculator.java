package chess;
import java.util.Collection;
import java.util.HashSet;

public class KingMovesCalculator implements PieceMovesCalculator{
    Collection<ChessMove> possible;
    ChessPosition position;
    ChessPiece myPiece;
    public KingMovesCalculator() {
        this.possible = new HashSet<>();
    }
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        myPiece = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        // Check to see if it can move up
        if(row<8) {
            row++;
            System.out.println("Position: {" + row + "," + col + "}");
            position = new ChessPosition(row, col);
            ChessPiece spot = board.getPiece(new ChessPosition(row, col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() != myPiece.getTeamColor()){
                this.possible.add(new ChessMove(myPosition,position,null));
            }
        }
        // Check if it can move to the right
        row = myPosition.getRow();
        col = myPosition.getColumn();
        if(col<8){
            col++;
            System.out.println("Position: {" + row + "," + col + "}");
            position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() != myPiece.getTeamColor()){
                this.possible.add(new ChessMove(myPosition,position,null));
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // check if it can move down
        if(row>1){
            row--;
            System.out.println("Position: {" + row + "," + col + "}");
            position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() != myPiece.getTeamColor()){
                this.possible.add(new ChessMove(myPosition,position,null));
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // check if it can move to left
        if(col>1){
            col--;
            System.out.println("Position: {" + row + "," + col + "}");
            position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() != myPiece.getTeamColor()){
                this.possible.add(new ChessMove(myPosition,position,null));
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // check if it can move up and to the left
        if(row<8 && col>1){
            row++;
            col--;
            System.out.println("Position: {" + row + "," + col + "}");
            position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() != myPiece.getTeamColor()){
                this.possible.add(new ChessMove(myPosition,position,null));
            }
        }
        // check if it can move up and to the right
        row = myPosition.getRow();
        col = myPosition.getColumn();
        if(row<8 && col<8){
            row++;
            col++;
            System.out.println("Position: {" + row + "," + col + "}");
            position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() != myPiece.getTeamColor()){
                this.possible.add(new ChessMove(myPosition,position,null));
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // chick if it can move down and to the right
        if(row>1 && col<8){
            row--;
            col++;
            System.out.println("Position: {" + row + "," + col + "}");
            position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() != myPiece.getTeamColor()){
                this.possible.add(new ChessMove(myPosition,position,null));
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // check if it can move down and to the left
        if(row>1 && col>1){
            row--;
            col--;
            System.out.println("Position: {" + row + "," + col + "}");
            position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() != myPiece.getTeamColor()){
                this.possible.add(new ChessMove(myPosition,position,null));
            }
        }
        return this.possible;
    }
}
