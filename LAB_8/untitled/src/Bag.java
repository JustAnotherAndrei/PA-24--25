import java.util.*;

public class Bag {
    private final List<Tile> tiles = new ArrayList<>();
    private final Random rand = new Random();

    public Bag() {
        initializeTiles();
    }

    private void initializeTiles() {
        int[] frequencies = {
                9, 2, 2, 4, 12, 2, 3, 2, 9, 1,
                1, 4, 2, 6, 8, 2, 1, 6, 4, 6,
                4, 2, 2, 1, 2, 1
        };
        int[] points = {
                1, 3, 3, 2, 1, 4, 2, 4, 1, 8,
                5, 1, 3, 1, 1, 3, 10, 1, 1, 1,
                1, 4, 4, 8, 4, 10
        };

        for (char c = 'A'; c <= 'Z'; c++) {
            for (int i = 0; i < frequencies[c - 'A']; i++) {
                tiles.add(new Tile(c, points[c - 'A']));
            }
        }
        Collections.shuffle(tiles);
    }

    public synchronized List<Tile> drawTiles(int count) {
        List<Tile> drawn = new ArrayList<>();
        for (int i = 0; i < count && !tiles.isEmpty(); i++) {
            drawn.add(tiles.remove(tiles.size() - 1));
        }
        return drawn;
    }

    public synchronized boolean isEmpty() {
        return tiles.isEmpty();
    }
}
