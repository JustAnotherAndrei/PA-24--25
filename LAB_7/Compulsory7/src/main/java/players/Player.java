package players;

import model.Bag;
import model.Board;
import model.Dictionary;
import model.Tile;
import java.util.ArrayList;
import java.util.List;

public class Player implements Runnable {
    private String name;
    private Bag bag;
    private Board board;
    private Dictionary dictionary;
    private int totalScore;

    public Player(String name, Bag bag, Board board, Dictionary dictionary) {
        this.name = name;
        this.bag = bag;
        this.board = board;
        this.dictionary = dictionary;
        this.totalScore = 0;
    }

    @Override
    public void run() {
        // Continue while there are tiles in the bag.
        while (bag.remainingTiles() > 0) {
            // Draw 7 tiles for the hand.
            List<Tile> hand = bag.drawTiles(7);
            if (hand.isEmpty()) {
                break;
            }

            // Try to form a valid word from the hand using the dictionary.
            String validWord = dictionary.getValidWord(hand);

            if (validWord != null) {
                int wordScore = 0;
                List<Tile> usedTiles = new ArrayList<>();
                // Remove tiles used in the word from the hand.
                for (char c : validWord.toCharArray()) {
                    for (int i = 0; i < hand.size(); i++) {
                        if (hand.get(i).getLetter() == c) {
                            wordScore += hand.get(i).getPoints();
                            usedTiles.add(hand.remove(i));
                            break;
                        }
                    }
                }
                totalScore += wordScore;
                board.submitWord(validWord, wordScore, name);

                // Draw replacement tiles equal to the number of letters used.
                List<Tile> newTiles = bag.drawTiles(validWord.length());
                hand.addAll(newTiles);
            } else {
                // If no valid word can be formed, discard the hand.
                System.out.println("Player " + name + " could not form a valid word and discarded hand.");
            }

            // Pause briefly to simulate thinking time.
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Player " + name + " finished with total score: " + totalScore);
    }
}
