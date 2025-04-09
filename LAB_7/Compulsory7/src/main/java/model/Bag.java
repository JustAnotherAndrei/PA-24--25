package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Bag {
    private List<Tile> tiles;

    public Bag() {
        tiles = new ArrayList<>();
        Random random = new Random();
        // Create 10 tiles for each letter A-Z with a random point value (1-10)
        for (char c = 'A'; c <= 'Z'; c++) {
            int points = random.nextInt(10) + 1; // points between 1 and 10
            for (int i = 0; i < 10; i++) {
                tiles.add(new Tile(c, points));
            }
        }
        // Shuffle the bag so the order is random.
        Collections.shuffle(tiles);
    }

    /**
     * Synchronized extraction of tiles.
     * @param count number of tiles to draw.
     * @return a list of drawn tiles (could be fewer than count if bag runs out)
     */
    public synchronized List<Tile> drawTiles(int count) {
        List<Tile> drawn = new ArrayList<>();
        for (int i = 0; i < count && !tiles.isEmpty(); i++) {
            drawn.add(tiles.remove(0));
        }
        return drawn;
    }

    public synchronized int remainingTiles() {
        return tiles.size();
    }
}
