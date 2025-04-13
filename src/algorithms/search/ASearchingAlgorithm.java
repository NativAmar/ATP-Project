package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    protected int nodesEvaluated;

    public ASearchingAlgorithm () {
        this.nodesEvaluated = 0;
    }
    
}
