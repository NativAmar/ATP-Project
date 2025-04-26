package algorithms.search;

import java.util.*;

/**
 * Abstract base class for all searching algorithms.
 * Implements common functionalities like tracking evaluated nodes
 * and constructing valid solution paths.
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    protected int nodesEvaluated;
    protected String name;
    protected HashSet<AState> closedSet;

    /**
     * Returns the number of nodes evaluated during the last search.
     *
     * @return Number of evaluated nodes.
     */
    public int getNumberOfNodesEvaluated(){
        return nodesEvaluated;
    };

    /**
     * Constructs a valid solution path from the goal state to the start state.
     * Traverses the path backwards using {getCameFrom()} and reverses it
     * to present the solution from start to goal.
     *
     * @param goal The goal state from which to backtrack the solution.
     * @return A Solution object representing the path from start to goal.
     */
    protected Solution validSolution(AState goal) {
        ArrayList<AState> sol = new ArrayList<>();
        AState current = goal;
        while (current != null) {
            sol.add(current);
            current = current.getCameFrom();
        }

        // To return the path in the correct forward direction
        Collections.reverse(sol);

        return new Solution(sol);
    }

    /**
     * Solves the given searchable problem and returns the solution path.
     *
     * @param searchable The searchable problem to solve.
     * @return A Solution representing the path to the goal, if found.
     */
    public abstract Solution solve(ISearchable searchable);

    /**
     * Returns the name of the search algorithm.
     *
     * @return The algorithm's name.
     */
    public String getName(){
        return name;
    };

}
