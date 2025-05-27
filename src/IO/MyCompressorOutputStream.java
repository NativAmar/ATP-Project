/**
 * This class implements a custom OutputStream that compresses maze data
 * using a specific run-length encoding technique before writing to an output stream.
 * It is tailored for compressing large 2D or 3D maze representations.
 */
package IO;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {
    private final OutputStream out;

    /**
     * Constructs a new MyCompressorOutputStream that wraps the given OutputStream.
     *
     * @param out the underlying OutputStream to write compressed data to
     */
    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    /**
     * Writes a single byte to the output stream (not used in custom compression).
     *
     * @param b the byte to be written
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    /**
     * Compresses the given byte array using run-length encoding and writes it to the output stream.
     *
     * @param b the byte array to be compressed and written
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void write(byte[] b) throws IOException {
        int i = 0;
        // Write the header without compression (typically metadata)
        for (; i < 12; i++) {
            out.write(b[i]);
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
