package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState implements Comparable<AState> {

    private Position position;

    public MazeState(int row, int col, MazeState cameFrom) {
        super(cameFrom, 0.0);
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

    @Override
    public int hashCode() {
        return position.hashCode();
    }

    @Override
    public void calculateHeuristic(AState goal) {
        if (!(goal instanceof MazeState)) return;
        MazeState mGoal = (MazeState) goal;
        int manhattan = Math.abs(this.position.getRowIndex() - mGoal.position.getRowIndex()) + Math.abs(this.position.getColumnIndex()- mGoal.position.getColumnIndex());
        this.setCost(manhattan);
    }

    @Override
    public int compareTo(AState other) {
        return Double.compare(this.getCost(), other.getCost());
    }

    @Override
    public String toString() {
        return "Position: " + position.toString() + ", Cost: " + this.getCost();
    }


}
