package algorithms.maze3D;

import algorithms.search.AState;
import algorithms.search.ISearchable;
import algorithms.maze3D.Maze3DState;

import java.util.ArrayList;


/**
 * Adapts a Maze3D into a searchable format for search algorithms.
 * Provides start, goal and neighbor states.
 */
public class SearchableMaze3D implements ISearchable {
    private Maze3D maze;

    /**
     * Constructs a SearchableMaze3D with the specified Maze3D.
     *
     * @param maze the 3D maze to adapt for searching
     */
    public SearchableMaze3D(Maze3D maze) {
        this.maze = maze;
    }

    /**
     * Returns the starting state of the maze.
     *
     * @return the start AState representing the start position of the maze
     */
    @Override
    public AState getStartState() {
        Position3D start = maze.getStartPosition();
        return new Maze3DState(start.getDepthIndex(), start.getRowIndex(), start.getColumnIndex(), null);
    }

    /**
     * Returns the goal state of the maze.
     *
     * @return the goal AState representing the goal position of the maze
     */
    @Override
    public AState getGoalState() {
        Position3D goal = maze.getGoalPosition();
        return new Maze3DState(goal.getDepthIndex(), goal.getRowIndex(), goal.getColumnIndex(), null);
    }

    /**
     * Generates all possible valid neighbor states for the given current state.
     * Movement is allowed in six directions: forward, backward, up, down, left, and right,
     * provided the destination cell is within the maze bounds and not a wall (cell value == 0).
     *
     * @param currState the current AState
     * @return an ArrayList of neighboring AStates
     */
    @Override
    public ArrayList<AState> getAllPossibleStates(AState currState) {
        ArrayList<AState> neighbors = new ArrayList<>();
        Maze3DState currentState = (Maze3DState) currState;

        int d = currentState.getD();
        int r = currentState.getR();
        int c = currentState.getC();

        // Movement directions: forward, backward, up, down, left, right
        int[][] directions = {
                {-1, 0, 0},
                {1, 0, 0},
                {0, -1, 0},
                {0, 1, 0},
                {0, 0, -1},
                {0, 0, 1}
        };

        for (int[] dir : directions) {
            int newD = d + dir[0];
            int newR = r + dir[1];
            int newC = c + dir[2];

            if (isValidMove(newD, newR, newC)) {
                Maze3DState neighbor = new Maze3DState(newD, newR, newC, currentState);
                neighbor.setCost(currentState.getCost() + 10);
                neighbors.add(neighbor);
            }
        }

        return neighbors;
    }

    /**
     * Checks if a move to the specified coordinates is valid within the maze.
     * A move is valid if the target cell is within bounds and is not a wall (cell value == 0).
     *
     * @param d the depth index
     * @param r the row index
     * @param c the column index
     * @return true if the move is valid; false otherwise
     */
    private boolean isValidMove(int d, int r, int c) {
        return d >= 0 && d < maze.getDepth() &&
                r >= 0 && r < maze.getRow() &&
                c >= 0 && c < maze.getColumn() &&
                maze.getCell(d, r, c) == 0;
    }
}

