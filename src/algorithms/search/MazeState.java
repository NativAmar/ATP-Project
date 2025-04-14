package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState {

    private Position position;

    public MazeState(int row, int col, MazeState cameFrom) {
        super(cameFrom, 0);
        this.position = new Position(row, col);
    }

    public Position getPosition() {
        return position;
    }

    public int getRow() {
        return position.getRowIndex();
    }

    public int getCol() {
        return position.getColumnIndex();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MazeState)) return false;
        MazeState other = (MazeState) obj;
        return this.position.equals(other.position);
    }



}
