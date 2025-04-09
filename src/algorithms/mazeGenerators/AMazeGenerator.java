package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerators{
    @Override
    public long measureAlgorithmTimeMillis(int row, int Columns){
        long a= System.currentTimeMillis();
        Maze maze=generate(row, Columns);
        long b=System.currentTimeMillis();
        return a-b;
    }

}
