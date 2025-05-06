import java.util.*;

public class Game {
    private final List<Player> players = new ArrayList<>();
    private final Bag bag;
    private final Dictionary dictionary;
    private int currentPlayerIndex = 0;
    private boolean gameOver = false;

    public Game(Dictionary dictionary, Bag bag) {
        this.dictionary = dictionary;
        this.bag = bag;
    }

    public synchronized boolean isMyTurn(Player player) {
        return players.get(currentPlayerIndex) == player;
    }

    public synchronized void nextTurn() {
        if (bag.isEmpty()) {
            gameOver = true;
            notifyAll();
        } else {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
    }

    public synchronized boolean isGameOver() {
        return gameOver;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void startGame() {
        for (Player p : players) {
            p.start();
        }
    }

    public void stopGame() {
        gameOver = true;
        for (Player p : players) {
            p.interrupt();
        }
    }

    public void printResults() {
        System.out.println("\nGame Over! Final Scores:");
        for (Player p : players) {
            System.out.println(p.getPlayerName() + ": " + p.getScore());
        }
    }
}
