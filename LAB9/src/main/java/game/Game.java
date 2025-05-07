// File: src/main/java/game/Game.java

package game;

import dictionary.Dictionary;

import java.io.IOException;
import java.util.*;

public class Game {
    private final List<Player> players = new ArrayList<>();
    private final TileBag tileBag = new TileBag();
    private final Dictionary dictionary;

    public Game(int numPlayers, long timeLimitSeconds) {
        try {
            this.dictionary = new Dictionary("src\\main\\resource\\dictionary.txt");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load dictionary", e);
        }

        for (int i = 1; i <= numPlayers; i++) {
            Player player = new Player("Player " + i);
            player.addTiles(tileBag.drawTiles(7));
            players.add(player);
        }
    }

    public void start() {
        while (!tileBag.isEmpty()) {
            for (Player player : players) {
                if (tileBag.isEmpty()) break;
                player.playTurn(dictionary, tileBag);
            }
        }
        printResults();
    }

    private void printResults() {
        players.sort(Comparator.comparingInt(Player::getScore).reversed());
        System.out.println("\n=== Final Scores ===");
        for (Player p : players) {
            System.out.println(p.getName() + ": " + p.getScore());
        }
        System.out.println("Winner: " + players.get(0).getName());
    }
}
