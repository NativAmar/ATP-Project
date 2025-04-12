package algorithms.mazeGenerators;

import java.util.Random;
import java.util.ArrayList;

public class MyMazeGenerator extends AMazeGenerator {
    Random rand = new Random();

    public Maze generate(int row, int column) {
        row = (row % 2 == 0) ? row : row + 1;
        column = (column % 2 == 0) ? column : column + 1;
        ArrayList<Position> walls = new ArrayList<>();

        if (row < 2 || column < 2) {
            throw new IllegalArgumentException("Invalid Maze Dimensions");
        }

        EmptyMazeGenerator emaze = new EmptyMazeGenerator();
        Maze maze = emaze.generate(row, column);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                maze.setmaze(i, j, 1); // אתחול כל התאים לקירות
            }
        }

        // נקודת התחלה בשוליים
        Position start = getRandomOddEdgePosition(row, column);
        int start_row = start.getRowIndex();
        int start_col = start.getColumnIndex();
        maze.setmaze(start_row, start_col, 0); // תא מעבר

        // מוסיף את הקירות הסמוכים
        if (start_row - 2 > 0)
            walls.add(new Position(start_row - 1, start_col));
        if (start_col - 2 > 0)
            walls.add(new Position(start_row, start_col - 1));
        if (start_row + 2 < row)
            walls.add(new Position(start_row + 1, start_col));
        if (start_col + 2 < column)
            walls.add(new Position(start_row, start_col + 1));

        while (!walls.isEmpty()) {
            int index = rand.nextInt(walls.size());
            Position chosenWall = walls.get(index);
            walls.remove(index);

            Position newpos1 = new Position(chosenWall.getRowIndex() - 1, chosenWall.getColumnIndex());
            Position newpos2 = new Position(chosenWall.getRowIndex(), chosenWall.getColumnIndex() - 1);
            Position newpos3 = new Position(chosenWall.getRowIndex() + 1, chosenWall.getColumnIndex());
            Position newpos4 = new Position(chosenWall.getRowIndex(), chosenWall.getColumnIndex() + 1);

            // כיוון למעלה-למטה
            if (isInside(maze, newpos1) && isInside(maze, newpos3) &&
                    maze.getPosition(newpos1) == 0 && maze.getPosition(newpos3) == 1) {
                maze.setmaze(chosenWall.getRowIndex(), chosenWall.getColumnIndex(), 0);
                maze.setmaze(newpos3.getRowIndex(), newpos3.getColumnIndex(), 0);
                addSurroundingWalls(walls, newpos3, maze);
            }
            // כיוון שמאל-ימין
            else if (isInside(maze, newpos2) && isInside(maze, newpos4) &&
                    maze.getPosition(newpos2) == 0 && maze.getPosition(newpos4) == 1) {
                maze.setmaze(chosenWall.getRowIndex(), chosenWall.getColumnIndex(), 0);
                maze.setmaze(newpos4.getRowIndex(), newpos4.getColumnIndex(), 0);
                addSurroundingWalls(walls, newpos4, maze);
            }
            // כיוון למטה-למעלה (הפוך)
            else if (isInside(maze, newpos3) && isInside(maze, newpos1) &&
                    maze.getPosition(newpos3) == 0 && maze.getPosition(newpos1) == 1) {
                maze.setmaze(chosenWall.getRowIndex(), chosenWall.getColumnIndex(), 0);
                maze.setmaze(newpos1.getRowIndex(), newpos1.getColumnIndex(), 0);
                addSurroundingWalls(walls, newpos1, maze);
            }
            // כיוון ימין-שמאל (הפוך)
            else if (isInside(maze, newpos4) && isInside(maze, newpos2) &&
                    maze.getPosition(newpos4) == 0 && maze.getPosition(newpos2) == 1) {
                maze.setmaze(chosenWall.getRowIndex(), chosenWall.getColumnIndex(), 0);
                maze.setmaze(newpos2.getRowIndex(), newpos2.getColumnIndex(), 0);
                addSurroundingWalls(walls, newpos2, maze);
            }
        }

        // קביעת נקודת סיום חוקית כלשהי בשוליים מתוך תאים פתוחים (0)
        ArrayList<Position> borderZeros = new ArrayList<>();
        for (int i = 1; i < row - 1; i++) {
            if (maze.getPosition(new Position(i, 0)) == 0)
                borderZeros.add(new Position(i, 0));
            if (maze.getPosition(new Position(i, column - 1)) == 0)
                borderZeros.add(new Position(i, column - 1));
        }
        for (int j = 1; j < column - 1; j++) {
            if (maze.getPosition(new Position(0, j)) == 0)
                borderZeros.add(new Position(0, j));
            if (maze.getPosition(new Position(row - 1, j)) == 0)
                borderZeros.add(new Position(row - 1, j));
        }

        Position goal = borderZeros.get(rand.nextInt(borderZeros.size()));

        // בודק אם מרחק קטן מדי
        while ((Math.abs(start.getRowIndex() - goal.getRowIndex()) + Math.abs(start.getColumnIndex() - goal.getColumnIndex())) < 5) {
            goal = borderZeros.get(rand.nextInt(borderZeros.size()));
        }

        maze.setStartPosition(start);
        maze.setGoalPosition(goal);

        return maze;
    }

    private void addSurroundingWalls(ArrayList<Position> walls, Position pos, Maze maze) {
        int r = pos.getRowIndex();
        int c = pos.getColumnIndex();
        if (r - 2 > 0)
            walls.add(new Position(r - 1, c));
        if (r + 2 < maze.getRow())
            walls.add(new Position(r + 1, c));
        if (c - 2 > 0)
            walls.add(new Position(r, c - 1));
        if (c + 2 < maze.getColumn())
            walls.add(new Position(r, c + 1));
    }

    private boolean isInside(Maze maze, Position pos) {
        int r = pos.getRowIndex();
        int c = pos.getColumnIndex();
        return r >= 0 && r < maze.getRow() && c >= 0 && c < maze.getColumn();
    }

    // נקודת התחלה אקראית בשוליים (תא אי-זוגי)
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
