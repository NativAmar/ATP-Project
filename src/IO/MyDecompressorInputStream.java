package IO;

import java.io.IOException;
import java.io.InputStream;

/**
 * A custom InputStream that decompresses maze data previously compressed with
 * MyCompressorOutputStream using run-length decoding.
 *
 * Format:
 * - The first 12 bytes are the maze header (uncompressed).
 * - The rest of the stream contains alternating run-lengths for values 0 and 1.
 *   For example: [0][5][3][4] means: 5 zeros, 3 ones, 4 zeros, etc.
 */
public class MyDecompressorInputStream extends InputStream {
    private final InputStream in;

    /**
     * Constructs a MyDecompressorInputStream that wraps a given input stream.
     *
     * @param in the compressed input stream to read from
     */
    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    /**
     * Reads a single byte from the underlying input stream.
     *
     * @return the byte read, or -1 if end of stream
     * @throws IOException if an I/O error occurs
     */
    @Override
    public int read() throws IOException {
        return in.read();
    }

    /**
     * Reads and decompresses maze data into the provided byte array.
     * Assumes the first 12 bytes of the stream are the uncompressed header,
     * and the rest is run-length encoded using alternating 0s and 1s.
     *
     * This method ensures it stops reading if the input stream ends early,
     * preventing infinite blocking or incorrect filling.
     *
     * @param b the destination array to store the decompressed data
     * @return the number of bytes written to the array
     * @throws IOException if an I/O error occurs
     */
    @Override
    public int read(byte[] b) throws IOException {
        int i = 0;

        // Read the 12-byte uncompressed header
        for (; i < 12; i++) {
            int byteRead = in.read();
            if (byteRead == -1) break;  // Stop if stream ends early
            b[i] = (byte) byteRead;
        }

        // Read the initial value (0 or 1)
        int value = in.read();
        if (value == -1) return i;  // No further data to read

        int index = i;

        // Decompress run-length encoded section
        while (index < b.length) {
            int count = in.read();
            if (count == -1) break;  // Stop if stream ends

            for (int j = 0; j < count && index < b.length; j++) {
                b[index++] = (byte) value;
            }

            value = 1 - value; // Toggle between 0 and 1
        }

        return index;  // Return how many bytes were written
    }
}
