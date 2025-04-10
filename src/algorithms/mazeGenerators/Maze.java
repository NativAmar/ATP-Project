package algorithms.mazeGenerators;

public class Maze {

    private int[][]maze;
    private int row;
    private int columns;
    private Position start;
    private Position end;
    public Maze(int row,int columns){
        this.row=row;
        this.columns=columns;
        this.maze=new int[row][columns];
    }
    public void setmaze(int row,int column,int value){
        if(value!=0&&value!=1){
            throw new IllegalArgumentException("Invalid value for maze cell: " + value + ". Only 0 or 1 allowed.");
        }
        this.maze[row][column]=value;
    }

    public Position getStartPosition(){
        return this.start;
    }
    public Position getEndPosition(){
        return this.end;
    }

    public int getColumn(){
        return this.columns;
    }

    public int getRow(){
        return this.row;
    }

    public int getCell(int r,int c){
        return this.maze[r][c];
    }

}
