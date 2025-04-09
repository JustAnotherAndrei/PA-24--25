package game;

import model.Bag;
import model.Board;
import model.Dictionary;
import players.Player;

public class Main {
    public static void main(String[] args) {
        Bag bag = new Bag();
        Board board = new Board();
        Dictionary dictionary = new Dictionary();

        // Create a given number of players, for example 3.
        int numPlayers = 3;
        Thread[] threads = new Thread[numPlayers];

        for (int i = 0; i < numPlayers; i++) {
            Player player = new Player("Player" + (i + 1), bag, board, dictionary);
            threads[i] = new Thread(player);
            threads[i].start();
        }

        // Wait for all player threads to finish.
        for (int i = 0; i < numPlayers; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Game over.");
    }
}
