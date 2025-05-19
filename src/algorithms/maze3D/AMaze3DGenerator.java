package algorithms.maze3D;

import algorithms.mazeGenerators.Maze;

public abstract class AMaze3DGenerator implements IMaze3DGenerator {

    public long measureAlgorithmTimeMillis(int depth, int row, int Columns) {
        long a = System.currentTimeMillis();
        Maze3D maze = generate(depth,row, Columns);
        long b = System.currentTimeMillis();
        return b - a;
    }
}
