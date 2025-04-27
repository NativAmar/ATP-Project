package algorithms.mazeGenerators;

/**
 * Represents a maze composed of walls and paths.
 * Each cell is either a wall (1) or a path (0).
 */
public class Maze {
    private int[][] maze;
    private int row;
    private int columns;
    private Position start;
    private Position end;


    /**
     * Constructs a Maze object with the given dimensions.
     *
     * @param row     number of rows
     * @param columns number of columns
     */
    public Maze(int row, int columns) {
        this.row = row;
        this.columns = columns;
        this.maze = new int[row][columns];

    }

    /**
     * Sets the value of a specific cell in the maze.
     *
     * @param row    the row index
     * @param column the column index
     * @param value  the value (0 for path, 1 for wall)
     */
    public void setMaze(int row, int column, int value) {
        if (value != 0 && value != 1) {
            throw new IllegalArgumentException("Invalid value for maze cell: " + value + ". Only 0 or 1 allowed.");
        }
        this.maze[row][column] = value;
    }

    /**
     * Sets the start position of the maze.
     *
     * @param start_pos the start position
     */
    public void setStartPosition(Position start_pos) {
        this.start = start_pos;
    }

    /**
     * Sets the goal position of the maze.
     *
     * @param end_pos the goal position
     */
    public void setGoalPosition(Position end_pos) {
        this.end = end_pos;
    }

    /**
     * Returns the start position of the maze.
     *
     * @return the start position
     */
    public Position getStartPosition() {
        return this.start;
    }


    /**
     * Returns the goal position of the maze.
     *
     * @return the goal position
     */
    public Position getGoalPosition() {
        return this.end;
    }

    /**
     * Returns the value at a specific position.
     *
     * @param p the position
     * @return 0 or 1 depending on whether the cell is a path or wall
     */
    public int getPosition(Position p) {

        return maze[p.getRowIndex()][p.getColumnIndex()];
    }

    /**
     * Returns the number of columns.
     *
     * @return the number of columns
     */
    public int getColumn() {
        return this.columns;
    }

    /**
     * Returns the number of rows.
     *
     * @return the number of rows
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Returns the value at the specified cell.
     *
     * @param r the row index
     * @param c the column index
     * @return the value (0 or 1)
     */
    public int getCell(int r, int c) {
        return this.maze[r][c];
    }


    /**
     * Prints the maze in a formatted layout.
     *
     * The maze is displayed as a two-dimensional array, with each row enclosed in curly braces `{}`,
     * and each element separated by a comma and a space.
     *
     * If a start position (`start`) is defined, it is represented by 'S' instead of the regular cell value.
     * If an end position (`end`) is defined, it is represented by 'E' instead of the regular cell value.
     *
     * Example output:
     * {
     *  { 0, 0, 1, 0, 1, 0, 0, 1 },
     *  { 1, 0, 1, 0, 1, 0, 1, 0 },
     *  { 1, 0, 0, 0, 0, 0, 0, 1 },
     *  ...
     * };
     */
    public void print() {
        System.out.println("{");

        for (int i = 0; i < this.row; i++) {
            System.out.print(" {");
            for (int j = 0; j < this.columns; j++) {
                if (start != null && i == start.getRowIndex() && j == start.getColumnIndex()) {
                    System.out.print('S');
                } else if (end != null && i == end.getRowIndex() && j == end.getColumnIndex()) {
                    System.out.print('E');
                } else {
                    System.out.print(maze[i][j]);
                }

                if (j < this.columns - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(" },");
        }

        System.out.println("};");
    }


}
