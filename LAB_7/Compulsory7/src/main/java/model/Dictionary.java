package model;

import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Dictionary {
    private List<String> words;

    public Dictionary() {
        // A list of acceptable words.
        // In a real application this might be loaded from a file.
        words = Arrays.asList("CAT", "DOG", "BIRD", "FISH", "HELLO", "WORLD", "JAVA", "THREAD", "GAME", "PLAY", "CONCURRENT", "TILE", "PYTHON");
    }

    /**
     * Checks if the word is in the dictionary.
     */
    public boolean isValidWord(String word) {
        return words.contains(word);
    }

    /**
     * Given a hand of tiles, try to find a valid word that can be formed.
     * It goes through each word in the dictionary and checks if the letters of the word
     * can be formed with the available tiles.
     * @param hand the list of tiles
     * @return a valid word if one can be formed, or null if none
     */
    public String getValidWord(java.util.List<Tile> hand) {
        for (String word : words) {
            if (canFormWord(word, hand)) {
                return word;
            }
        }
        return null;
    }

    /**
     * Helper method to check if a word can be formed with the given hand.
     */
    private boolean canFormWord(String word, java.util.List<Tile> hand) {
        Map<Character, Integer> freq = new HashMap<>();
        for (Tile tile : hand) {
            char c = tile.getLetter();
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        for (char c : word.toCharArray()) {
            if (freq.getOrDefault(c, 0) <= 0) {
                return false;
            }
            freq.put(c, freq.get(c) - 1);
        }
        return true;
    }
}
