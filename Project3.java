import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Project3 {
    static final int START_SIZE = 1024;
    public static void main(String[] args) throws FileNotFoundException {
        PrintStream out = System.out;
        File inputFile = new File("input.txt");

        List<Request> requests = getRequests(inputFile);
        /// We use a heap structure to organize left and right tree
        List<Box> boxes = new ArrayList<>();
        Box box = new Box(START_SIZE);
        boxes.add(box);

        execute(out, requests, boxes);
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

    public static void execute(PrintStream out, List<Request> requests, List<Box> boxes) {
        printBoxes(out, boxes);
        for (Request request : requests) {
            String str;
            if (request.isRequest) {
                str = String.format("Request %dK", request.size);
            }
            else {
                str = String.format("Release %c", request.value);
            }
            out.println(str);
            handleRequest(request, boxes);
            printBoxes(out, boxes);
        }
    }

    public static void handleRequest(Request request, List<Box> boxes) {
        if (request.isRequest) {
            allocateBox(request.size, boxes);
        }
        else {
            releaseBox(request.value, boxes);
        }
    }

    public static void allocateBox(int size, List<Box> boxes) {
        for (Box box : boxes) {
            if (box.isEmpty() && box.getSize() >= size) {
                while (box.getSize() / 2 >= size) {
                    int index = boxes.indexOf(box);
                    boxes.add(index + 1, box.split());
                }
                box.allocate();
                return;
            }
        }
    }

    public static void releaseBox(char c, List<Box> boxes) {
        for (Box box : boxes) {
            if (box.getValue() == c) {
                box.release();
                combineBoxes(box, boxes);
                return;
            }
        }
        throw new AssertionError("Failed to find char in boxes.");
    }

    public static void combineBoxes(Box box, List<Box> boxes) {
        int totalSize = boxes.get(0).getSize();
        for (int i = 1; i < boxes.size(); ++i) {
            Box curr = boxes.get(i), prev = boxes.get(i - 1);
            totalSize += curr.getSize();

            if (START_SIZE % totalSize == 0 && curr.isEmpty() && prev.isEmpty() && curr.getSize() == prev.getSize()) {
                boxes.remove(prev);
                curr.merge();
                // back up to enable recursive merges; twice because we removed behind
                i -= Math.min(i, 2);
                totalSize -= curr.getSize();
            }
        }
    }

    public static void printBoxes(PrintStream out, List<Box> boxes) {
        String middle = "|";
        for (Box box : boxes) {
            middle += box.toString() + "|";
        }
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

    public Box(int size) {
        this.size = size;
    }

    // public Box(int size, int pairOffset) {
    //     this.size = size;
    //     this.pairOffset = pairOffset;
    // }

    public void allocate() {
        value = (char)('A' + count++);
        isEmpty = false;
    }

    public void release() {
        isEmpty = true;
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
    
    public void merge() {
        size *= 2;
    }

    public Box split() {
        size /= 2;
        return new Box(size);
    }

    @Override
    public String toString() {
        return String.format(" %c%5dK ", (isEmpty ? ' ' : value), size);
    }
}

