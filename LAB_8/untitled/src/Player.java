import java.util.*;

public class Player extends Thread {
    private final String name;
    private final Dictionary dictionary;
    private final Bag bag;
    private final Game game;
    private final List<Tile> hand = new ArrayList<>();
    private int score = 0;

    public Player(String name, Dictionary dictionary, Bag bag, Game game) {
        this.name = name;
        this.dictionary = dictionary;
        this.bag = bag;
        this.game = game;
    }

    public String getPlayerName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    private String tryCreateWord() {
        String handStr = handToString();
        for (String word : dictionary.getWords()) {
            if (canFormWord(word, handStr)) {
                return word;
            }
        }
        return null;
    }

    private boolean canFormWord(String word, String letters) {
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : letters.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        for (char c : word.toCharArray()) {
            if (!freq.containsKey(c) || freq.get(c) == 0) return false;
            freq.put(c, freq.get(c) - 1);
        }
        return true;
    }

    private String handToString() {
        StringBuilder sb = new StringBuilder();
        for (Tile tile : hand) {
            sb.append(tile.getLetter());
        }
        return sb.toString();
    }

    private int calculatePoints(String word) {
        int points = 0;
        for (char c : word.toCharArray()) {
            for (Tile tile : hand) {
                if (tile.getLetter() == c) {
                    points += tile.getPoints();
                    break;
                }
            }
        }
        return points;
    }

    private void removeUsedLetters(String word) {
        for (char c : word.toCharArray()) {
            for (int i = 0; i < hand.size(); i++) {
                if (hand.get(i).getLetter() == c) {
                    hand.remove(i);
                    break;
                }
            }
        }
    }

    public void run() {
        synchronized (game) {
            hand.addAll(bag.drawTiles(7));
        }

        while (!game.isGameOver()) {
            synchronized (game) {
                while (!game.isMyTurn(this)) {
                    try {
                        game.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }

                if (game.isGameOver()) break;

                String word = tryCreateWord();
                if (word != null) {
                    score += calculatePoints(word);
                    System.out.println(name + " plays word: " + word + " (+ " + score + " pts)");
                    removeUsedLetters(word);
                    hand.addAll(bag.drawTiles(word.length()));
                } else {
                    System.out.println(name + " cannot form a word. Drawing new tiles.");
                    hand.clear();
                    hand.addAll(bag.drawTiles(7));
                }

                game.nextTurn();
                game.notifyAll();
            }
        }
    }
}
