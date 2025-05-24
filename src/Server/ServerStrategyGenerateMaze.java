package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy{
    private static final int MAX_DIMENSION = 1000;

    @Override
    public void applyStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.flush();

            // maze input handling
            Object obj = objectInputStream.readObject();
            if (!(obj instanceof int[])) {
                System.err.println("Invalid input: expected int[2]");
                objectOutputStream.writeObject(null);
                return;
            }

            int[] dimensions = (int[]) obj;

            if (dimensions.length != 2) {
                System.err.println("Invalid input length: expected 2");
                objectOutputStream.writeObject(null);
                return;
            }

            int rows = dimensions[0];
            int cols = dimensions[1];

            if (rows <= 0 || cols <= 0 || rows > MAX_DIMENSION || cols > MAX_DIMENSION) {
                System.err.printf("Invalid maze size: rows=%d, cols=%d%n", rows, cols);
                objectOutputStream.writeObject(null);
                return;
            }

            // Generate the maze
            MyMazeGenerator generator = new MyMazeGenerator(); // <- change to any generator you have
            Maze maze = generator.generate(rows, cols);

            // Compress the maze
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            MyCompressorOutputStream compressor = new MyCompressorOutputStream(byteOutput);
            compressor.write(maze.toByteArray());
            compressor.flush();
            compressor.close();

            // Send compressed maze back
            objectOutputStream.writeObject(byteOutput.toByteArray());
            objectOutputStream.flush();

        } catch (Exception e) {
            System.err.println("Error in ServerStrategyGenerateMaze: " + e.getMessage());
        }
    }
}
