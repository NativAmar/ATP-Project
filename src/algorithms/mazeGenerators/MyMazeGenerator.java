package algorithms.mazeGenerators;

import java.util.Random;
import java.util.ArrayList;

/**
 * A maze generator that uses a randomized version of Prim's algorithm to generate mazes.
 * The generated maze has walls (1) and paths (0), with a start and goal on the edges.
 */
public class MyMazeGenerator extends AMazeGenerator {
    Random rand = new Random();

    /**
     * Generates a maze using a modified Prim’s algorithm.
     * The maze is initialized fully walled, then carved from a random odd cell on the border.
     *
     * @param row    the number of rows
     * @param column the number of columns
     * @return the generated Maze object
     */
    public Maze generate(int row, int column) {
        // Ensure even dimensions
        row = (row % 2 == 0) ? row : row + 1;
        column = (column % 2 == 0) ? column : column + 1;
        ArrayList<Position> walls = new ArrayList<>();

        if (row < 2 || column < 2) {
            throw new IllegalArgumentException("Invalid Maze Dimensions");
        }

        // Initialize maze and fill it with walls
        EmptyMazeGenerator emaze = new EmptyMazeGenerator();
        Maze maze = emaze.generate(row, column);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {

                maze.setMaze(i, j, 1);

            }
        }

        // Choose random starting position on the edge (odd index)
        Position start = getRandomOddEdgePosition(row, column);
        int start_row = start.getRowIndex();
        int start_col = start.getColumnIndex();

        maze.setMaze(start_row, start_col, 0); // mark as path


        // Add adjacent walls to the list
        if (start_row - 2 > 0) walls.add(new Position(start_row - 1, start_col));
        if (start_col - 2 > 0) walls.add(new Position(start_row, start_col - 1));
        if (start_row + 2 < row) walls.add(new Position(start_row + 1, start_col));
        if (start_col + 2 < column) walls.add(new Position(start_row, start_col + 1));

        // Main loop to carve paths
        while (!walls.isEmpty()) {
            int index = rand.nextInt(walls.size());
            Position chosenWall = walls.remove(index);

            Position newpos1 = new Position(chosenWall.getRowIndex() - 1, chosenWall.getColumnIndex());
            Position newpos2 = new Position(chosenWall.getRowIndex(), chosenWall.getColumnIndex() - 1);
            Position newpos3 = new Position(chosenWall.getRowIndex() + 1, chosenWall.getColumnIndex());
            Position newpos4 = new Position(chosenWall.getRowIndex(), chosenWall.getColumnIndex() + 1);

            // Carve paths depending on adjacency
            if (isInside(maze, newpos1) && isInside(maze, newpos3) &&
                    maze.getPosition(newpos1) == 0 && maze.getPosition(newpos3) == 1) {
                maze.setMaze(chosenWall.getRowIndex(), chosenWall.getColumnIndex(), 0);
                maze.setMaze(newpos3.getRowIndex(), newpos3.getColumnIndex(), 0);
                addSurroundingWalls(walls, newpos3, maze);
            } else if (isInside(maze, newpos2) && isInside(maze, newpos4) &&
                    maze.getPosition(newpos2) == 0 && maze.getPosition(newpos4) == 1) {
                maze.setMaze(chosenWall.getRowIndex(), chosenWall.getColumnIndex(), 0);
                maze.setMaze(newpos4.getRowIndex(), newpos4.getColumnIndex(), 0);
                addSurroundingWalls(walls, newpos4, maze);
            } else if (isInside(maze, newpos3) && isInside(maze, newpos1) &&
                    maze.getPosition(newpos3) == 0 && maze.getPosition(newpos1) == 1) {
                maze.setMaze(chosenWall.getRowIndex(), chosenWall.getColumnIndex(), 0);
                maze.setMaze(newpos1.getRowIndex(), newpos1.getColumnIndex(), 0);
                addSurroundingWalls(walls, newpos1, maze);
            } else if (isInside(maze, newpos4) && isInside(maze, newpos2) &&
                    maze.getPosition(newpos4) == 0 && maze.getPosition(newpos2) == 1) {
                maze.setMaze(chosenWall.getRowIndex(), chosenWall.getColumnIndex(), 0);
                maze.setMaze(newpos2.getRowIndex(), newpos2.getColumnIndex(), 0);
                addSurroundingWalls(walls, newpos2, maze);
            }
        }

        // Select a valid border goal cell far enough from the start
        ArrayList<Position> borderZeros = new ArrayList<>();
        for (int i = 1; i < row - 1; i++) {
            if (maze.getPosition(new Position(i, 0)) == 0) borderZeros.add(new Position(i, 0));
            if (maze.getPosition(new Position(i, column - 1)) == 0) borderZeros.add(new Position(i, column - 1));
        }
        for (int j = 1; j < column - 1; j++) {
            if (maze.getPosition(new Position(0, j)) == 0) borderZeros.add(new Position(0, j));
            if (maze.getPosition(new Position(row - 1, j)) == 0) borderZeros.add(new Position(row - 1, j));
        }

        Position goal = borderZeros.get(rand.nextInt(borderZeros.size()));
        while ((Math.abs(start.getRowIndex() - goal.getRowIndex()) +
                Math.abs(start.getColumnIndex() - goal.getColumnIndex())) < 5) {
            goal = borderZeros.get(rand.nextInt(borderZeros.size()));
        }

        maze.setStartPosition(start);
        maze.setGoalPosition(goal);
        return maze;
    }

    /**
     * Adds all valid surrounding walls of a newly opened path cell to the wall list.
     *
     * @param walls list of walls to expand
     * @param pos   the center position
     * @param maze  the maze reference
     */
    private void addSurroundingWalls(ArrayList<Position> walls, Position pos, Maze maze) {
        int r = pos.getRowIndex();
        int c = pos.getColumnIndex();
        if (r - 2 > 0) walls.add(new Position(r - 1, c));
        if (r + 2 < maze.getRow()) walls.add(new Position(r + 1, c));
        if (c - 2 > 0) walls.add(new Position(r, c - 1));
        if (c + 2 < maze.getColumn()) walls.add(new Position(r, c + 1));
    }

    /**
     * Checks if a given position is inside the maze boundaries.
     *
     * @param maze the maze
     * @param pos  the position to check
     * @return true if inside, false otherwise
     */
    private boolean isInside(Maze maze, Position pos) {
        int r = pos.getRowIndex();
        int c = pos.getColumnIndex();
        return r >= 0 && r < maze.getRow() && c >= 0 && c < maze.getColumn();
    }

    /**
     * Returns a random odd-numbered position on one of the four edges of the maze.
     *
     * @param row    number of rows
     * @param column number of columns
     * @return the randomly selected edge position
     */
    private Position getRandomOddEdgePosition(int row, int column) {
        Random rand = new Random();
        int edge = rand.nextInt(4); // 0=top, 1=bottom, 2=left, 3=right
        int start_row, start_col;

        switch (edge) {
            case 0:
                start_row = 0;
                start_col = rand.nextInt((column - 1) / 2) * 2 + 1;
                break;
            case 1:
                start_row = row - 1;
                start_col = rand.nextInt((column - 1) / 2) * 2 + 1;
                break;
            case 2:
                start_col = 0;
                start_row = rand.nextInt((row - 1) / 2) * 2 + 1;
                break;
            case 3:
                start_col = column - 1;
                start_row = rand.nextInt((row - 1) / 2) * 2 + 1;
                break;
            default:
                throw new IllegalStateException("Unexpected edge value");
        }

        return new Position(start_row, start_col);
    }
}
