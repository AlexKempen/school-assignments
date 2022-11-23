import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Maze {
    public Maze(int width, int height) {
        if (width <= 1 || height <= 1) {
            throw new IllegalArgumentException("Width and height must be greater than 1.");
        }
        this.width = width;
        this.height = height;
    }

    public void generate() {
        int size = width * height;
        cells = new DisjSet(size);

        Stream<Wall> verticalWalls = Stream.iterate(0, n -> n + 1)
                .limit(size - 1)
                .filter(n -> n % width != width - 1)
                .map(n -> new Wall(n, n + 1));
        Stream<Wall> horizontalWalls = Stream.iterate(0, n -> n + 1)
                .limit(size - width)
                .map(n -> new Wall(n, n + width));

        walls = Stream.concat(verticalWalls,
                horizontalWalls).collect(Collectors.toList());
        Collections.shuffle(walls); // randomize collections

        Iterator<Wall> wallItr = walls.iterator();
        int removes = 0;
        // remove first size - 1 valid walls. Since we're iterating over every wall,
        // guaranteed to find size - 1 walls to remove.
        while (removes < size - 1) {
            Wall curr = wallItr.next();
            if (!curr.sameCell(cells)) {
                cells.union(cells.find(curr.cell1), cells.find(curr.cell2));
                wallItr.remove();
                ++removes;
            }
        }
    }

    public List<String> getMaze() {
        // Walls exist between any pair of adjacent cells which aren't in the same set
        // List<List<Character>> walls =
        List<StringBuilder> chars = new ArrayList<>();
        chars.add(new StringBuilder("   " + "_ ".repeat(width - 1))); // top row

        for (int i = 0; i < height - 1; ++i) {
            chars.add(new StringBuilder());
            chars.get(i + 1).append("|" + "  ".repeat(width - 1) + " |");
        }
        chars.get(1).setCharAt(0, ' '); // set entrance
        chars.add(new StringBuilder("|" + "_ ".repeat(width - 1))); // bottom row

        walls.forEach(wall -> {
            StringBuilder str = chars.get(1 + wall.cell1 / width);
            int offset = 1 + 2 * (wall.cell1 % width);
            str.setCharAt(offset + (wall.isVertical() ? 1 : 0),
                    (wall.isVertical() ? '|' : '_'));
        });
        return chars.parallelStream().map(str -> str.toString()).collect(Collectors.toList());
    }

    private int width;
    private int height;
    private DisjSet cells;
    private List<Wall> walls;
}

class Wall {
    public int cell1;
    public int cell2;

    public Wall(int cell1, int cell2) {
        if (cell1 >= cell2) {
            throw new IllegalArgumentException("cell1 must be smaller than cell2.");
        }
        this.cell1 = cell1;
        this.cell2 = cell2;
    }

    public boolean sameCell(DisjSet cells) {
        return cells.find(cell1) == cells.find(cell2);
    }

    /**
     * Returns true if the wall represents a vertical wall.
     */
    public boolean isVertical() {
        return cell1 + 1 == cell2;
    }
}