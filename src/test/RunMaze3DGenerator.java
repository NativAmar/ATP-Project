package test;

import algorithms.maze3D.Maze3D;
import algorithms.maze3D.MyMaze3DGenerator;

/**
 * Demonstrates generating and printing a 3D maze using MyMaze3DGenerator.
 */
public class RunMaze3DGenerator {
    public static void main(String[] args) {
        MyMaze3DGenerator generator = new MyMaze3DGenerator();
        System.out.println("Generating maze...");
        Maze3D maze = generator.generate(100, 100, 100);
        maze.print();
        System.out.println("Maze generated successfully.");
    }
}

