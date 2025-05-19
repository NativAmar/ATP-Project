package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {

    private final InputStream in;
    private byte currentByte = 0;
    private int bitIndex = -1;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        if (bitIndex == -1) {
            int nextByte = in.read();
            if (nextByte == -1) {
                return -1;
            }
            currentByte = (byte) nextByte;
            bitIndex = 7;
        }

        int bit = (currentByte >> bitIndex) & 0x01;
        bitIndex--;

        return bit;
    }

    @Override
    public int read(byte[] b) throws IOException {
        int headerSize = 12;

        // Read header as-is
        int readHeader = in.read(b, 0, headerSize);
        if (readHeader != headerSize) {
            throw new IOException("Failed to read header");
        }

        // Read maze content bit by bit
        for (int i = headerSize; i < b.length; i++) {
            int val = read();
            if (val == -1) {
                return i;
            }
            b[i] = (byte) val;
        }
        return b.length;
    }

    @Override
    public void close() throws IOException {
        in.close();
    }
}
