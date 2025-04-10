package algorithms.mazeGenerators;

public class SimpleMazeGenerator extends AMazeGenerator {
    @Override
    public Maze generate(int row,int columns){
        EmptyMazeGenerator emaze = new EmptyMazeGenerator();
        Maze maze=emaze.generate(row,columns);

        //להגדיר את כל התאים כ1
        for(int i=0;i<row;i++){
            for(int j=0;j<columns;j++){
                maze.setmaze(i,j,1);
            }
        }

    }
}
