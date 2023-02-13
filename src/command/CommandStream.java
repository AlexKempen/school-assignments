package src.command;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class CommandStream {
    public CommandStream(InputStream in, OutputStream out) {
        makeObjectStreams(in, out);
    }

    private void makeObjectStreams(InputStream in, OutputStream out) {
        try {
            this.in = new ObjectInputStream(in);
            this.out = new ObjectOutputStream(out);
        } catch (IOException e) {
            throw new AssertionError("Failed to construct object input stream.", e);
        }
    }

    public void writeObject(Object o) {
        try {
            out.writeObject(o);
            out.flush();
        } catch (IOException e) {
            throw new AssertionError("Failed to write object.", e);
        }
    }

    public Object readObject() {
        try {
            return in.readObject();
        } catch (Exception e) {
            throw new AssertionError("Failed to read object.", e);
        }
    }

    /**
     * Cast an Object to a given type.
     */
    public <T> T castObject(Object o) {
        @SuppressWarnings("unchecked")
        T result = (T) o;
        return result;
    }

    private ObjectInputStream in;
    private ObjectOutputStream out;
}
