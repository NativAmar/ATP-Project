package algorithms.mazeGenerators;

/**
 * An abstract base class for maze generators implementing the IMazeGenerator interface.
 * Provides a default implementation for measuring the execution time of the maze generation.
 */
public abstract class AMazeGenerator implements IMazeGenerator {

    /**
     * Measures the time in milliseconds required to generate a maze of given dimensions.
     *
     * @param row    the number of rows in the maze
     * @param Columns the number of columns in the maze
     * @return the duration in milliseconds of the maze generation
     */
    @Override
    public long measureAlgorithmTimeMillis(int row, int Columns) {
        long a = System.currentTimeMillis();
        Maze maze = generate(row, Columns);
        long b = System.currentTimeMillis();
        return b - a;
    }

}
