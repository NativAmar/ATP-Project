package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A server strategy that solves a maze sent by the client.
 * It supports caching previously solved mazes by comparing compressed byte arrays.
 * If a solution is found in the cache, it is returned immediately; otherwise,
 * the maze is solved using the configured search algorithm and stored for future use.
 */
public class ServerStrategySolveSearchProblem implements IServerStrategy {

    /**
     * Reads a maze from the client, checks if the solution is already cached.
     * If cached, the solution is returned; otherwise, the maze is solved,
     * the solution is cached, and returned to the client.
     *
     * @param inputStream  Input stream from the client
     * @param outputStream Output stream to send the solution
     */
    @Override
    public void applyStrategy(InputStream inputStream, OutputStream outputStream) {
        try (ObjectInputStream clientInput = new ObjectInputStream(inputStream);
             ObjectOutputStream clientOutput = new ObjectOutputStream(outputStream)) {

            clientOutput.flush();

            // Read maze from client
            Maze maze = (Maze) clientInput.readObject();
            SearchableMaze searchableMaze = new SearchableMaze(maze);
            byte[] mazeBytes = maze.toByteArray();
            Solution solution;

            // Count '1's in maze for caching key
            int ones = 0;
            for (byte b : mazeBytes) {
                if (b == 1) ones++;
            }

            // Save current maze to temp file for comparison
            String tempDirectoryPath = System.getProperty("java.io.tmpdir");
            String tempMazeFile = tempDirectoryPath + File.separator + "savedMaze.maze";

            try (OutputStream out = new MyCompressorOutputStream(new FileOutputStream(tempMazeFile))) {
                out.write(mazeBytes);
                out.flush();
            }

            Path tempPath = Paths.get(tempMazeFile);
            byte[] compressedMazeBytes = Files.readAllBytes(tempPath);

            // Check if maze already exists in cache
            String cachedMazeName = mazeExists(String.valueOf(ones), compressedMazeBytes);

            if (cachedMazeName != null) {
                // Load solution from cache
                solution = getSolution(cachedMazeName);
            } else {
                // Solve maze using configured algorithm
                ASearchingAlgorithm searchingAlgorithm = getConfiguredSearchAlgorithm();
                solution = searchingAlgorithm.solve(searchableMaze);

                // Save maze and solution to cache
                saveMaze(solution, mazeBytes, ones);
            }

            // Send solution back to client
            clientOutput.writeObject(solution);
            clientOutput.flush();

        } catch (ClassNotFoundException e) {
            System.err.println("Failed to deserialize maze object: " + e.getMessage());
            sendNullResponse(outputStream);
        } catch (IOException e) {
            System.err.println("I/O error during maze solving: " + e.getMessage());
            sendNullResponse(outputStream);
        } catch (Exception e) {
            System.err.println("Unexpected error during maze solving: " + e.getMessage());
            sendNullResponse(outputStream);
        }
    }

    /**
     * Retrieves the search algorithm from the configuration.
     * Falls back to BestFirstSearch if unspecified or invalid.
     *
     * @return a configured search algorithm instance
     */
    private static ASearchingAlgorithm getConfiguredSearchAlgorithm() {
        Configurations conf = Configurations.getInstance();
        String search = conf.getProperty("mazeSearchingAlgorithm");

        if (search == null) {
            System.out.println("No search algorithm specified in config. Using default: BestFirstSearch");
            return new BestFirstSearch(); // Default fallback
        }

        switch (search.toLowerCase()) {
            case "breadth":
                return new BreadthFirstSearch();
            case "dfs":
                return new DepthFirstSearch();
            case "best":
            default:
                return new BestFirstSearch();
        }
    }

    /**
     * Saves a newly solved maze and its solution to the cache directory.
     *
     * @param solution   the solution to the maze
     * @param mazeBytes  the original uncompressed maze byte array
     * @param ones       the number of '1's used to create a unique identifier
     */
    private static void saveMaze(Solution solution, byte[] mazeBytes, int ones) {
        try {
            String tempDirectoryPath = System.getProperty("java.io.tmpdir");
            File cacheDir = new File(tempDirectoryPath + File.separator + "Solved_Mazes");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }

            String fileName = checkFile(String.valueOf(ones));
            String fullMazePath = cacheDir.getPath() + File.separator + fileName;

            // Compress directly to final destination using ByteArrayOutputStream
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 MyCompressorOutputStream compressor = new MyCompressorOutputStream(baos)) {

                compressor.write(mazeBytes);
                compressor.flush();

                // Write compressed bytes directly to final file
                try (FileOutputStream fos = new FileOutputStream(fullMazePath)) {
                    fos.write(baos.toByteArray());
                }
            }

            // Save solution
            String solutionPath = cacheDir.getPath() + File.separator + "Solution_" + fileName + ".txt";
            try (PrintWriter writer = new PrintWriter(solutionPath, "UTF-8")) {
                writer.println(solution.toString());
            }

        } catch (IOException e) {
            System.err.println("Error saving maze to cache: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Generates unique filename for maze cache
     */
    private static String checkFile(String num) {
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        File dir = new File(tempDirectoryPath + File.separator + "Solved_Mazes");
        File[] directoryListing = dir.listFiles();
        int count = 1;

        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (cutString(child.getName()).equals(num)) {
                    count++;
                }
            }
        }
        return num + "_" + count;
    }

    /**
     * Checks whether a maze already exists in the cache by comparing compressed bytes.
     *
     * @param ones                the unique identifier prefix based on '1's count
     * @param compressedMazeBytes the byte content of the current compressed maze
     * @return the cached file name if found, otherwise {@code null}
     */
    private static String mazeExists(String ones, byte[] compressedMazeBytes) throws IOException {
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        File dir = new File(tempDirectoryPath + File.separator + "Solved_Mazes");
        File[] directoryListing = dir.listFiles();

        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (cutString(child.getName()).equals(ones)) {
                    byte[] fileBytes = Files.readAllBytes(Paths.get(child.getPath()));
                    if (Arrays.equals(compressedMazeBytes, fileBytes)) {
                        return child.getName();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Extracts number prefix from filename
     */
    private static String cutString(String str) {
        int i = 0;
        while (i < str.length() && str.charAt(i) != '_') {
            i++;
        }
        return str.substring(0, i);
    }

    /**
     * Loads solution from cached file
     */
    private static Solution getSolution(String path) throws IOException {
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        String solutionFilePath = tempDirectoryPath + File.separator + "Solved_Mazes" + File.separator + "Solution_" + path + ".txt";

        String mazeText = readFile(solutionFilePath, StandardCharsets.UTF_8);
        ArrayList<AState> solutionPath = new ArrayList<>();

        int k = 0;
        int counter = 0;
        String x = "", y = "";
        MazeState prevState = null;

        // Find start of solution path
        while (k < mazeText.length() && mazeText.charAt(k) != ':') {
            k++;
        }

        // Parse solution positions
        for (int i = k + 1; i < mazeText.length(); i++) {
            if (mazeText.charAt(i) == '{') {
                x = "";
                y = "";
                i++;
                while (i < mazeText.length() && mazeText.charAt(i) != ',') {
                    x += mazeText.charAt(i);
                    i++;
                }
                i++;
                while (i < mazeText.length() && mazeText.charAt(i) != '}') {
                    y += mazeText.charAt(i);
                    i++;
                }
                counter++;

                int row = Integer.parseInt(x);
                int col = Integer.parseInt(y);

                if (counter == 1) {
                    prevState = null;
                }
                MazeState currState = new MazeState(row, col, prevState);
                prevState = currState;
                solutionPath.add(currState);
            }
        }
        return new Solution(solutionPath);
    }

    /**
     * Reads file content as string
     */
    private static String readFile(String path, java.nio.charset.Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    /**
     * Sends null response to indicate error
     */
    private static void sendNullResponse(OutputStream outputStream) {
        try (ObjectOutputStream errorOutput = new ObjectOutputStream(outputStream)) {
            errorOutput.writeObject(null);
            errorOutput.flush();
        } catch (IOException e) {
            System.err.println("Failed to send error response: " + e.getMessage());
        }
    }
}