package chess;

import java.util.Collection;
import java.util.HashSet;

public class RookMovesCalculator extends PieceMovesCalculator{
    Collection<ChessMove> possible;
    ChessPosition position;
    ChessPiece myPiece;
    public RookMovesCalculator() {
        this.possible = new HashSet<>();
    }
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        this.rookMoves(board, myPosition, this.possible);
        return this.possible;
    }
    public void rookMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> possible) {
        myPiece = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        // Getting all possible moves moving to the top
        while (row < 8) {
            row++;
            // System.out.println("Rook Position: {" + row + "," + col + "}");
            position = new ChessPosition(row, col);
            ChessPiece spot = board.getPiece(new ChessPosition(row, col));
            if (spot == null) {
                possible.add(new ChessMove(myPosition, position, null));
            } else if (spot.getTeamColor() == myPiece.getTeamColor()) {
                break;
            } else {
                possible.add(new ChessMove(myPosition, position, null));
                break;
            }
        }
        // Getting all possible moves moving to the right
        row = myPosition.getRow();
        col = myPosition.getColumn();
        while (col < 8) {
            col++;
            // System.out.println("Rook Position: {" + row + "," + col + "}");
            position = new ChessPosition(row, col);
            ChessPiece spot = board.getPiece(new ChessPosition(row, col));
            if (spot == null) {
                possible.add(new ChessMove(myPosition, position, null));
            } else if (spot.getTeamColor() == myPiece.getTeamColor()) {
                break;
            } else {
                possible.add(new ChessMove(myPosition, position, null));
                break;
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // Getting all possible moves moving to bottom
        while (row > 1) {
            row--;
            // System.out.println("Rook Position: {" + row + "," + col + "}");
            position = new ChessPosition(row, col);
            ChessPiece spot = board.getPiece(new ChessPosition(row, col));
            if (spot == null) {
                possible.add(new ChessMove(myPosition, position, null));
            } else if (spot.getTeamColor() == myPiece.getTeamColor()) {
                break;
            } else {
                possible.add(new ChessMove(myPosition, position, null));
                break;
            }
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
        // Getting all possible moves moving to left
        while (col > 1) {
            col--;
            // System.out.println("Rook Position: {" + row + "," + col + "}");
            position = new ChessPosition(row, col);
            ChessPiece spot = board.getPiece(new ChessPosition(row, col));
            if (spot == null) {
                possible.add(new ChessMove(myPosition, position, null));
            } else if (spot.getTeamColor() == myPiece.getTeamColor()) {
                break;
            } else {
                possible.add(new ChessMove(myPosition, position, null));
                break;
            }
        }
    }
}
