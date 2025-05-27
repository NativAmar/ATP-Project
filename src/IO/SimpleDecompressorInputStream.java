/**
 * This class implements a simple decompressor that reverses the compression
 * done by SimpleCompressorOutputStream using basic run-length decoding.
 */
package IO;

import java.io.IOException;
import java.io.InputStream;

public class SimpleDecompressorInputStream extends InputStream {
    private final InputStream in;

    /**
     * Constructs a new SimpleDecompressorInputStream.
     *
     * @param in the underlying InputStream to read compressed data from
     */
    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    /**
     * Reads a single byte from the input stream (not used).
     *
     * @return the byte read
     * @throws IOException if an I/O error occurs
     */
    @Override
    public int read() throws IOException {
        return in.read();
    }

    /**
     * Decompresses data from the stream into the provided byte array.
     *
     * @param b the array to hold decompressed data
     * @return number of bytes read
     * @throws IOException if an I/O error occurs
     */
    @Override
    public int read(byte[] b) throws IOException {
        int i = 0;
        for (; i < 12; i++) {
            b[i] = (byte) in.read(); // Read header
        }

        int value = 0;
        int index = i;

        // Run-length decode the remaining bytes
        while (index < b.length) {
            int count = in.read();
            for (int j = 0; j < count; j++) {
                b[index++] = (byte) value;
            }
            value = 1 - value; // Toggle value
        }

        return b.length;
    }
}
