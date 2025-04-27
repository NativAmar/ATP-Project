package algorithms.search;

import algorithms.mazeGenerators.Position;

/**
 * Represents a state in a maze used for search algorithms.
 * A MazeState wraps a Position and includes metadata like cost and path tracking.
 * Implements Comparable to allow sorting by cost (for algorithms Best-First Search).
 */
public class MazeState extends AState implements Comparable<AState> {

    /**
     * The position of this state in the maze grid.
     */
    private Position position;

    /**
     * Constructs a new MazeState with a specific row and column, and a reference to its predecessor.
     *
     * @param row      The row index of the position.
     * @param col      The column index of the position.
     * @param cameFrom The previous {@code MazeState} in the path.
     */
    public MazeState(int row, int col, MazeState cameFrom) {
        super(cameFrom, 0.0);
        this.position = new Position(row, col);
    }

    /**
     * Returns the Position associated with this state.
     *
     * @return The position in the maze.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Returns the row index of this state's position.
     *
     * @return The row index.
     */
    public int getRow() {
        return position.getRowIndex();
    }

    /**
     * Returns the column index of this state's position.
     *
     * @return The column index.
     */
    public int getCol() {
        return position.getColumnIndex();
    }

    /**
     * Compares this MazeState to another object for equality.
     * Two MazeState instances are considered equal if their positions are equal.
     *
     * @param obj The object to compare with.
     * @return true if the positions are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MazeState)) return false;
        MazeState other = (MazeState) obj;
        return this.position.equals(other.position);
    }

    /**
     * Returns the hash code of this state, based on its position.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return position.hashCode();
    }

    /**
     * Calculates the Manhattan distance heuristic from this state to the goal state,
     * and sets it as the state's cost.
     *
     * @param goal The goal AState, expected to be a MazeState.
     */
    @Override
    public void calculateHeuristic(AState goal) {
        if (!(goal instanceof MazeState)) return;
        MazeState mGoal = (MazeState) goal;
        int manhattan = Math.abs(this.position.getRowIndex() - mGoal.position.getRowIndex()) + Math.abs(this.position.getColumnIndex()- mGoal.position.getColumnIndex());
        this.setCost(this.getCost() + manhattan);
    }

    /**
     * Compares this state to another based on their cost values.
     * Used for sorting in priority queues.
     *
     * @param other The other state to compare to.
     * @return A negative value if this cost is less, zero if equal, positive if greater.
     */
    @Override
    public int compareTo(AState other) {
        return Double.compare(this.getCost(), other.getCost());
    }

    /**
     * Returns a string representation of the state, including its position and cost.
     *
     * @return A string describing this state.
     */
    @Override
    public String toString() {
        return  position.toString();
    }


}
