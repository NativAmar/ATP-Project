package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class BestFirstSearch extends ASearchingAlgorithm {

    public BestFirstSearch () {
        super("Best First Search");
    }

    @Override
    public Solution solve(ISearchable searchable) {
        if (searchable == null)
            return null;

        AState startPos = searchable.getStartState();
        AState goalPos = searchable.getGoalState();

        PriorityQueue<AState> openList = new PriorityQueue<>();
        HashSet<AState> closedSet = new HashSet<>();

        startPos.setCost(0);
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
                    openList.add(neighbor); // cost's set in SearchableMaze
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
