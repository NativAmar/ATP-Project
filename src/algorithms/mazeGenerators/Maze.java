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

    public void setMaze(int row, int column, int value){
        if(value!=0&&value!=1){
            throw new IllegalArgumentException("Invalid value for maze cell: " + value + ". Only 0 or 1 allowed.");
        }
        this.maze[row][column]=value;
    }

    public void setStartPosition(Position start_pos){
        this.start=start_pos;
    }

    public void setGoalPosition(Position end_pos){
        this.end=end_pos;
    }

    public Position getStartPosition(){
        return this.start;
    }

    public Position getGoalPosition(){
        return this.end;
    }

    public int getPosition(Position p){
        return maze[p.getRowIndex()][p.getColumnIndex()];
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

    public void print(){
        for(int i=0;i<this.row;i++){
            for(int j=0;j<this.columns;j++){
                if(start!=null&&i== start.getRowIndex()&&j== start.getColumnIndex()){
                    System.out.print('S');
                } else if (end!=null&&i==end.getRowIndex()&&j==end.getColumnIndex()) {
                    System.out.print('E');

                }
                else{
                    System.out.print(maze[i][j]);
                }
            }
            System.out.println();
        }
    }
}
