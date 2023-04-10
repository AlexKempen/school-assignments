import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Project3 {
    public static void main(String[] args) throws FileNotFoundException {
        PrintStream out = System.out;

        File inputFile = new File("input.txt");
        List<Request> requests = getRequests(inputFile);
        List<Box> boxes = new ArrayList<>();
        Box box = new Box(1024);
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
            combineBoxes(boxes);
        }
    }

    public static void allocateBox(int size, List<Box> boxes) {
        for (Box box : boxes) {
            if (box.value == ' ' && box.size >= size) {
                while (box.size / 2 >= size) {
                    box.split();
                    boxes.add(boxes.indexOf(box) + 1, new Box(box.size));
                }
                box.allocate(size);
                return;
            }
        }
    }

    public static void releaseBox(char c, List<Box> boxes) {
        for (Box box : boxes) {
            if (box.value == c) {
                box.release();
                return;
            }
        }
        throw new AssertionError("Failed to find char in boxes.");
    }

    /**
     * Recombines adjacent boxes if they are free and of the same size.
     */
    public static void combineBoxes(List<Box> boxes) {
        for (int i = 0; i < boxes.size() - 1; ++i) {
            Box curr = boxes.get(i), next = boxes.get(i + 1);
            if (curr.value == ' ' && next.value == ' ' && curr.size == next.size) {
                boxes.remove(i + 1);
                curr.merge();
                // back up to enable recursive merges
                i -= 1;
            }
        }
    }

    public static void printBoxes(PrintStream out, List<Box> boxes) {
        String middle = "|";
        for (Box box : boxes) {
            middle += box.toString() + "|";
        }
        String top = "-".repeat(middle.length());
        String bottom = "-".repeat(middle.length());
        out.println(top);
        out.println(middle);
        out.println(bottom);
    }
}

class Request {
    boolean isRequest;
    int size;
    char value;
}

class Box {
    private static int count = 0;
    public int size;
    public char value = ' ';
    private int savedSize;

    public Box(int size) {
        this.size = size;
    }

    public void allocate(int requestSize) {
        savedSize = size;
        size = requestSize;
        value = (char)('A' + count++);
    }

    public void release() {
        size = savedSize;
        value = ' ';
    }
    
    public void merge() {
        size *= 2;
    }

    public void split() {
        size /= 2;
    }

    @Override
    public String toString() {
        return String.format(" %c%5dK ", value, size);
    }
}

