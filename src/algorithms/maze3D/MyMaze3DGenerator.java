package algorithms.maze3D;


import algorithms.mazeGenerators.EmptyMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.Random;

public class MyMaze3DGenerator extends AMaze3DGenerator {
    Random rand = new Random();


    public Maze3D generate(int depth,int row, int column) {

        depth=(depth % 2 == 0) ? depth: depth+1;
        row = (row % 2 == 0) ? row : row + 1;
        column = (column % 2 == 0) ? column : column + 1;
        ArrayList<Position3D> walls = new ArrayList<>();

        if (depth<2||row < 2 || column < 2) {
            throw new IllegalArgumentException("Invalid Maze Dimensions");
        }


        Maze3D maze = new Maze3D(depth,row,column);
        for(int k=0;k<depth;k++){
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {

                    maze.setMaze3D(k,i, j, 1);

            }
        }}

        // Choose random starting position on the edge (odd index)
        Position3D start = getRandomOddEdge3DPosition(depth,row, column);
        int start_depth= start.getDepthIndex();
        int start_row = start.getRowIndex();
        int start_col = start.getColumnIndex();

        maze.setMaze3D(start_depth,start_row, start_col, 0); // mark as path
        // Add adjacent walls to the list



        if (start_row - 2 > 0) walls.add(new Position3D(start_depth,start_row - 1, start_col));
        if (start_row + 2 < row) walls.add(new Position3D(start_depth,start_row + 1, start_col));
        if (start_col - 2 > 0) walls.add(new Position3D(start_depth,start_row, start_col - 1));
        if (start_col + 2 < column) walls.add(new Position3D(start_depth,start_row, start_col + 1));
        if (start_depth + 2 < row) walls.add(new Position3D(start_depth+1,start_row , start_col));
        if (start_depth - 2 < column) walls.add(new Position3D(start_depth-1,start_row, start_col + 1));
        // Main loop to carve paths
        while (!walls.isEmpty()) {
            int index = rand.nextInt(walls.size());
            Position3D chosenWall = walls.remove(index);

            Position3D newpos1 = new Position3D(chosenWall.getDepthIndex(),chosenWall.getRowIndex() -1, chosenWall.getColumnIndex());
            Position3D newpos2 = new Position3D(chosenWall.getDepthIndex(),chosenWall.getRowIndex()+1 , chosenWall.getColumnIndex());
            Position3D newpos3 = new Position3D(chosenWall.getDepthIndex(),chosenWall.getRowIndex(), chosenWall.getColumnIndex()-1);
            Position3D newpos4 = new Position3D(chosenWall.getDepthIndex(),chosenWall.getRowIndex(), chosenWall.getColumnIndex()+1);
            Position3D newpos5 = new Position3D(chosenWall.getDepthIndex()-1,chosenWall.getRowIndex(), chosenWall.getColumnIndex());
            Position3D newpos6 = new Position3D(chosenWall.getDepthIndex()+1,chosenWall.getRowIndex(), chosenWall.getColumnIndex());


            if (isInside(maze, newpos1) && isInside(maze, newpos2) &&
                    maze.getPosition(newpos1) == 0 && maze.getPosition(newpos2) == 1) {
                maze.setMaze3D(chosenWall.getDepthIndex(),chosenWall.getRowIndex(), chosenWall.getColumnIndex(), 0);
                maze.setMaze3D(newpos2.getDepthIndex(),newpos2.getRowIndex(), newpos2.getColumnIndex(), 0);
                addSurroundingWalls(walls, newpos2, maze);
            } else if (isInside(maze, newpos2) && isInside(maze, newpos1) &&
                    maze.getPosition(newpos2) == 0 && maze.getPosition(newpos1) == 1) {
                maze.setMaze3D(chosenWall.getDepthIndex(),chosenWall.getRowIndex(), chosenWall.getColumnIndex(), 0);
                maze.setMaze3D(newpos1.getDepthIndex(),newpos1.getRowIndex(), newpos1.getColumnIndex(), 0);
                addSurroundingWalls(walls, newpos1, maze);
            } else if (isInside(maze, newpos3) && isInside(maze, newpos4) &&
                    maze.getPosition(newpos3) == 0 && maze.getPosition(newpos4) == 1) {
                maze.setMaze3D(chosenWall.getDepthIndex(),chosenWall.getRowIndex(), chosenWall.getColumnIndex(), 0);
                maze.setMaze3D(newpos4.getDepthIndex(),newpos4.getRowIndex(), newpos4.getColumnIndex(), 0);
                addSurroundingWalls(walls, newpos4, maze);
            } else if (isInside(maze, newpos4) && isInside(maze, newpos3) &&
                    maze.getPosition(newpos4) == 0 && maze.getPosition(newpos3) == 1) {
                maze.setMaze3D(chosenWall.getDepthIndex(),chosenWall.getRowIndex(), chosenWall.getColumnIndex(), 0);
                maze.setMaze3D(newpos3.getDepthIndex(),newpos3.getRowIndex(), newpos3.getColumnIndex(), 0);
                addSurroundingWalls(walls, newpos3, maze);
            }
            else if (isInside(maze, newpos5) && isInside(maze, newpos6) &&
                    maze.getPosition(newpos5) == 0 && maze.getPosition(newpos6) == 1) {
                maze.setMaze3D(chosenWall.getDepthIndex(),chosenWall.getRowIndex(), chosenWall.getColumnIndex(), 0);
                maze.setMaze3D(newpos6.getDepthIndex(),newpos6.getRowIndex(), newpos6.getColumnIndex(), 0);
                addSurroundingWalls(walls, newpos6, maze);
            } else if (isInside(maze, newpos6) && isInside(maze, newpos5) &&
                    maze.getPosition(newpos6) == 0 && maze.getPosition(newpos5) == 1) {
                maze.setMaze3D(chosenWall.getDepthIndex(),chosenWall.getRowIndex(), chosenWall.getColumnIndex(), 0);
                maze.setMaze3D(newpos5.getDepthIndex(),newpos5.getRowIndex(), newpos5.getColumnIndex(), 0);
                addSurroundingWalls(walls, newpos5, maze);
            }


        }

        ArrayList<Position3D> borderZeros = new ArrayList<>();

// Check left/right and top/bottom walls for every depth
        for (int d = 0; d < depth; d++) {
            for (int i = 1; i < row - 1; i++) {
                if (maze.getCell(d, i, 0) == 0)
                    borderZeros.add(new Position3D(d, i, 0));
                if (maze.getCell(d, i, column - 1) == 0)
                    borderZeros.add(new Position3D(d, i, column - 1));
            }

            for (int j = 1; j < column - 1; j++) {
                if (maze.getCell(d, 0, j) == 0)
                    borderZeros.add(new Position3D(d, 0, j));
                if (maze.getCell(d, row - 1, j) == 0)
                    borderZeros.add(new Position3D(d, row - 1, j));
            }
        }

// Check the front and back layers
        for (int r = 1; r < row - 1; r++) {
            for (int c = 1; c < column - 1; c++) {
                if (maze.getCell(0, r, c) == 0)
                    borderZeros.add(new Position3D(0, r, c));
                if (maze.getCell(depth - 1, r, c) == 0)
                    borderZeros.add(new Position3D(depth - 1, r, c));
            }
        }

// Choose a goal position far from the start
        Position3D goal = borderZeros.get(rand.nextInt(borderZeros.size()));
        while ((Math.abs(start.getDepthIndex() - goal.getDepthIndex()) +
                Math.abs(start.getRowIndex() - goal.getRowIndex()) +
                Math.abs(start.getColumnIndex() - goal.getColumnIndex())) < 5) {
            goal = borderZeros.get(rand.nextInt(borderZeros.size()));
        }

        maze.setStartPosition(start);
        maze.setGoalPosition(goal);

        return maze;
    }


    /**
     * Adds all valid surrounding walls of a newly opened path cell to the wall list.
     *
     * @param walls list of walls to expand
     * @param pos   the center position
     * @param maze  the maze reference
     */
    private void addSurroundingWalls(ArrayList<Position3D> walls, Position3D pos, Maze3D maze) {
        int d = pos.getDepthIndex();
        int r = pos.getRowIndex();
        int c = pos.getColumnIndex();

        // back and front
        if (d - 2 > 0) walls.add(new Position3D(d - 1, r, c));
        if (d + 2 < maze.getDepth()) walls.add(new Position3D(d + 1, r, c));

        // ups and down
        if (r - 2 > 0) walls.add(new Position3D(d, r - 1, c));
        if (r + 2 < maze.getRow()) walls.add(new Position3D(d, r + 1, c));

        // left and right
        if (c - 2 > 0) walls.add(new Position3D(d, r, c - 1));
        if (c + 2 < maze.getColumn()) walls.add(new Position3D(d, r, c + 1));
    }


    /**
     * Checks if a given position is inside the maze boundaries.
     *
     * @param maze the maze
     * @param pos  the position to check
     * @return true if inside, false otherwise
     */
    private boolean isInside(Maze3D maze, Position3D pos) {
        int d=pos.getDepthIndex();
        int r = pos.getRowIndex();
        int c = pos.getColumnIndex();
        return d>=0&&d<maze.getDepth()&& r>= 0 && r < maze.getRow() && c >= 0 && c < maze.getColumn();
    }

    /**
     * Returns a random odd-numbered position on one of the four edges of the maze.
     *
     * @param row    number of rows
     * @param column number of columns
     * @return the randomly selected edge position
     */
    private Position3D getRandomOddEdge3DPosition(int depth,int row, int column) {
        Random rand = new Random();
        int edge = rand.nextInt(6); // 0=top, 1=bottom, 2=left, 3=right,4=Front,5=Back
        int start_depth,start_row, start_col;

        switch (edge) {
            case 0://top
                start_depth = rand.nextInt((depth - 1) / 2) * 2 + 1;
                start_row = 0;
                start_col = rand.nextInt((column - 1) / 2) * 2 + 1;
                break;
            case 1://bottom
                start_depth = rand.nextInt((depth - 1) / 2) * 2 + 1;
                start_row = row - 1;
                start_col = rand.nextInt((column - 1) / 2) * 2 + 1;
                break;
            case 2://left
                start_depth = rand.nextInt((depth - 1) / 2) * 2 + 1;
                start_col = 0;
                start_row = rand.nextInt((row - 1) / 2) * 2 + 1;
                break;
            case 3://right
                start_depth = rand.nextInt((depth - 1) / 2) * 2 + 1;
                start_col = column - 1;
                start_row = rand.nextInt((row - 1) / 2) * 2 + 1;
                break;

            case 4: // Front
                start_depth = 0;
                start_col = rand.nextInt((column - 1) / 2) * 2 + 1;
                start_row = rand.nextInt((row - 1) / 2) * 2 + 1;
                break;
            case 5: // Back
                start_depth = depth-1;
                start_col = rand.nextInt((column - 1) / 2) * 2 + 1;
                start_row = rand.nextInt((row - 1) / 2) * 2 + 1;
                break;

            default:
                throw new IllegalStateException("Unexpected edge value");
        }

        return new Position3D(start_depth,start_row, start_col);
    }
}

