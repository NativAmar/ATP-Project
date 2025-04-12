package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator{
    @Override
    public long measureAlgorithmTimeMillis(int row, int Columns){
        long a= System.currentTimeMillis();
        Maze maze=generate(row, Columns);
        long b=System.currentTimeMillis();
        return b-a;
    }

}
