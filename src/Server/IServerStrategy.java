package Server;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * An interface that defines the server-side strategy to be applied
 * when handling client requests. Implementing classes should provide
 * a concrete way to process input and produce output using the given streams.
 */
public interface IServerStrategy {

    /**
     * Applies a specific strategy using the provided input and output streams.
     * Typically used to process data from a client and send back a response.
     *
     * @param inputStream  the input stream from the client
     * @param outputStream the output stream to send data back to the client
     */
    void applyStrategy(InputStream inputStream, OutputStream outputStream);
}
