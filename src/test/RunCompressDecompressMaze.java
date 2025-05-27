/**
 * This class demonstrates how to compress and decompress a Maze using custom streams.
 * It generates a maze, saves it to file using compression, reads it back using decompression,
 * and checks if the original and decompressed mazes are identical.
 */
package test;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;
import java.util.Arrays;

public class RunCompressDecompressMaze {

    /**
     * The main method to run a test: generate, compress, decompress, and verify maze data.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        String mazeFileName = "savedMaze.maze";

        // Generate new maze
        AMazeGenerator mazeGenerator = new MyMazeGenerator();
        Maze maze = mazeGenerator.generate(100, 100);

        try {
            // Save maze to file using MyCompressorOutputStream
            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(mazeFileName));
            out.write(maze.toByteArray());
            out.flush();
            out.close();

            // Load maze from file using MyDecompressorInputStream
            InputStream in = new MyDecompressorInputStream(new FileInputStream(mazeFileName));
            byte[] savedMazeBytes = new byte[maze.toByteArray().length];
            in.read(savedMazeBytes);
            in.close();

            Maze loadedMaze = new Maze(savedMazeBytes);

            boolean areEqual = Arrays.equals(loadedMaze.toByteArray(), maze.toByteArray());
            System.out.println("Mazes are equal: " + areEqual);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
