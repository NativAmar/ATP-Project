package algorithms.mazeGenerators;

import java.util.Random;
import java.util.ArrayList;

public class MyMazeGenerator extends AMazeGenerator {
    Random rand = new Random();

    public Maze generate(int row, int column){
        row = (row % 2 == 0) ? row : row + 1;
        column = (column % 2 == 0) ? column : column + 1;
        ArrayList<Position> walls = new ArrayList<>();

        if (row < 2 || column < 2){
            throw new IllegalArgumentException("Invalid Maze Dimensions");
        }

        EmptyMazeGenerator emaze = new EmptyMazeGenerator();
        Maze maze = emaze.generate(row, column);

        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++){
                maze.setmaze(i, j, 1); // כל התאים קיר
            }
        }

        Position start = getRandomOddEdgePosition(row, column);
        int start_row = start.getRowIndex();
        int start_col = start.getColumnIndex();
        maze.setmaze(start_row, start_col, 0); // תא מעבר
        maze.setStartPosition(start); // קביעת נקודת התחלה

        if(start_row - 1 != 0)
            walls.add(new Position(start_row - 1, start_col));
        if(start_col - 1 != 0)
            walls.add(new Position(start_row, start_col - 1));
        if(start_row + 1 != row)
            walls.add(new Position(start_row + 1, start_col));
        if(start_col + 1 != column)
            walls.add(new Position(start_row, start_col + 1));

        while(!walls.isEmpty()){
            int index = rand.nextInt(walls.size());
            Position chosenWall = walls.get(index);
            Position newpos1 = new Position(chosenWall.getRowIndex() - 1, chosenWall.getColumnIndex());
            Position newpos2 = new Position(chosenWall.getRowIndex(), chosenWall.getColumnIndex() - 1);
            Position newpos3 = new Position(chosenWall.getRowIndex() + 1, chosenWall.getColumnIndex());
            Position newpos4 = new Position(chosenWall.getRowIndex(), chosenWall.getColumnIndex() + 1);

            walls.remove(index);

            if(isInsideMaze(newpos1, row, column) && isInsideMaze(newpos3, row, column) &&
                    maze.getPosition(newpos1) == 0 && maze.getPosition(newpos3) == 1){
                maze.setmaze(chosenWall.getRowIndex(), chosenWall.getColumnIndex(), 0);
                maze.setmaze(newpos3.getRowIndex(), newpos3.getColumnIndex(), 0);
                newpos1 = new Position(newpos3.getRowIndex(), newpos3.getColumnIndex() - 1);
                newpos2 = new Position(newpos3.getRowIndex() + 1, newpos3.getColumnIndex());
                newpos3 = new Position(newpos3.getRowIndex(), newpos3.getColumnIndex() + 1);
                if(newpos1.getColumnIndex() != 0)
                    walls.add(newpos1);
                if(newpos2.getRowIndex() != row)
                    walls.add(newpos2);
                if(newpos3.getColumnIndex() != column)
                    walls.add(newpos3);
            }
            else if(isInsideMaze(newpos2, row, column) && isInsideMaze(newpos4, row, column) &&
                    maze.getPosition(newpos2) == 0 && maze.getPosition(newpos4) == 1){
                maze.setmaze(chosenWall.getRowIndex(), chosenWall.getColumnIndex(), 0);
                maze.setmaze(newpos4.getRowIndex(), newpos4.getColumnIndex(), 0);
                newpos1 = new Position(newpos4.getRowIndex() - 1, newpos4.getColumnIndex());
                newpos3 = new Position(newpos4.getRowIndex(), newpos4.getColumnIndex() + 1);
                newpos4 = new Position(newpos4.getRowIndex() + 1, newpos4.getColumnIndex());
                if(newpos1.getRowIndex() != 0)
                    walls.add(newpos1);
                if(newpos3.getColumnIndex() != column)
                    walls.add(newpos3);
                if(newpos4.getRowIndex() != row)
                    walls.add(newpos4);
            }
            else if(isInsideMaze(newpos3, row, column) && isInsideMaze(newpos1, row, column) &&
                    maze.getPosition(newpos3) == 0 && maze.getPosition(newpos1) == 1){
                maze.setmaze(chosenWall.getRowIndex(), chosenWall.getColumnIndex(), 0);
                maze.setmaze(newpos1.getRowIndex(), newpos1.getColumnIndex(), 0);
                newpos2 = new Position(newpos1.getRowIndex() - 1, newpos1.getColumnIndex());
                newpos3 = new Position(newpos1.getRowIndex(), newpos1.getColumnIndex() - 1);
                newpos4 = new Position(newpos1.getRowIndex(), newpos1.getColumnIndex() + 1);
                if(newpos2.getRowIndex() != 0)
                    walls.add(newpos2);
                if(newpos3.getColumnIndex() != 0)
                    walls.add(newpos3);
                if(newpos4.getRowIndex() != row)
                    walls.add(newpos4);
            }
            else if(isInsideMaze(newpos4, row, column) && isInsideMaze(newpos2, row, column) &&
                    maze.getPosition(newpos4) == 0 && maze.getPosition(newpos2) == 1){
                maze.setmaze(chosenWall.getRowIndex(), chosenWall.getColumnIndex(), 0);
                maze.setmaze(newpos2.getRowIndex(), newpos2.getColumnIndex(), 0);
                newpos1 = new Position(newpos2.getRowIndex() - 1, newpos2.getColumnIndex());
                newpos3 = new Position(newpos2.getRowIndex(), newpos2.getColumnIndex() - 1);
                newpos4 = new Position(newpos2.getRowIndex() + 1, newpos2.getColumnIndex());
                if(newpos1.getRowIndex() != 0)
                    walls.add(newpos1);
                if(newpos3.getColumnIndex() != 0)
                    walls.add(newpos3);
                if(newpos4.getRowIndex() != row)
                    walls.add(newpos4);
            }
        }

        // ✅ בחירת נקודת סיום תקפה בגבול מתוך תאי מעבר (0)
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
        maze.setGoalPosition(goal); // 👈 שימוש ב-setEndPosition לפי המחלקה שלך

        return maze;
    }

    private boolean isInsideMaze(Position pos, int row, int col) {
        int r = pos.getRowIndex();
        int c = pos.getColumnIndex();
        return (r >= 0 && r < row && c >= 0 && c < col);
    }

    private Position getRandomOddEdgePosition(int row, int column) {
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
