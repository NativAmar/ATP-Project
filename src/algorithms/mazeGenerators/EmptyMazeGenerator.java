package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator {
    public Maze generate(int row,int columns){
        Maze emptyMaze=new Maze(row,columns);
        for(int r=0;r<row;r++){
            for(int c=0;c<columns;c++){
                emptyMaze.setmaze(r,c,0);
            }
        }
        return emptyMaze;
    }


}
