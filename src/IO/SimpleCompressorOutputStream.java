/**
 * This class implements a simple compressor using a basic run-length encoding (RLE).
 * It compresses sequences of 0s and 1s into counts to reduce data size.
 */
package IO;

import java.io.IOException;
import java.io.OutputStream;

public class SimpleCompressorOutputStream extends OutputStream {
    private final OutputStream out;

    /**
     * Constructs a new SimpleCompressorOutputStream.
     *
     * @param out the underlying OutputStream to which compressed data is written
     */
    public SimpleCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    /**
     * Writes a single byte to the output stream (not used).
     *
     * @param b the byte to write
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    /**
     * Compresses the given byte array using simple RLE and writes it to the output stream.
     *
     * @param b the byte array to compress
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void write(byte[] b) throws IOException {
        int i = 0;
        for (; i < 12; i++) {
            out.write(b[i]); // Write header
        }

        int count = 1;
        for (; i < b.length - 1; i++) {
            if (b[i] == b[i + 1] && count < 255) {
                count++;
            } else {
                out.write(count);
                count = 1;
            }
        }

        out.write(count); // write the final count
    }
}
