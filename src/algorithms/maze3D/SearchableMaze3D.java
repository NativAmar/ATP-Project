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

    public SearchableMaze3D(Maze3D maze) {
        this.maze = maze;
    }

    @Override
    public AState getStartState() {
        Position3D start = maze.getStartPosition();
        return new Maze3DState(start.getDepthIndex(), start.getRowIndex(), start.getColumnIndex(), null);
    }

    @Override
    public AState getGoalState() {
        Position3D goal = maze.getGoalPosition();
        return new Maze3DState(goal.getDepthIndex(), goal.getRowIndex(), goal.getColumnIndex(), null);
    }

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

    private boolean isValidMove(int d, int r, int c) {
        return d >= 0 && d < maze.getDepth() &&
                r >= 0 && r < maze.getRow() &&
                c >= 0 && c < maze.getColumn() &&
                maze.getCell(d, r, c) == 0;
    }
}

