package IO;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A custom OutputStream that compresses a maze using run-length encoding (RLE).
 * The first 12 bytes (header) are written as-is. Then, the compressed maze data
 * is written by counting sequences of 0s and 1s. The first value after the header
 * is written explicitly to allow correct decoding.
 */
public class MyCompressorOutputStream extends OutputStream {
    private final OutputStream out;

    /**
     * Constructor that wraps another output stream.
     * @param out The underlying OutputStream to write to.
     */
    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    /**
     * Not used. Provided to comply with OutputStream interface.
     */
    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    /**
     * Compresses the given byte array using RLE and writes the result to the stream.
     * The first 12 bytes are written without compression.
     * After that, the compression alternates between 0s and 1s and writes counts.
     * @param b the original byte array representing the maze.
     */
    @Override
    public void write(byte[] b) throws IOException {
        int i = 0;

        // Write header as-is (first 12 bytes)
        for (; i < 12; i++) {
            out.write(b[i]);
        }

        // Write the initial value (0 or 1)
        int currentValue = b[i];
        out.write(currentValue);

        // Apply run-length encoding from i onward
        int count = 1;
        for (; i < b.length - 1; i++) {
            if (b[i] == b[i + 1] && count < 255) {
                count++;
            } else {
                out.write(count);
                count = 1;
            }
        }

        // Write final count
        out.write(count);
    }
}
