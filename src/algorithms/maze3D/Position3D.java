package algorithms.maze3D;



import java.util.Objects;

/**
 * Represents a 3D coordinate in a maze with depth, row, and column indices.
 */
public class Position3D {
    private int depth;
    private int row;
    private int column;

    /**
     * Constructs a new Position3D with the specified depth, row, and column indices.
     *
     * @param depth  the depth index
     * @param row    the row index
     * @param column the column index
     */
    public Position3D(int depth,int row,int column){
        this.depth = depth;
        this.row = row;
        this.column = column;
    }

    /**
     * Returns the depth index of this position.
     *
     * @return the depth index
     */
    public int getDepthIndex() {
        return this.depth;
    }

    /**
     * Returns the row index of this position.
     *
     * @return the row index
     */
    public int getRowIndex() {
        return this.row;
    }

    /**
     * Returns the column index of this position.
     *
     * @return the column index
     */
    public int getColumnIndex() {
        return this.column;
    }

    /**
     * Returns a string representation of the 3D position in the format "{depth,row,column}".
     *
     * @return a string representing the 3D position
     */
    @Override
    public String toString() {
        return "{" + depth + "," + row + "," + column + "}";
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * Two positions are considered equal if they have the same depth, row, and column indices.
     *
     * @param other the reference object with which to compare
     * @return true if this object is the same as the other argument; false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Position3D)) return false;
        Position3D o = (Position3D) other;
        return this.depth == o.depth && this.row == o.row && this.column == o.column;
    }

    /**
     * Returns a hash code value for the position based on its depth, row, and column.
     *
     * @return a hash code value for this position
     */
    public int hashCode() {
        return Objects.hash(row, column);
    }
}

