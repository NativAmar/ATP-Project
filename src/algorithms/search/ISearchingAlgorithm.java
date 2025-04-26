package algorithms.search;

/**
 * An interface that defines the structure for search algorithms.
 * Implementing classes are expected to provide a mechanism for solving search problems,
 * tracking the number of nodes evaluated, and identifying the algorithm by name.
 */
public interface ISearchingAlgorithm {

    /**
     * Solves the given searchable problem and returns the solution path, if one exists.
     *
     * @param searchable The problem to be solved, implementing the {@link ISearchable} interface.
     * @return A Solution representing the path from the start to the goal state,
     *         or null if no solution exists.
     */
    Solution solve(ISearchable searchable);

    /**
     * Returns the number of nodes that were evaluated during the most recent search operation.
     *
     * @return The count of evaluated nodes.
     */
    int getNumberOfNodesEvaluated();

    /**
     * Returns the name of the search algorithm.
     *
     * @return The algorithm's name as a string.
     */
    String getName();
}
