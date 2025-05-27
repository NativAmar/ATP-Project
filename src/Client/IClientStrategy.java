package Client;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Represents a strategy interface for client-side communication logic.
 * Classes that implement this interface define how the client interacts with the server
 * using input and output streams once a connection is established.
 */
public interface IClientStrategy {

    /**
     * Defines the client-side behavior when communicating with the server.
     *
     * @param inFromServer the input stream to read data from the server
     * @param outToServer  the output stream to send data to the server
     */
    void clientStrategy(InputStream inFromServer, OutputStream outToServer);
}
