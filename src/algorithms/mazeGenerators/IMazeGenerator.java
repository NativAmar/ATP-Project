package algorithms.mazeGenerators;

public interface IMazeGenerator {
    Maze generate(int row, int columns);

    long measureAlgorithmTimeMillis(int row, int Columns);
}
