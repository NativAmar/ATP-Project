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
        throw new UnsupportedOperationException("Use write(byte[]) instead");
    }

    @Override
    public void write(byte[] b) throws IOException {
        int headerSize = 12;
        out.write(b, 0, headerSize);

        int index = headerSize;
        byte currentValue = 0;
        int count = 0;

        for (; index < b.length; index++) {
            byte value = b[index];
            if (value == currentValue) {
                count++;
                if (count == 255) {
                    out.write(count);
                    count = 0;
                }
            } else {
                out.write(count);
                currentValue = value;
                count = 1;
            }
        }

        if (count > 0) {
            out.write(count);
        }
    }

    @Override
    public void flush() throws IOException {
        out.flush();
    }

    @Override
    public void close() throws IOException {
        out.close();
    }
}
