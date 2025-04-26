package test;

import algorithms.maze3D.*;
import algorithms.search.*;

/**
 * Demonstrates running a search algorithm on a 3D maze.
 * Generates a 3D maze, wraps it in a searchable interface, and solves it using BFS.
 */
public class RunSearchOnMaze3D {
    public static void main(String[] args) {
        MyMaze3DGenerator generator = new MyMaze3DGenerator();
        Maze3D maze = generator.generate(5, 5, 5);

        SearchableMaze3D searchableMaze = new SearchableMaze3D(maze);

        ASearchingAlgorithm searcher = new BreadthFirstSearch();
        Solution solution = searcher.solve(searchableMaze);

        System.out.println("Start: " + searchableMaze.getStartState());
        System.out.println("Goal: " + searchableMaze.getGoalState());
        System.out.println("Solution path:");
        for (AState state : solution.getSolutionPath()) {
            System.out.println(state);
        }

        System.out.println("Number of nodes evaluated: " + searcher.getNumberOfNodesEvaluated());
    }
}

