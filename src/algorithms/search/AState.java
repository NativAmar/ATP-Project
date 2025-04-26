package algorithms.search;

/**
 * Represents an abstract state in a search space.
 * Serves as the base for specific state implementations in different domains (e.g., MazeState).
 * Each state keeps track of its predecessor and the cost to reach it.
 */
public abstract class AState {

    /**
     * The state that led to this one (used for backtracking the solution path).
     */
    private AState cameFrom;

    /**
     * The cost associated with reaching this state from the start state.
     */
    private double cost;

    /**
     * Constructs a new state with a reference to its predecessor and a cost value.
     *
     * @param cameFrom The state from which this state was reached.
     * @param cost     The cost to reach this state.
     */
    public AState(AState cameFrom, double cost) {
        this.cameFrom = cameFrom;
        this.cost = cost;
    }

    /**
     * Returns the previous state in the solution path.
     *
     * @return The parent state.
     */
    public AState getCameFrom() {
        return cameFrom;
    }

    /**
     * Returns the cost associated with this state.
     *
     * @return The cost value.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Sets the predecessor state.
     *
     * @param cameFrom The state that led to this one.
     */
    public void setCameFrom(AState cameFrom) {
        this.cameFrom = cameFrom;
    }

    /**
     * Sets the cost of reaching this state.
     *
     * @param cost The new cost value.
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Calculates the heuristic (estimated cost) from this state to the goal state.
     * The specific heuristic logic should be implemented by subclasses.
     *
     * @param goal The goal state to estimate distance or cost to.
     */
    public abstract void calculateHeuristic(AState goal);

    /**
     * Compares this state with another state, typically based on cost or heuristic.
     * The comparison logic should be implemented by subclasses.
     *
     * @param other The other state to compare with.
     * @return A negative integer, zero, or a positive integer as this state is less than,
     * equal to, or greater than the specified state.
     */
    public abstract int compareTo(AState other);
}
