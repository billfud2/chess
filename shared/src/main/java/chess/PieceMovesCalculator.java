package chess;

import java.util.Collection;

public class PieceMovesCalculator {
    Collection<ChessMove> possible;
    ChessPosition position;
    ChessPiece myPiece;
    public void checkPos(int rowInc, int colInc, int row, int col, ChessBoard board, ChessPosition myPosition){
        while(row>1 && col>1 && col<8 && row<8){
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
        if(row>(0 + Math.abs(rowInc)) && col>(0 + Math.abs(colInc)) && col<(9 - Math.abs(colInc)) && row<(9 - Math.abs(rowInc))) {
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
