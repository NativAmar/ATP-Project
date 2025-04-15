package algorithms.mazeGenerators;

/**
 * A maze generator that generates an empty maze with no walls (all cells are open).
 * Start position is set at (0,0), and goal is at the bottom-right corner.
 */
public class EmptyMazeGenerator extends AMazeGenerator {


    /**
     * Generates an empty maze where all cells are paths (value 0).
     *
     * @param row     the number of rows in the maze
     * @param columns the number of columns in the maze
     * @return the generated Maze object
     */
    public Maze generate(int row, int columns) {
        Maze emptyMaze = new Maze(row, columns);
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < columns; c++) {
                emptyMaze.setMaze(r, c, 0);

            }
        }
        emptyMaze.setStartPosition(new Position(0, 0));
        emptyMaze.setGoalPosition(new Position(row - 1, columns - 1));
        return emptyMaze;
    }

}
