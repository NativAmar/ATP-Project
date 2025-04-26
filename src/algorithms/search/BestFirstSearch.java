package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Implementation of the Best-First Search (BestFS) algorithm for solving searchable problems.
 * This algorithm selects the next node to explore based on a heuristic value,
 * prioritizing nodes estimated to be closer to the goal.
 *
 * Extends BreadthFirstSearch but overrides behavior to use a priority queue and heuristics.
 */
public class BestFirstSearch extends BreadthFirstSearch {

    /**
     * Constructs a BestFirstSearch algorithm instance.
     * Initializes the open list as a priority queue and resets tracking structures.
     */
    public BestFirstSearch () {
        this.openList = new PriorityQueue<>();
        this.nodesEvaluated = 0;
        this.name = "Best First Search";
        this.closedSet = new HashSet<>();
    }

    /**
     * Solves the given searchable problem using Best-First Search strategy.
     *
     * Nodes are prioritized based on heuristic estimations towards the goal.
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

        startPos.setCost(0);
        openList.add(startPos);
        this.nodesEvaluated = 0;
        this.closedSet.clear();

        while (!openList.isEmpty()) {
            AState current = openList.poll();
            this.nodesEvaluated++;

            if (current.equals(goalPos))
                return validSolution(current);

            closedSet.add(current);
            ArrayList<AState> neighbors = searchable.getAllPossibleStates(current);

            for (AState neighbor : neighbors) {
                if (!closedSet.contains(neighbor) && !openList.contains(neighbor)) {
                    double moveCost = neighbor.getCost();
                    neighbor.setCameFrom(current);
                    neighbor.setCost(current.getCost() + moveCost);
                    neighbor.calculateHeuristic(goalPos);
                    openList.add(neighbor);
                }
            }
        }

        // In case it's not possible to reach the goalPos
        return null;
    }
}
