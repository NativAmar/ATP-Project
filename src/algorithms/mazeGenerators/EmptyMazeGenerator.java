package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator {
    public Maze generate(int row,int columns){
        Maze emptyMaze=new Maze(row,columns);
        for(int r=0;r<row;r++){
            for(int c=0;c<columns;c++){
                emptyMaze.setMaze(r,c,0);
            }
        }
        emptyMaze.setStartPosition(new Position(0, 0));
        emptyMaze.setGoalPosition(new Position(row - 1, columns - 1));
        return emptyMaze;
    }


}
