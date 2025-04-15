package algorithms.search;

public abstract class AState {

    private AState cameFrom;
    private double cost;

    public AState(AState cameFrom, double cost) {
        this.cameFrom = cameFrom;
        this.cost = cost;
    }

    public AState getCameFrom() {
        return cameFrom;
    }

    public double getCost() {
        return cost;
    }

    public void setCameFrom(AState cameFrom) {
        this.cameFrom = cameFrom;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public abstract int compareTo(AState other);
}
