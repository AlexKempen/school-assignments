import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the width: ");
        int width = in.nextInt();
        System.out.println("Enter the height: ");
        int height = in.nextInt();
        in.close();

        Maze maze = new Maze(width, height);
        maze.generate();

        List<String> str = maze.getMaze();
        str.forEach(line -> System.out.println(line));
    }
}