package IO;

import java.io.IOException;
import java.io.InputStream;

/**
 * A custom InputStream that decompresses maze data previously compressed with
 * MyCompressorOutputStream using run-length decoding.
 * Assumes the first 12 bytes are uncompressed header, and the rest is
 * alternating counts of 0s and 1s.
 */
public class MyDecompressorInputStream extends InputStream {
    private final InputStream in;

    /**
     * Constructor that wraps another input stream.
     * @param in The underlying InputStream to read from.
     */
    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    /**
     * Not used. Provided to comply with InputStream interface.
     */
    @Override
    public int read() throws IOException {
        return in.read();
    }

    /**
     * Reads and decompresses data from the stream into the given byte array.
     * Reads the first 12 bytes as-is (header), then reads an initial value (0 or 1),
     * followed by alternating counts representing how many times each value repeats.
     * @param b The byte array to fill with decompressed data.
     * @return The number of bytes read (filled into b).
     */
    @Override
    public int read(byte[] b) throws IOException {
        int i = 0;

        // Read the header directly
        for (; i < 12; i++) {
            b[i] = (byte) in.read();
        }

        // Read the initial value (0 or 1)
        int value = in.read();
        int index = i;

        // Decode run-length encoded counts
        while (index < b.length) {
            int count = in.read();
            for (int j = 0; j < count && index < b.length; j++) {
                b[index++] = (byte) value;
            }
            value = 1 - value; // Toggle between 0 and 1
        }

        return b.length;
    }
}
