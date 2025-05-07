// File: src/main/java/game/Player.java

package game;

import dictionary.Dictionary;

import java.util.*;
import java.util.stream.Collectors;

public class Player {
    private final String name;
    private final List<Tile> rack = new ArrayList<>();
    private int score = 0;

    public Player(String name) {
        this.name = name;
    }

    public void addTiles(List<Tile> tiles) {
        rack.addAll(tiles);
    }

    public void playTurn(Dictionary dictionary, TileBag bag) {
        List<String> permutations = generatePermutations("", rack, new HashSet<>());
        Optional<String> bestWord = permutations.stream()
                .filter(dictionary::contains)
                .max(Comparator.comparingInt(this::scoreWord));

        if (bestWord.isPresent()) {
            String word = bestWord.get();
            int wordScore = scoreWord(word);
            score += wordScore;
            System.out.println(name + " played: " + word + " for " + wordScore + " points.");

            removeUsedTiles(word);
            addTiles(bag.drawTiles(word.length()));
        } else {
            System.out.println(name + " could not play and discarded tiles.");
            rack.clear();
            addTiles(bag.drawTiles(7));
        }
    }

    private List<String> generatePermutations(String prefix, List<Tile> tiles, Set<String> results) {
        if (!prefix.isEmpty()) results.add(prefix);
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            List<Tile> remaining = new ArrayList<>(tiles);
            remaining.remove(i);
            generatePermutations(prefix + tile.getLetter(), remaining, results);
        }
        return new ArrayList<>(results);
    }

    private int scoreWord(String word) {
        return word.chars()
                .map(c -> rack.stream()
                        .filter(tile -> tile.getLetter() == c)
                        .findFirst()
                        .map(Tile::getPoints).orElse(0))
                .sum();
    }

    private void removeUsedTiles(String word) {
        for (char c : word.toCharArray()) {
            for (Iterator<Tile> it = rack.iterator(); it.hasNext(); ) {
                if (it.next().getLetter() == c) {
                    it.remove();
                    break;
                }
            }
        }
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
}
