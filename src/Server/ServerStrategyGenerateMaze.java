package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;

/**
 * A server strategy that generates a maze based on client input (dimensions),
 * compresses it, and sends it back to the client.
 */
public class ServerStrategyGenerateMaze implements IServerStrategy {
    private static final int MAX_DIMENSION = 1000;

    /**
     * Applies the strategy of generating and compressing a maze.
     * The client is expected to send an {@code int[]} of size 2 representing the
     * desired maze dimensions (rows and columns). If the input is valid, a maze is
     * generated using the selected generator, compressed, and the resulting byte array
     * is returned to the client.
     *
     * @param inputStream  the input stream from the client
     * @param outputStream the output stream to send data back to the client
     */
    @Override
    public void applyStrategy(InputStream inputStream, OutputStream outputStream) {
        ObjectOutputStream toClient = null;
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            toClient = new ObjectOutputStream(outputStream);
            toClient.flush();

            // --- Input Handling & Validation ---
            Object obj = fromClient.readObject();
            if (!(obj instanceof int[])) {
                System.err.println("Invalid input: expected int[2]");
                toClient.writeObject(null);
                toClient.flush();
                return;
            }
            int[] mazeDimensions = (int[]) obj;
            if (mazeDimensions.length != 2) {
                System.err.println("Invalid input length: expected 2");
                toClient.writeObject(null);
                toClient.flush();
                return;
            }
            int rows = mazeDimensions[0], cols = mazeDimensions[1];

            if (rows <= 0 || cols <= 0 || rows > MAX_DIMENSION || cols > MAX_DIMENSION) {
                System.err.printf("Invalid maze size: rows=%d, cols=%d%n", rows, cols);
                toClient.writeObject(null);
                toClient.flush();
                return;
            }

            // --- Maze Generator Selection ---
            String generatorName = null;
            try {
                generatorName = Configurations.getInstance().getProperty("mazeGenerationAlgorithm");
            } catch (Throwable t) {
                // If Configurations not available, fallback to default
            }
            AMazeGenerator generator;
            if ("SimpleMazeGenerator".equalsIgnoreCase(generatorName)) {
                generator = new SimpleMazeGenerator();
            } else if ("EmptyMazeGenerator".equalsIgnoreCase(generatorName)) {
                generator = new EmptyMazeGenerator();
            } else {
                generator = new MyMazeGenerator(); // Default
            }

            // --- Maze Generation (protect against generator bugs) ---
            Maze maze;
            try {
                maze = generator.generate(rows, cols);
            } catch (Throwable t) {
                System.err.println("Maze generator threw exception: " + t);
                toClient.writeObject(null);
                toClient.flush();
                return;
            }

            // --- Maze Compression (in memory) ---
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            try (MyCompressorOutputStream compressor = new MyCompressorOutputStream(byteOut)) {
                compressor.write(maze.toByteArray());
            }

            // --- Send compressed maze to client ---
            toClient.writeObject(byteOut.toByteArray());
            toClient.flush();
        } catch (Exception e) {
            System.err.println("Error in ServerStrategyGenerateMaze: " + e);
            // Always respond with null if an error occurs, unless already responded
            try {
                if (toClient == null) {
                    toClient = new ObjectOutputStream(outputStream);
                }
                toClient.writeObject(null);
                toClient.flush();
            } catch (IOException ioException) {
                System.err.println("Failed to send error/null to client: " + ioException);
            }
        }
    }
}