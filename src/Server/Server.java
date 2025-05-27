package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple multi-threaded server that listens for client connections
 * and applies a provided server strategy to each client using a thread pool.
 */
public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean stop;
    private ExecutorService threadPool;

    /**
     * Constructs a new {@code Server} instance.
     * The thread pool size is determined from the configuration file (via {@code Configurations.getInstance()}),
     * using the property {@code "threadPoolSize"}. If the property is missing or invalid, a default of 10 threads is used.
     * The constructor ignores the {@code threadPoolSize} parameter passed and always reads from configuration.
     * @param port                the port number on which the server will listen
     * @param listeningIntervalMS the time in milliseconds to wait for a client before re-checking the stop condition
     * @param strategy            the strategy to apply when handling a client connection
     */
    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        Configurations conf = Configurations.getInstance();
        String size = conf.getProperty("threadPoolSize");
        if (size == null)
            size = "10";
        this.threadPool = Executors.newFixedThreadPool(Integer.parseInt(size));
    }

    /**
     * Starts the server in a new thread.
     * Listens for incoming client connections and handles each one using the strategy and a thread from the pool.
     */
    public void start() {
        Thread serverThread = new Thread(() -> {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(this.port);
                serverSocket.setSoTimeout(this.listeningIntervalMS);

                while (!this.stop) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        this.threadPool.submit(() -> {
                            this.ServerStrategy(clientSocket);
                        });
                    } catch (SocketTimeoutException e) {
                        // Timeout: re-check stop condition
                    } catch (IOException e) {
                        System.err.println("Error accepting client: " + e.getMessage());
                    }
                }

                System.out.println("Server on port " + this.port + " stopped.");
            } catch (IOException e) {
                System.err.println("Could not start server on port " + port + ": " + e.getMessage());
            } finally {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        System.err.println("Error closing server socket: " + e.getMessage());
                    }
                }
                threadPool.shutdown();
            }
        });

        serverThread.start();
    }

    /**
     * Handles a client connection by applying the server strategy.
     *
     * @param clientSocket the socket connected to the client
     */
    private void ServerStrategy(Socket clientSocket) {
        try {
            this.strategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Failed to close client socket: " + e.getMessage());
            }
        }
    }

    /**
     * Stops the server gracefully. The server will finish handling current clients
     * and will no longer accept new ones.
     */
    public void stop() {
        this.stop = true;
    }
}
