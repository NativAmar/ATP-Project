package algorithms.search;

public class AState {

    private AState cameFrom;
    private int cost;

    public AState(AState cameFrom, int cost) {
        this.cameFrom = cameFrom;
        this.cost = cost;
    }

    public AState getCameFrom() {
        return cameFrom;
    }

    public int getCost() {
        return cost;
    }

    public void setCameFrom(AState cameFrom) {
        this.cameFrom = cameFrom;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

}
