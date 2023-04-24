import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

class Project3 {
    static final int START_SIZE = 1024;
    public static void main(String[] args) throws FileNotFoundException {
        PrintStream out = System.out;
        File inputFile = new File("input.txt");

        List<Request> requests = getRequests(inputFile);
        Box root = new Box(START_SIZE);
        execute(out, requests, root);
    }

    public static List<Request> getRequests(File file) throws FileNotFoundException  {
        try (Scanner in = new Scanner(file)) {
            return in.findAll("(Request|Release) (\\d+|[A-Z])").map(match -> {
                Request request = new Request();
                if (match.group(1).equals("Request")) {
                    request.isRequest = true;
                    request.size = Integer.parseInt(match.group(2));
                }
                else if (match.group(1).equals("Release")) {
                    request.isRequest = false;
                    request.value = match.group(2).charAt(0);
                }
                return request;
            }).toList();
        } 
    }

    public static void execute(PrintStream out, List<Request> requests, Box root) {
        printBoxes(out, root);
        for (Request request : requests) {
            String str;
            if (request.isRequest) {
                str = String.format("Request %dK", request.size);
            }
            else {
                str = String.format("Release %c", request.value);
            }
            out.println(str);
            handleRequest(request, root);
            printBoxes(out, root);
        }
    }

    public static void handleRequest(Request request, Box root) {
        if (request.isRequest) {
            allocateBox(request.size, root);
        }
        else {
            releaseBox(request.value, root);
        }
    }

    public static void allocateBox(int size, Box root) {
        if (!root.allocate(size)) {
            throw new AssertionError("Failed to find sufficient space in root.");
        }
    }

    public static void releaseBox(char c, Box root) {
        if (!root.release(c)) {
            throw new AssertionError("Failed to find char in boxes.");
        }
        root.merge();
    }

    public static void printBoxes(PrintStream out, Box root) {
        String middle = "|" + root.toString();
        String header = "-".repeat(middle.length());
        out.println(header);
        out.println(middle);
        out.println(header);
    }
}

class Request {
    boolean isRequest;
    int size;
    char value;
}

class Box {
    private static int count = 0;

    private int size;
    private boolean isEmpty = true;
    private char value;

    private Box left = null;
    private Box right = null;

    public Box(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public int getValue() {
        return value;
    }
    
    public boolean isEmpty() {
        return isEmpty;
    }

    public boolean allocate(int requestSize) {
        if (left != null && left.allocate(requestSize)) {
            return true;
        }
        else if (right != null && right.allocate(requestSize)) {
            return true;
        }
        // choose this (empty) node to allocate about
        else if (left == null && right == null && isEmpty && requestSize <= size) {
            // request is small; split this node and allocate, possibly recursively
            if (requestSize <= size / 2) {
                split();
                isEmpty = false;
                left.allocate(requestSize);
            }
            else {
                // assign to node
                value = (char)('A' + count++);
                isEmpty = false;
            }
            return true;
        }
        return false;
    }

    public boolean release(char c) {
        if (value == c) {
            isEmpty = true;
            return true;
        }
        else if (left != null && left.release(c)) {
            return true;
        }
        else if (right != null && right.release(c)) {
            return true;
        }
        return false;
    }
    
    public void merge() {
        if (left != null) {
            left.merge();
        }
        if (right != null) {
            right.merge();
        }

        if (left != null && right != null && left.isEmpty() && right.isEmpty()) {
            isEmpty = true;
            left = null;
            right = null;
        }
    }

    public void split() {
        if (!isEmpty) {
            throw new AssertionError("Cannot split non-empty box.");
        }
        left = new Box(size / 2);
        right = new Box(size / 2);
    }

    @Override
    public String toString() {
        if (left == null && right == null) {
            return String.format(" %c%5dK ", (isEmpty ? ' ' : value), size) + "|";
        }
        // tree is guaranteed to be balanced thanks to split
        return left.toString() + right.toString();
    }
}

