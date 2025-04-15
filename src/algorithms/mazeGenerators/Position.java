package algorithms.mazeGenerators;

/**
 * Represents a position in a 2D grid by row and column indices.
 */
public class Position {
    private int row;
    private int column;

    /**
     * Constructs a Position with specified row and column.
     *
     * @param row    the row index
     * @param column the column index
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
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

    @Override
    public String toString() {
        return "{" + row + "," + column + "}";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Position)) return false;
        Position o = (Position) other;
        return this.row == o.row && this.column == o.column;
    }
}
