package IO;

import java.io.IOException;
import java.io.InputStream;

public class SimpleDecompressorInputStream extends InputStream {
    private final InputStream in;

    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        int index = 0;

        // קריאה של 12 בתים ראשונים
        for (int i = 0; i < 12; i++) {
            b[index++] = (byte) in.read();
        }

        // דחיסה לפי [value, count]
        while (index < b.length) {
            int value = in.read();
            int count = in.read();
            if (value == -1 || count == -1)
                break;

            for (int j = 0; j < count && index < b.length; j++) {
                b[index++] = (byte) value;
            }
        }

        return index;
    }
}
