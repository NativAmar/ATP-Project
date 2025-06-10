package IO;

import java.io.IOException;
import java.io.OutputStream;

public class SimpleCompressorOutputStream extends OutputStream {
    private final OutputStream out;

    public SimpleCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        // Write header (first 12 bytes)
        for (int i = 0; i < 12; i++) {
            out.write(b[i]);
        }

        // Compress the maze body from index 12 onwards
        int currentValue = b[12];
        int count = 1;

        for (int i = 13; i < b.length; i++) {
            if (b[i] == currentValue && count < 255) {
                count++;
            } else {
                out.write(currentValue);
                out.write(count);
                currentValue = b[i];
                count = 1;
            }
        }

        // Write the last pair
        out.write(currentValue);
        out.write(count);
    }
}
