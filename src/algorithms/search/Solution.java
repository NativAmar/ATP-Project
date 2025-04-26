package algorithms.search;

import java.util.ArrayList;

/**
 * Represents the result of a search algorithm.
 * Contains the path from the start state to the goal state,
 * as well as the total cost of that path.
 */
public class Solution {

    /**
     * The ordered list of states from the start to the goal.
     */
    private ArrayList<AState> solution;

    /**
     * The total cost of the path, typically derived from the goal state's cost.
     */
    private double cost;

    /**
     * Constructs a new Solution with the given path.
     * If the path is non-empty, the cost is set to the cost of the last state (assumed to be the goal).
     * Otherwise, the cost is set to 0.
     *
     * @param path The path from start to goal as a list of AState instances.
     */
    public Solution(ArrayList<AState> path) {
        this.solution = path;
        if (!path.isEmpty()) {
            this.cost = path.get(path.size() - 1).getCost(); // total path cost in the last element
        } else {
            this.cost = 0;
        }
    }

    /**
     * Returns the solution path from the start to the goal state.
     *
     * @return An ArrayList of AState objects representing the path.
     */
    public ArrayList<AState> getSolutionPath() {
        return solution;
    }

    /**
     * Returns the total cost of the solution path.
     *
     * @return The cost as a double.
     */
    public double getCost() {
        return cost;
    }
}
