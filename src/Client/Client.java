package Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Represents a network client that connects to a server using a specified strategy.
 * The client establishes a TCP connection to a given IP address and port,
 * and applies a strategy to communicate with the server using input and output streams.
 */
public class Client {
    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy strategy;

    /**
     * Constructs a new {@code Client} with the specified server address, port, and strategy.
     *
     * @param serverIP   the IP address of the server to connect to
     * @param serverPort the port number of the server
     * @param strategy   the strategy to use for client-server communication
     */
    public Client(InetAddress serverIP, int serverPort, IClientStrategy strategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.strategy = strategy;
    }

    /**
     * Connects to the server and delegates communication to the provided strategy.
     * If the connection fails, the exception is caught and printed to the console.
     */
    public void communicateWithServer() {
        try (Socket serverSocket = new Socket(serverIP, serverPort)) {
            System.out.println("connected to server - IP = " + serverIP + ", Port = " + serverPort);
            strategy.clientStrategy(serverSocket.getInputStream(), serverSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
