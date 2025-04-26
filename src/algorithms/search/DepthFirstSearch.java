package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

/**
 * Implementation of the Depth-First Search (DFS) algorithm for solving searchable problems.
 * This algorithm explores as deeply as possible along each branch before backtracking,
 * using a LIFO (Last-In-First-Out) stack structure.
 */
public class DepthFirstSearch extends ASearchingAlgorithm {

    /**
     * The stack used to manage states for exploration.
     */
    private Stack<AState> openList;

    /**
     * Constructs a DepthFirstSearch algorithm instance.
     * Initializes the open list as a stack and resets tracking structures.
     */
    public DepthFirstSearch() {
        this.nodesEvaluated = 0;
        this.name = "Depth First Search";
        this.openList = new Stack<>();
        this.closedSet = new HashSet<>();
    }

    /**
     * Solves the given searchable problem using Depth-First Search strategy.
     * Explores deep paths first before considering alternative paths.
     *
     * @param searchable The problem to be solved.
     * @return A Solution representing the path from start to goal, or null if no solution exists.
     */
    @Override
    public Solution solve(ISearchable searchable) {
        if (searchable == null)
            return null;

        AState startPos = searchable.getStartState();
        AState goalPos = searchable.getGoalState();

        openList.push(startPos);
        this.nodesEvaluated = 0;
        this.closedSet.clear();

        while (!openList.isEmpty()) {
            AState current = openList.pop();
            this.nodesEvaluated++;

            if (current.equals(goalPos))
                return validSolution(current);

            if (!closedSet.contains(current)){
                closedSet.add(current);
                ArrayList<AState> neighbors = searchable.getAllPossibleStates(current);

                for (AState neighbor : neighbors) {
                    if (!closedSet.contains(neighbor) && !openList.contains(neighbor)) {
                        neighbor.setCameFrom(current);
                        neighbor.setCost(current.getCost() + neighbor.getCost());
                        openList.push(neighbor);
                    }
                }
            }
        }
        // In case it's not possible to reach the goalPos
        return null;
    }
}
