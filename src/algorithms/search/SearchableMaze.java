package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable {
    private Maze maze;
    public static final int straightMove = 10;
    public static final int diagonalMove = 15;

    public SearchableMaze(Maze maze) {
        this.maze = maze;
    }

    @Override
    public AState getStartState() {
        Position start = maze.getStartPosition();
        return new MazeState(start.getRowIndex(), start.getColumnIndex(), null);
    }

    @Override
    public AState getGoalState() {
        Position goal = maze.getGoalPosition();
        return new MazeState(goal.getRowIndex(), goal.getColumnIndex(), null);
    }

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
                neighbor.setCost(currentMazeState.getCost() + straightMove);
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
                neighbor.setCost(currentMazeState.getCost() + diagonalMove);
                neighbors.add(neighbor);
            }
        }

        return neighbors;
    }

    private boolean isValidStraightMove(int r, int c) {
        return isInBounds(r, c) && maze.getCell(r, c) == 0;
    }

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

    private boolean isInBounds(int r, int c) {
        return r >= 0 && r < maze.getRow() && c >= 0 && c < maze.getColumn();
    }

}
