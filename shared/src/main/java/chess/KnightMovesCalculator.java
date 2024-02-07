package chess;

import java.util.Collection;
import java.util.HashSet;

public class KnightMovesCalculator implements PieceMovesCalculator {
    Collection<ChessMove> possible;
    ChessPosition position;
    ChessPiece myPiece;
    public KnightMovesCalculator() {
        this.possible = new HashSet<>();
    }
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        myPiece = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        // Check to see if it can move up 2 and 1 to the right
        if(row<7 && col<8) {
            row +=2;
            col++;
            // System.out.println("Knight Position: {" + row + "," + col + "}");
            position = new ChessPosition(row, col);
            ChessPiece spot = board.getPiece(new ChessPosition(row, col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition,position,null));
            } else if (spot.getTeamColor() != myPiece.getTeamColor()){
                this.possible.add(new ChessMove(myPosition,position,null));
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // Check to see if it can move up 1 and 2 to the right
        if(row<8 && col<7) {
            row++;
            col+=2;
            // System.out.println("Knight Position: {" + row + "," + col + "}");
            position = new ChessPosition(row, col);
            ChessPiece spot = board.getPiece(new ChessPosition(row, col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition, position, null));
            } else if (spot.getTeamColor() != myPiece.getTeamColor()) {
                this.possible.add(new ChessMove(myPosition, position, null));
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // check if it can move up 1 and 2 to the left
        if(row<8 && col>2) {
            row++;
            col-=2;
            // System.out.println("Knight Position: {" + row + "," + col + "}");
            position = new ChessPosition(row, col);
            ChessPiece spot = board.getPiece(new ChessPosition(row, col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition, position, null));
            } else if (spot.getTeamColor() != myPiece.getTeamColor()) {
                this.possible.add(new ChessMove(myPosition, position, null));
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // check if it can move up 2 and 1 to the left.
        if(row<7 && col>1) {
            row+=2;
            col--;
            // System.out.println("Knight Position: {" + row + "," + col + "}");
            position = new ChessPosition(row, col);
            ChessPiece spot = board.getPiece(new ChessPosition(row, col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition, position, null));
            } else if (spot.getTeamColor() != myPiece.getTeamColor()) {
                this.possible.add(new ChessMove(myPosition, position, null));
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // check if it can move down 1 and 2 to the left.
        if(row>1 && col>2) {
            row--;
            col-=2;
            // System.out.println("Knight Position: {" + row + "," + col + "}");
            position = new ChessPosition(row, col);
            ChessPiece spot = board.getPiece(new ChessPosition(row, col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition, position, null));
            } else if (spot.getTeamColor() != myPiece.getTeamColor()) {
                this.possible.add(new ChessMove(myPosition, position, null));
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // check if it can move down 2 and 1 to the left.
        if(row>2 && col>1) {
            row-=2;
            col--;
            // System.out.println("Knight Position: {" + row + "," + col + "}");
            position = new ChessPosition(row, col);
            ChessPiece spot = board.getPiece(new ChessPosition(row, col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition, position, null));
            } else if (spot.getTeamColor() != myPiece.getTeamColor()) {
                this.possible.add(new ChessMove(myPosition, position, null));
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // check if it can move down 2 and 1 to the right.
        if(row>2 && col<8) {
            row-=2;
            col++;
            // System.out.println("Knight Position: {" + row + "," + col + "}");
            position = new ChessPosition(row, col);
            ChessPiece spot = board.getPiece(new ChessPosition(row, col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition, position, null));
            } else if (spot.getTeamColor() != myPiece.getTeamColor()) {
                this.possible.add(new ChessMove(myPosition, position, null));
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // check if it can move down 1 and 2 to the right.
        if(row>1 && col<7) {
            row--;
            col+=2;
            // System.out.println("Knight Position: {" + row + "," + col + "}");
            position = new ChessPosition(row, col);
            ChessPiece spot = board.getPiece(new ChessPosition(row, col));
            if (spot == null) {
                this.possible.add(new ChessMove(myPosition, position, null));
            } else if (spot.getTeamColor() != myPiece.getTeamColor()) {
                this.possible.add(new ChessMove(myPosition, position, null));
            }
        }

        return this.possible;
    }
}
