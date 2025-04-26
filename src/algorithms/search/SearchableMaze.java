package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

/**
 * A wrapper class that adapts a Maze object to the {@link ISearchable} interface.
 * Allows generic search algorithms to operate on a maze by providing start/goal states
 * and all valid neighboring states from any given state.
 */
public class SearchableMaze implements ISearchable {
    /**
     * The underlying maze to be searched.
     */
    private Maze maze;

    /**
     * The cost of moving in a straight direction.
     */
    public static final int straightMove = 10;

    /**
     * The cost of moving diagonally.
     */
    public static final int diagonalMove = 15;

    /**
     * Constructs a new SearchableMaze from a given Maze.
     *
     * @param maze The maze to be wrapped and made searchable.
     */
    public SearchableMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Returns the start state of the maze, wrapping the maze's start Position in a MazeState.
     *
     * @return The starting AState of the maze.
     */
    @Override
    public AState getStartState() {
        Position start = maze.getStartPosition();
        return new MazeState(start.getRowIndex(), start.getColumnIndex(), null);
    }

    /**
     * Returns the goal state of the maze, wrapping the maze's goal Position in a MazeState.
     *
     * @return The goal AState of the maze.
     */
    @Override
    public AState getGoalState() {
        Position goal = maze.getGoalPosition();
        return new MazeState(goal.getRowIndex(), goal.getColumnIndex(), null);
    }

    /**
     * Returns all valid neighboring states from the given current state.
     * This includes both straight and diagonal neighbors,
     * following movement rules and maze boundaries.
     *
     * @param currState The current state in the maze.
     * @return A list of valid neighboring AState instances.
     */
    @Override
    public ArrayList<AState> getAllPossibleStates(AState currState) {
        ArrayList<AState> neighbors = new ArrayList<>();
        MazeState currentMazeState = (MazeState) currState;

        Position pos = currentMazeState.getPosition();

        int row = pos.getRowIndex();
        int col = pos.getColumnIndex();

        int[][] straightDirs = {
                {-1, 0}, // Up
                {0, 1},  // Right
                {1, 0},  // Down
                {0, -1}  // Left
        };

        int[][] diagonalDirs = {
                {-1, -1}, // Up - Left
                {-1, 1},  // Up - Right
                {1, 1},   // Down - Right
                {1, -1}   // Down - Left
        };

        // Handle straight directions
        for (int i = 0; i < straightDirs.length; i++) {
            int dRow = straightDirs[i][0];
            int dCol = straightDirs[i][1];
            int newRow = row + dRow;
            int newCol = col + dCol;

            if (isValidStraightMove(newRow, newCol)) {
                MazeState neighbor = new MazeState(newRow, newCol, currentMazeState);
                neighbor.setCost(straightMove);
                neighbors.add(neighbor);
            }
        }

        // Handle diagonals
        for (int i = 0; i < diagonalDirs.length; i++) {
            int dRow = diagonalDirs[i][0];
            int dCol = diagonalDirs[i][1];
            int newRow = row + dRow;
            int newCol = col + dCol;

            if (isValidDiagonalMove(row, col, dRow, dCol)) {
                MazeState neighbor = new MazeState(newRow, newCol, currentMazeState);
                neighbor.setCost(diagonalMove);
                neighbors.add(neighbor);
            }
        }

        return neighbors;
    }

    /**
     * Checks if a straight move to the specified cell is valid.
     * A move is valid if it's within bounds and the cell is passable (value 0).
     *
     * @param r The target row.
     * @param c The target column.
     * @return true if the move is valid; false otherwise.
     */
    private boolean isValidStraightMove(int r, int c) {
        return isInBounds(r, c) && maze.getCell(r, c) == 0;
    }

    /**
     * Checks if a diagonal move is valid based on the maze's R-shape rule.
     * Diagonal moves are only valid if the destination cell and both adjacent
     * straight-side cells are passable.
     *
     * @param row  The current row.
     * @param col  The current column.
     * @param dRow The row offset for the move.
     * @param dCol The column offset for the move.
     * @return true if the diagonal move is valid; false otherwise.
     */
    private boolean isValidDiagonalMove(int row, int col, int dRow, int dCol) {
        int newRow = row + dRow;
        int newCol = col + dCol;

        if (!isInBounds(newRow, newCol) || maze.getCell(newRow, newCol) != 0)
            return false;

        // R-shape rule
        int sideRow1 = row + dRow;
        int sideCol1 = col;
        int sideRow2 = row;
        int sideCol2 = col + dCol;

        return isInBounds(sideRow1, sideCol1) && isInBounds(sideRow2, sideCol2)
                && maze.getCell(sideRow1, sideCol1) == 0
                && maze.getCell(sideRow2, sideCol2) == 0;
    }

    /**
     * Checks if the given row and column are within the maze bounds.
     *
     * @param r The row index.
     * @param c The column index.
     * @return true if the position is within bounds; false otherwise.
     */
    private boolean isInBounds(int r, int c) {
        return r >= 0 && r < maze.getRow() && c >= 0 && c < maze.getColumn();
    }

}
