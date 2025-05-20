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

    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        this.threadPool = Executors.newFixedThreadPool(2);
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.port);
            serverSocket.setSoTimeout(this.listeningIntervalMS);

            while(!this.stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    (new Thread(() -> {
                        this.ServerStrategy(clientSocket);
                    }
                    )).start();
                } catch (SocketTimeoutException var3) {

                }
            }
        } catch (IOException var4) {

        }
    }

    private void ServerStrategy(Socket clientSocket) {
        try {
            this.strategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException var3) {
            IOException e = var3;
        }

    }
}
