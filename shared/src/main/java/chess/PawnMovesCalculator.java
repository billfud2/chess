package chess;

import java.util.Collection;
import java.util.HashSet;

public class PawnMovesCalculator implements PieceMovesCalculator{
    Collection<ChessMove> possible;
    ChessPosition position;
    ChessPiece myPiece;
    public PawnMovesCalculator() {
        this.possible = new HashSet<>();
    }
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        myPiece = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        // Check all the moves it can do if it is WHITE
        if(myPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            // Check to see if it can move up if nothing is there
            if (row < 8) {
                row++;
                System.out.println("Position: {" + row + "," + col + "}");
                position = new ChessPosition(row, col);
                ChessPiece spot = board.getPiece(new ChessPosition(row, col));
                if (spot == null) {
                    if (row == 8) {
                        Promotion(myPosition, position);
                    }else {
                    this.possible.add(new ChessMove(myPosition, position, null));
                    }
                }
                if (row == 3){
                    row++;
                    System.out.println("Position: {" + row + "," + col + "}");
                    position = new ChessPosition(row, col);
                    spot = board.getPiece(new ChessPosition(row, col));
                    if (spot == null) {
                        this.possible.add(new ChessMove(myPosition, position, null));
                    }
                }
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            // check if it can move up and to the left to capture
            if (row < 8 && col > 1) {
                row++;
                col--;
                System.out.println("Position: {" + row + "," + col + "}");
                position = new ChessPosition(row, col);
                ChessPiece spot = board.getPiece(new ChessPosition(row, col));
                if (spot != null && spot.getTeamColor() == ChessGame.TeamColor.BLACK) {
                    if (row == 8) {
                        Promotion(myPosition, position);
                    }else {
                        this.possible.add(new ChessMove(myPosition, position, null));
                    }
                }
            }
            // check if it can move up and to the right to capture
            row = myPosition.getRow();
            col = myPosition.getColumn();
            if(row<8 && col<8){
                row++;
                col++;
                System.out.println("Position: {" + row + "," + col + "}");
                position = new ChessPosition(row,col);
                ChessPiece spot = board.getPiece(new ChessPosition(row,col));
                if (spot != null && spot.getTeamColor() == ChessGame.TeamColor.BLACK){
                    if (row == 8) {
                        Promotion(myPosition, position);
                    }else {
                        this.possible.add(new ChessMove(myPosition, position, null));
                    }
                }
            }

        }
        // all the moves if it is a black piece
        else {
            row = myPosition.getRow();
            col = myPosition.getColumn();
            // check if it can move down if nothing is there
            if (row > 1) {
                row--;
                System.out.println("Position: {" + row + "," + col + "}");
                position = new ChessPosition(row, col);
                ChessPiece spot = board.getPiece(new ChessPosition(row, col));
                if (spot == null) {
                    if (row == 1) {
                        Promotion(myPosition, position);
                    }else {
                        this.possible.add(new ChessMove(myPosition, position, null));
                    }
                    if (row == 6){
                        row--;
                        System.out.println("Position: {" + row + "," + col + "}");
                        position = new ChessPosition(row, col);
                        spot = board.getPiece(new ChessPosition(row, col));
                        if (spot == null) {
                            this.possible.add(new ChessMove(myPosition, position, null));
                        }
                    }
                }
            }

            row = myPosition.getRow();
            col = myPosition.getColumn();
            // chick if it can move down and to the right to capture
            if (row > 1 && col < 8) {
                row--;
                col++;
                System.out.println("Position: {" + row + "," + col + "}");
                position = new ChessPosition(row, col);
                ChessPiece spot = board.getPiece(new ChessPosition(row, col));
                if (spot != null && spot.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    if (row == 1) {
                        Promotion(myPosition, position);
                    }else {
                        this.possible.add(new ChessMove(myPosition, position, null));
                    }
                }
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            // check if it can move down and to the left to capture
            if (row > 1 && col > 1) {
                row--;
                col--;
                System.out.println("Position: {" + row + "," + col + "}");
                position = new ChessPosition(row, col);
                ChessPiece spot = board.getPiece(new ChessPosition(row, col));
                if (spot != null && spot.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    if (row == 1) {
                        Promotion(myPosition, position);
                    }else {
                        this.possible.add(new ChessMove(myPosition, position, null));
                    }
                }
            }
        }
        return this.possible;
    }
    public void Promotion(ChessPosition start, ChessPosition end){
        this.possible.add(new ChessMove(start, end, ChessPiece.PieceType.ROOK));
        this.possible.add(new ChessMove(start, end, ChessPiece.PieceType.BISHOP));
        this.possible.add(new ChessMove(start, end, ChessPiece.PieceType.KNIGHT));
        this.possible.add(new ChessMove(start, end, ChessPiece.PieceType.QUEEN));
    }
}
