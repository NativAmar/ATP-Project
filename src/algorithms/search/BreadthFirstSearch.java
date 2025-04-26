package algorithms.search;

import java.util.*;

/**
 * Implementation of the Breadth-First Search (BFS) algorithm for solving searchable problems.
 * This algorithm explores the search space level by level and guarantees finding the shortest path
 * in terms of the number of steps (if all steps have equal cost).
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {

    protected Queue<AState> openList;

    public BreadthFirstSearch() {
        this.nodesEvaluated = 0;
        this.name = "Breadth First Search";
        this.closedSet = new HashSet<>();
        this.openList = new LinkedList<>();
    }

    @Override
    public Solution solve(ISearchable searchable) {
        if (searchable == null)
            return null;

        AState startPos = searchable.getStartState();
        AState goalPos = searchable.getGoalState();

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
                    neighbor.setCameFrom(current);
                    neighbor.setCost(neighbor.getCost() + current.getCost());
                    openList.add(neighbor);
                }
            }
        }
        // In case it's not possible to reach the goalPos
        return null;
    }
}
