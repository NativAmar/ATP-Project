package algorithms.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Queue;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    protected int nodesEvaluated;
    protected String name;

    public ASearchingAlgorithm (String name) {
        this.name = name;
        this.nodesEvaluated = 0;
    }

    public abstract int getNumberOfNodesEvaluated();

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

    public abstract Solution solve(ISearchable searchable);

    public abstract String getName();

}
