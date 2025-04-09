package algorithms.search;

import java.util.ArrayList;

public interface ISearchable {
    /**
     *
     * @return the start state of the maze
     */
    AState getStartState();

    /**
     *
     * @return the goal state of the maze
     */
    AState getGoalState();

    /**
     *
     * @param currState a specific state
     * @return a list of all the states we can achieve from the specific state
     */
    ArrayList<AState> getAllPossibleStates(AState currState);
}
