package algorithms.mazeGenerators;

import java.util.Random;

/**
 * A basic maze generator that connects a start and goal point with a simple path,
 * and randomly adds additional paths.
 */
public class SimpleMazeGenerator extends AMazeGenerator {
    private Random rand = new Random();

    /**
     * Generates a simple maze by carving a direct path from start to goal,
     * and adding random openings.
     *
     * @param row     the number of rows
     * @param columns the number of columns
     * @return a Maze object
     */
    @Override
    public Maze generate(int row, int columns) {
        if (row < 2 || columns < 2)
            throw new IllegalArgumentException("Invalid Maze Dimensions");

        EmptyMazeGenerator emaze = new EmptyMazeGenerator();
        Maze maze = emaze.generate(row, columns);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < columns; j++) {
                maze.setmaze(i, j, 1); // all walls
            }
        }

        Position start, goal;
        do {
            start = getRandomEdgePosition(row, columns);
            goal = getRandomEdgePosition(row, columns);
        } while (getManhattanDistance(start, goal) < 2 || start.equals(goal));

        maze.setmaze(start.getRowIndex(), start.getColumnIndex(), 0);
        maze.setmaze(goal.getRowIndex(), goal.getColumnIndex(), 0);

        craveSimplePath(maze, start, goal);
        int numberOfRandomZeros = (row * columns) / 6;
        addRandomZeros(maze, numberOfRandomZeros);

        maze.setStartPosition(start);
        maze.setGoalPosition(goal);

        return maze;
    }

    /**
     * Selects a random edge cell of the maze.
     */
    private Position getRandomEdgePosition(int row, int col) {
        Random rand = new Random();
        int wall = rand.nextInt(4);
        switch (wall) {
            case 0: return new Position(0, rand.nextInt(col));
            case 1: return new Position(rand.nextInt(row), col - 1);
            case 2: return new Position(row - 1, rand.nextInt(col));
            case 3: return new Position(rand.nextInt(row), 0);
            default: throw new IllegalArgumentException("Invalid wall index");
        }
    }

    /**
     * Returns the Manhattan distance between two positions.
     */
    private int getManhattanDistance(Position p1, Position p2) {
        return Math.abs(p1.getRowIndex() - p2.getRowIndex()) +
                Math.abs(p1.getColumnIndex() - p2.getColumnIndex());
    }

    /**
     * Carves a direct path from start to goal (row-first then column).
     */
    private void craveSimplePath(Maze maze, Position start, Position goal) {
        int currentRow = start.getRowIndex();
        int currentCol = start.getColumnIndex();
        maze.setmaze(currentRow, currentCol, 0);
        while (currentRow != goal.getRowIndex()) {
            currentRow += (goal.getRowIndex() > currentRow) ? 1 : -1;
            maze.setmaze(currentRow, currentCol, 0);
        }
        while (currentCol != goal.getColumnIndex()) {
            currentCol += (goal.getColumnIndex() > currentCol) ? 1 : -1;
            maze.setmaze(currentRow, currentCol, 0);
        }
    }

    /**
     * Adds a specified number of random path cells (0s) into the maze.
     */
    private void addRandomZeros(Maze maze, int count) {
        int rows = maze.getRow();
        int cols = maze.getColumn();
        int added = 0;

        while (added < count) {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);
            if (maze.getCell(r, c) == 1) {
                maze.setmaze(r, c, 0);
                added++;
            }
        }
    }
}
