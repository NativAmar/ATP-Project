package algorithms.mazeGenerators;

/**
 * Interface for maze generators that can generate a maze
 * and measure the time it takes to generate one.
 */
public interface IMazeGenerator {

    /**
     * Generates a maze with the given number of rows and columns.
     *
     * @param row     the number of rows
     * @param columns the number of columns
     * @return a Maze object representing the generated maze
     */
    Maze generate(int row, int columns);

    /**
     * Measures the time (in milliseconds) it takes to generate a maze.
     *
     * @param row     the number of rows
     * @param Columns the number of columns
     * @return the duration in milliseconds
     */
    long measureAlgorithmTimeMillis(int row, int Columns);
}
