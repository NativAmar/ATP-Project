package algorithms.mazeGenerators;

public interface IMazeGenerators {
    Maze generate(int row, int columns);

    long measureAlgorithmTimeMillis(int row, int Columns);
}
