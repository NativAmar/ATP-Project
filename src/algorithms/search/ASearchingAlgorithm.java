package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    private int nodesEvaluated;

    public ASearchingAlgorithm () {
        this.nodesEvaluated = 0;
    }

}
