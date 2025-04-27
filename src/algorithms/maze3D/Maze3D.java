package algorithms.maze3D;

import algorithms.mazeGenerators.Position;

/**
 * Maze3D represents a 3D maze structure using a 3D integer array.
 * The value 0 represents a path, and 1 represents a wall.
 * It also contains start and goal positions in 3D space.
 */
public class Maze3D {
    private int[][][] maze3D;
    private int depth;
    private int row;
    private int columns;
    private Position3D start;
    private Position3D goal;

    /**
     * Constructs a Maze3D object with given dimensions.
     *
     * @param depth   the number of layers in the maze
     * @param row     the number of rows in each layer
     * @param columns the number of columns in each row
     */
    public Maze3D(int depth, int row, int columns) {
        this.depth = depth;
        this.row = row;
        this.columns = columns;
        this.maze3D = new int[depth][row][columns];
    }

    /**
     * Sets the value of a specific cell in the maze.
     *
     * @param depth  the depth index
     * @param row    the row index
     * @param column the column index
     * @param value  0 for path, 1 for wall
     */
    public void setMaze3D(int depth, int row, int column, int value) {
        if (value != 0 && value != 1) {
            throw new IllegalArgumentException("Invalid value for maze cell: " + value + ". Only 0 or 1 allowed.");
        }
        this.maze3D[depth][row][column] = value;
    }

    /**
     * Sets the start position of the maze.
     *
     * @param start_pos the starting position
     */
    public void setStartPosition(Position3D start_pos) {
        this.start = start_pos;
    }

    /**
     * Sets the goal (end) position of the maze.
     *
     * @param end_pos the goal position
     */
    public void setGoalPosition(Position3D end_pos) {
        this.goal = end_pos;
    }

    /**
     * Returns the entire 3D maze map.
     *
     * @return a 3D array representing the maze
     */
    public int[][][] getmap() {
        return maze3D;
    }

    /**
     * Returns the start position of the maze.
     *
     * @return the start position
     */
    public Position3D getStartPosition() {
        return this.start;
    }

    /**
     * Returns the goal position of the maze.
     *
     * @return the goal position
     */
    public Position3D getGoalPosition() {
        return this.goal;
    }

    /**
     * Returns the value at a specific position in the maze.
     *
     * @param p the position in 3D space
     * @return 0 for path, 1 for wall
     */
    public int getPosition(Position3D p) {
        return maze3D[p.getDepthIndex()][p.getRowIndex()][p.getColumnIndex()];
    }

    /**
     * Returns the number of layers in the maze.
     *
     * @return the depth of the maze
     */
    public int getDepth() {
        return this.depth;
    }

    /**
     * Returns the number of columns in each row.
     *
     * @return the number of columns
     */
    public int getColumn() {
        return this.columns;
    }

    /**
     * Returns the number of rows in each layer.
     *
     * @return the number of rows
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Returns the value at a specific cell in the maze.
     *
     * @param d the depth index
     * @param r the row index
     * @param c the column index
     * @return 0 for path, 1 for wall
     */
    public int getCell(int d, int r, int c) {
        return this.maze3D[d][r][c];
    }

    /**
     * Prints the maze in a formatted 3D structure.
     * 'S' marks the start position, 'E' marks the goal position,
     * and other values are 0 or 1.
     */
    public void print() {
        System.out.println("{");
        for (int d = 0; d < depth; d++) {
            for (int r = 0; r < row; r++) {
                System.out.print("{ ");
                for (int c = 0; c < columns; c++) {
                    if (start != null && d == start.getDepthIndex() && r == start.getRowIndex() && c == start.getColumnIndex()) {
                        System.out.print("S ");
                    } else if (goal != null && d == goal.getDepthIndex() && r == goal.getRowIndex() && c == goal.getColumnIndex()) {
                        System.out.print("E ");
                    } else {
                        System.out.print(maze3D[d][r][c] + " ");
                    }
                }
                System.out.println("}");
            }
            if (d < depth - 1) {
                System.out.print("---");
                for (int i = 0; i < columns; i++) {
                    System.out.print("--");
                }
                System.out.println();
            }
        }
        System.out.println("}");
    }

}
