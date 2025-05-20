package IO;

import java.io.IOException;
import java.io.InputStream;

public class SimpleDecompressorInputStream extends InputStream {

    private final InputStream in;

    private int currentValue = 0;
    private int remainingCount = 0;

    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        if (remainingCount == 0) {
            int count = in.read();
            if (count == -1) {
                return -1;
            }
            remainingCount = count;
            currentValue = currentValue == 0 ? 1 : 0;
        }

        remainingCount--;
        return currentValue;
    }

    @Override
    public int read(byte[] b) throws IOException {
        int i = 0;
        for (; i < b.length; i++) {
            int val = read();
            if (val == -1) {
                return (i == 0) ? -1 : i;
            }
            b[i] = (byte) val;
        }
        return i;
    }

    @Override
    public void close() throws IOException {
        in.close();
    }
}
