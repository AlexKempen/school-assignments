import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.util.Pair;

public class Maze {
    public Maze(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Generates the maze.
     */
    public void generate() {
        int size = width * height;
        cells = new DisjSet(size);

        Stream<Pair<Integer, Integer>> verticalWalls = Stream.iterate(0, n -> n + 1).limit(size - 1)
                .filter(n -> n % width != width - 1)
                .map(n -> new Pair<>(n, n + 1));

        Stream<Pair<Integer, Integer>> horizontalWalls = Stream.iterate(0, n -> n + 1)
                .limit(size - width)
                .map(n -> new Pair<>(n, n + width));
        walls = Stream.concat(verticalWalls, horizontalWalls).collect(Collectors.toList());
        Collections.shuffle(walls);

        System.out.println(walls.size());

        ListIterator<Pair<Integer, Integer>> itr = walls.listIterator();

        // we expect to knock down width * height - 1 walls since
        // each valid wall knocked down allows us to reach one additional set of cells
        walls = walls.stream()
                // this is a really dubious use of streams...
                .filter(pair -> {
                    boolean result = cells.find(pair.getKey()) != cells.find(pair.getValue());
                    if (result) {
                        cells.union(cells.find(pair.getKey()),
                                cells.find(pair.getValue()));
                    }
                    return result;
                })
                .limit(size - 1) // only keep size - 1 walls
                .collect(Collectors.toList());

        // int wallsToDestroy = size - 1;
        // while (wallsToDestroy > 0) {
        // if (cell1 != cell2 && cells.find(cell1) != cells.find(cell2)) {
        // // path compression makes second find O(1)
        // cells.union(cells.find(cell1), cells.find(cell2));
        // wallsToDestroy--;
        // }
        // }
    }

    public List<String> getMaze() {
        // Walls exist between any pair of adjacent cells which aren't in the same set
        List<CharSequence> c = new ArrayList<>();

        StringBuilder str;
        return new ArrayList<String>();
    }

    private int width;
    private int height;
    private DisjSet cells;
    private List<Pair<Integer, Integer>> walls;
}
