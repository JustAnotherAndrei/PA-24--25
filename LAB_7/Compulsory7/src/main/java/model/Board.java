package model;

public class Board {

    /**
     * Synchronized method to submit a word.
     * It prints out the move.
     */
    public synchronized void submitWord(String word, int score, String playerName) {
        System.out.println("Player " + playerName + " played word: " + word + " for " + score + " points.");
    }
}
