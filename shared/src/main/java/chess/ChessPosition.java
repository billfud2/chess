package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    private int row;
    private int col;

    public ChessPosition(int row, int col) {

        this.row = row-1;
        this.col = col-1;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return this.row+1;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return this.col+1;
    }
    public String toString(){
        String s = "";
        if(col == 0){
            s = "a";
        } else if (col == 1) {
            s = "b";
        }else if (col == 2) {
            s = "c";
        }else if (col == 3) {
            s = "d";
        }else if (col == 4) {
            s = "e";
        }else if (col == 5) {
            s = "f";
        }else if (col == 6) {
            s = "g";
        }else if (col == 7) {
            s = "h";
        }
        s = s + Integer.toString(getRow());
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPosition that = (ChessPosition) o;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
