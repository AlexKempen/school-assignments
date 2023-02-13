package src.command;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

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

    public void writeObject(Object o) {
        try {
            out.writeObject(o);
            out.flush();
        } catch (IOException e) {
            throw new AssertionError("Failed to write object.", e);
        }
    }

    public <T> T read() {
        return this.<T>castObject(readObject());
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
