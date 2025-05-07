package game;

import java.util.*;

public class TileBag {
    private final List<Tile> tiles = new ArrayList<>();
    private final Random random = new Random();

    public TileBag() {
        initialize();
        Collections.shuffle(tiles);
    }

    private void initialize() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (char c : letters.toCharArray()) {
            int points = (c - 'A') % 10 + 1;
            for (int i = 0; i < 4; i++) {
                tiles.add(new Tile(c, points));
            }
        }
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
