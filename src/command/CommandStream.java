package src.command;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * A class wrapping an input and output stream which can be used to send
 * serialized command objects.
 */
public class CommandStream {
    public CommandStream(InputStream in, OutputStream out) {
        try {
            // out has to be constructed before in to prevent errors with stream EOF
            this.out = new ObjectOutputStream(out);
            this.in = new ObjectInputStream(in);
        } catch (IOException e) {
            throw new AssertionError("Failed to construct object streams.", e);
        }
    }

    /**
     * Writes Object o to the stream.
     */
    public void writeObject(Object o) {
        try {
            out.writeObject(o);
            out.flush();
        } catch (IOException e) {
            throw new AssertionError("Failed to write object.", e);
        }
    }

    /**
     * Reads an Object of type T from the stream.
     */
    public <T> T read() {
        return this.<T>castObject(readObject());
    }

    /**
     * Reads an Object from the stream.
     */
    public Object readObject() {
        try {
            return in.readObject();
        } catch (Exception e) {
            throw new AssertionError("Failed to read object.", e);
        }
    }

    /**
     * Performs an unchecked cast an Object to type T.
     */
    public <T> T castObject(Object o) {
        @SuppressWarnings("unchecked")
        T result = (T) o;
        return result;
    }

    public void close() {
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            throw new AssertionError("Failed to close command streams.", e);
        }
    }

    private ObjectInputStream in;
    private ObjectOutputStream out;
}
