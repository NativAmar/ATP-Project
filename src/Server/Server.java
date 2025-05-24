package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean stop;
    private ExecutorService threadPool;

    public Server(int port, int listeningIntervalMS, IServerStrategy strategy, int threadPoolSize) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        this.threadPool = Executors.newFixedThreadPool(threadPoolSize);
    }

    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this(port, listeningIntervalMS, strategy, 5); // Default to 5 threads
    }

    public void start() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(this.port);
            serverSocket.setSoTimeout(this.listeningIntervalMS);

            while(!this.stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    this.threadPool.submit(() -> {
                        this.ServerStrategy(clientSocket);
                    });
                } catch (SocketTimeoutException e) {
                    //this.stop();
                    // Nothing — just loop again and check stop
                }
                catch (IOException e) {
                    System.err.println("Error accepting client: " + e.getMessage());
                }
            }

            System.out.println("Server stopped");
        }
        catch (IOException var1) {
            System.err.println("Could not start server on port " + port + ": " + var1.getMessage());
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
    }

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

    public void stop() {
        this.stop = true;
    }
}
