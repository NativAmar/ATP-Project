/**
 * This class implements a custom InputStream that decompresses data previously
 * compressed using the MyCompressorOutputStream.
 * It uses a run-length decoding method to reconstruct the original maze data.
 */
package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {
    private final InputStream in;

    /**
     * Constructs a new MyDecompressorInputStream that wraps the given InputStream.
     *
     * @param in the underlying InputStream to read compressed data from
     */
    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    /**
     * Reads a single byte from the input stream (not used in custom decompression).
     *
     * @return the byte read
     * @throws IOException if an I/O error occurs
     */
    @Override
    public int read() throws IOException {
        return in.read();
    }

    /**
     * Reads compressed data from the stream and decompresses it into the provided byte array.
     *
     * @param b the destination array to hold the decompressed maze data
     * @return the number of bytes read into the array
     * @throws IOException if an I/O error occurs
     */
    @Override
    public int read(byte[] b) throws IOException {
        int i = 0;
        // Read header as-is
        for (; i < 12; i++) {
            b[i] = (byte) in.read();
        }

        int value = 0;
        int index = i;

        // Read and decode the run-length compressed data
        while (index < b.length) {
            int count = in.read();
            for (int j = 0; j < count; j++) {
                b[index++] = (byte) value;
            }
            value = 1 - value; // Toggle between 0 and 1
        }

        return b.length;
    }
}
