package algorithms.maze3D;

/**
 * Interface for maze generators that can generate a maze
 * and measure the time it takes to generate one.
 */
public interface IMazeGenerator3D {


    Maze3D generate(int depth, int row, int columns);


    long measureAlgorithmTimeMillis(int depth,int row, int Columns);
}
