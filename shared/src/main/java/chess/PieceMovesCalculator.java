package chess;

import java.util.Collection;

public class PieceMovesCalculator {
    Collection<ChessMove> possible;
    ChessPosition position;
    ChessPiece myPiece;
    public void checkPos(int rowInc, int colInc, int row, int col, ChessBoard board, ChessPosition myPosition){
        int rowMin = 0;
        int rowMax = 8;
        if (rowInc == -1){
            rowMin = 1;
            rowMax = 9;
        }
        int colMin = 0;
        int colMax = 8;
        if (colInc == -1){
            colMin = 1;
            colMax = 9;
        }
        while(row>rowMin && col>colMin && col<colMax && row<rowMax){
            row += rowInc;
            col += colInc;
            // System.out.println("Bishop Position: {" + row + "," + col + "}");
            this.position = new ChessPosition(row,col);
            ChessPiece spot = board.getPiece(new ChessPosition(row,col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition,this.position,null));
            } else if (spot.getTeamColor() == this.myPiece.getTeamColor()) {
                break;
            } else{
                this.possible.add(new ChessMove(myPosition,this.position,null));
                break;
            }
        }
    }
    public void checkOne(int rowInc, int colInc, int row, int col, ChessBoard board, ChessPosition myPosition){
        if(row>(1+(1-rowInc)/2) && col>(1+(1-colInc)/2) && col<(8+(1-colInc)/2) && row<(8+(1-rowInc)/2)) {
            row += rowInc;
            col += colInc;
            // System.out.println("King Position: {" + row + "," + col + "}");
            position = new ChessPosition(row, col);
            ChessPiece spot = board.getPiece(new ChessPosition(row, col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition, position, null));
            } else if (spot.getTeamColor() != myPiece.getTeamColor()) {
                this.possible.add(new ChessMove(myPosition, position, null));
            }
        }
    }
}
