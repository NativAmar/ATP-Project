package IO;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {

    private final OutputStream out;

    public MyCompressorOutputStream(OutputStream out) {
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
        int bitCounter = 0;
        byte packedByte = 0;

        for (; index < b.length; index++) {
            byte currentBit = (byte) (b[index] & 0x01);
            packedByte = (byte) ((packedByte << 1) | currentBit);
            bitCounter++;

            if (bitCounter == 8) {
                out.write(packedByte);
                packedByte = 0;
                bitCounter = 0;
            }
        }

        if (bitCounter > 0) {
            packedByte = (byte) (packedByte << (8 - bitCounter));
            out.write(packedByte);
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
