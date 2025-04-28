package algorithms.mazeGenerators;

import java.util.Objects;

/**
 * Represents a position in a 2D maze with row and column indices.
 */
public class Position {
    private int row;
    private int column;

    /**
     * Constructs a new Position with the specified row and column indices.
     *
     * @param row    the row index
     * @param column the column index
     */
    public Position(int row,int column){
        this.row=row;
        this.column=column;
    }

    /**
     * Returns the row index of this position.
     *
     * @return the row index
     */
    public int getRowIndex(){
        return this.row;
    }

    /**
     * Returns the column index of this position.
     *
     * @return the column index
     */
    public int getColumnIndex(){
        return this.column;
    }

    /**
     * Returns a string representation of the position in the format "{row,column}".
     *
     * @return a string representing this position
     */
    @Override
    public String toString() {
        return "{" + row + "," + column + "}";
    }

    /**
     * Checks if this position is equal to another object.
     * Two positions are equal if they have the same row and column indices.
     *
     * @param other the object to compare to
     * @return true if the positions are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Position)) return false;
        Position o = (Position) other;
        return this.row == o.row && this.column == o.column;
    }

    /**
     * Returns a hash code value for this position based on its row and column.
     *
     * @return a hash code value
     */
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
