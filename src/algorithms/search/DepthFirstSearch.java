package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm {

    public DepthFirstSearch() {
        super("Depth First Search");
    }

    @Override
    public Solution solve(ISearchable searchable) {
        if (searchable == null)
            return null;

        AState startPos = searchable.getStartState();
        AState goalPos = searchable.getGoalState();

        Stack<AState> openList = new Stack<>();
        HashSet<AState> closedSet = new HashSet<>();

        openList.push(startPos);
        this.nodesEvaluated = 0;

        while (!openList.isEmpty()) {
            AState current = openList.pop();
            this.nodesEvaluated++;

            if (current.equals(goalPos))
                return validSolution(current);

            if (!closedSet.contains(current)){
                closedSet.add(current);
                ArrayList<AState> neighbors = searchable.getAllPossibleStates(current);

                for (AState neighbor : neighbors) {
                    if (!closedSet.contains(neighbor)) {
                        neighbor.setCameFrom(current);
                        neighbor.setCost(current.getCost() + 1);
                        openList.push(neighbor);
                    }
                }
            }

        }
        // In case it's not possible to reach the goalPos
        return null;
    }

    @Override
    public int getNumOfNodesEvaluated() {
        return this.nodesEvaluated;
    }
}
