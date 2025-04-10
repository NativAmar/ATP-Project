package algorithms.mazeGenerators;

import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator {
    private Random rand = new Random();
    @Override
    public Maze generate(int row, int columns) {
        if (row < 2 || columns < 2)
            throw new IllegalArgumentException("Invalid Maze Dimensions");

        EmptyMazeGenerator emaze = new EmptyMazeGenerator();
        Maze maze = emaze.generate(row, columns);

        // להפוך את כל התאים לקירות
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < columns; j++) {
                maze.setmaze(i, j, 1);
            }
        }

        // מיקום התחלה וסיום – בקצוות ולא קרובים מדי
        Position start, goal;
        do {
            start = getRandomEdgePosition(row, columns);
            goal = getRandomEdgePosition(row, columns);
        } while (getManhattanDistance(start, goal) < 2 || start.equals(goal));

        maze.setmaze(start.getRowIndex(), start.getColumnIndex(), 0);
        maze.setmaze(goal.getRowIndex(), goal.getColumnIndex(), 0);

        craveSimplePath(maze,start,goal);
        int numberOfRandomZeros = (row * columns) / 6;
        addRandomZeros(maze, numberOfRandomZeros);


        return maze;
    }
    // בוחר נקודה אקראית על גבולות המבוך
    private Position getRandomEdgePosition(int row, int col) {
        Random rand = new Random();
        int wall = rand.nextInt(4); // 0=top, 1=right, 2=bottom, 3=left
        switch (wall) {
            case 0: return new Position(0, rand.nextInt(col));        // עליון
            case 1: return new Position(rand.nextInt(row), col - 1);  // ימין
            case 2: return new Position(row - 1, rand.nextInt(col));  // תחתון
            case 3: return new Position(rand.nextInt(row), 0);        // שמאל
            default: throw new IllegalArgumentException("Invalid wall index");
        }
    }

    // מחשב מרחק מנח (Manhattan distance) בין 2 נקודות
    private int getManhattanDistance(Position p1, Position p2) {
        return Math.abs(p1.getRowIndex() - p2.getRowIndex()) +
                Math.abs(p1.getColumnIndex() - p2.getColumnIndex());
    }

    private void craveSimplePath(Maze maze,Position start,Position goal){
      int currentRow=start.getRowIndex();
      int currentCol=start.getColumnIndex();
      maze.setmaze(currentRow, currentCol, 0);
      while(currentRow!=goal.getRowIndex()){
          currentRow+=(goal.getRowIndex()>currentRow)?1:-1; // כל עוד התנאי בסוגרים מתקיים עולה ב1 אם לא יורד ב1
          maze.setmaze(currentRow, currentCol, 0);
      }
      while(currentCol!=goal.getColumnIndex()){
          currentCol+=(goal.getColumnIndex()>currentCol)?1:-1;
          maze.setmaze(currentRow,currentCol,0);
      }

    }
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

}}}
