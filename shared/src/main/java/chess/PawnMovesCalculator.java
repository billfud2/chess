package chess;

import java.util.Collection;
import java.util.HashSet;

public class PawnMovesCalculator extends PieceMovesCalculator{
    public PawnMovesCalculator() {
        this.possible = new HashSet<>();
    }
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        this.myPiece = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        // Check all the moves it can do if it is WHITE
        if(this.myPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            // Check to see if it can move up if nothing is there
            if (row < 8) {
                row++;
                // System.out.println("Pawn Position: {" + row + "," + col + "}");
                this.position = new ChessPosition(row, col);
                ChessPiece spot = board.getPiece(new ChessPosition(row, col));
                if (spot == null) {
                    if (row == 8) {
                        promotion(myPosition, this.position);
                    }else {
                    this.possible.add(new ChessMove(myPosition, this.position, null));
                    }
                }
                if (row == 3){
                    row++;
                    // System.out.println("Pawn Position: {" + row + "," + col + "}");
                    this.position = new ChessPosition(row, col);
                    spot = board.getPiece(new ChessPosition(row, col));
                    if (spot == null) {
                        this.possible.add(new ChessMove(myPosition, this.position, null));
                    }
                }
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            // check if it can move up and to the left to capture
            moveTake(row, col, 1, -1, board, myPosition, ChessGame.TeamColor.BLACK);
            // check if it can move up and to the right to capture
            row = myPosition.getRow();
            col = myPosition.getColumn();
            moveTake(row, col, 1, 1, board, myPosition, ChessGame.TeamColor.BLACK);
        }
        // all the moves if it is a black piece
        else {
            row = myPosition.getRow();
            col = myPosition.getColumn();
            // check if it can move down if nothing is there
            if (row > 1) {
                row--;
                this.position = new ChessPosition(row, col);
                ChessPiece spot = board.getPiece(new ChessPosition(row, col));
                if (spot == null) {
                    if (row == 1) {
                        promotion(myPosition, this.position);
                    }else {
                        this.possible.add(new ChessMove(myPosition, this.position, null));
                    }
                    if (row == 6){
                        row--;
                        this.position = new ChessPosition(row, col);
                        spot = board.getPiece(new ChessPosition(row, col));
                        if (spot == null) {
                            this.possible.add(new ChessMove(myPosition, this.position, null));
                        }
                    }
                }
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            // chick if it can move down and to the right to capture
            moveTake(row, col, -1, 1, board, myPosition, ChessGame.TeamColor.WHITE);
            row = myPosition.getRow();
            col = myPosition.getColumn();
            // check if it can move down and to the left to capture
            moveTake(row, col, -1, -1, board, myPosition, ChessGame.TeamColor.WHITE);
        }
        return this.possible;
    }
}