package algorithms.search;

import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm {

    public BreadthFirstSearch() {
        super("Breadth First Search");
    }

    @Override
    public Solution solve(ISearchable searchable) {
        if (searchable == null)
            return null;

        AState startPos = searchable.getStartState();
        AState goalPos = searchable.getGoalState();

        Queue<AState> openList = new LinkedList<>();
        HashSet<AState> closedSet = new HashSet<>();

        openList.add(startPos);
        this.nodesEvaluated = 0;

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
                    neighbor.setCost(current.getCost() + 1);
                    openList.add(neighbor);
                }
            }
        }
        // In case it's not possible to reach the goalPos
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return this.nodesEvaluated;
    }
}
