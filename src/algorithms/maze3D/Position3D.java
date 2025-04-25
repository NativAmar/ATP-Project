package algorithms.maze3D;



import java.util.Objects;

/**
 * Represents a 3D coordinate in a maze with depth, row, and column indices.
 */
public class Position3D {
    private int depth;
    private int row;
    private int column;

    public Position3D(int depth,int row,int column){
        this.depth = depth;
        this.row = row;
        this.column = column;
    }

    public int getDepthIndex() {
        return this.depth;
    }

    public int getRowIndex() {
        return this.row;
    }

    public int getColumnIndex() {
        return this.column;
    }

    @Override
    public String toString() {
        return "{" + depth + "," + row + "," + column + "}";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Position3D)) return false;
        Position3D o = (Position3D) other;
        return this.depth == o.depth && this.row == o.row && this.column == o.column;
    }

    public int hashCode() {
        return Objects.hash(row, column);
    }
}

