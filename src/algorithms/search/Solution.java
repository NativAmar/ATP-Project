package algorithms.search;

import java.util.ArrayList;

public class Solution {

    private ArrayList<AState> solution;
    private double cost;

    public Solution(ArrayList<AState> path) {
        this.solution = path;
        if (!path.isEmpty()) {
            this.cost = path.get(path.size() - 1).getCost(); // total path cost in the last element
        } else {
            this.cost = 0;
        }
    }

    public ArrayList<AState> getSolution() {
        return solution;
    }

    public double getCost() {
        return cost;
    }
}
