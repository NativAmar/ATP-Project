package algorithms.maze3D;

import algorithms.search.AState;
import algorithms.maze3D.Position3D;

/**
 * Maze3DState represents a specific state in a 3D maze.
 * It holds a position in 3D space and inherits common state properties from AState.
 */
public class Maze3DState extends AState implements Comparable<AState> {
    private Position3D position;

    /**
     * Constructs a new Maze3DState with the given position and predecessor state.
     *
     * @param depth    the depth index in the 3D maze
     * @param row      the row index in the 3D maze
     * @param col      the column index in the 3D maze
     * @param cameFrom the previous state leading to this one
     */
    public Maze3DState(int depth, int row, int col, AState cameFrom) {
        super(cameFrom, 0.0);
        this.position = new Position3D(depth, row, col);
    }

    /**
     * Gets the 3D position of this state.
     *
     * @return the position in the 3D maze
     */
    public Position3D getPosition() {
        return position;
    }

    /**
     * Gets the depth index of this state.
     *
     * @return the depth index
     */
    public int getD() {
        return position.getDepthIndex();
    }

    /**
     * Gets the row index of this state.
     *
     * @return the row index
     */
    public int getR() {
        return position.getRowIndex();
    }

    /**
     * Gets the column index of this state.
     *
     * @return the column index
     */
    public int getC() {
        return position.getColumnIndex();
    }

    /**
     * Checks if two Maze3DState objects are equal based on their position.
     *
     * @param obj the object to compare with
     * @return true if the positions are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Maze3DState)) return false;
        Maze3DState other = (Maze3DState) obj;
        return this.position.equals(other.position);
    }

    /**
     * Computes the hash code based on the position.
     *
     * @return the hash code of this state
     */
    @Override
    public int hashCode() {
        return position.hashCode();
    }

    /**
     * Calculates the Manhattan distance heuristic from this state to the goal state.
     *
     * @param goal the goal state to compute heuristic against
     */
    @Override
    public void calculateHeuristic(AState goal) {
        if (!(goal instanceof Maze3DState)) return;
        Maze3DState mGoal = (Maze3DState) goal;
        int manhattan = Math.abs(this.getD() - mGoal.getD()) +
                Math.abs(this.getR() - mGoal.getR()) +
                Math.abs(this.getC() - mGoal.getC());
        this.setCost(manhattan);
    }

    /**
     * Compares this state with another state based on cost.
     *
     * @param other the other state to compare to
     * @return a negative number, zero, or a positive number as this state is less than,
     * equal to, or greater than the other state
     */
    @Override
    public int compareTo(AState other) {
        return Double.compare(this.getCost(), other.getCost());
    }

    /**
     * Returns a string representation of this state, including its position and cost.
     *
     * @return a string representing this state
     */
    @Override
    public String toString() {
        return position.toString();
    }
}
