// File: src/main/java/Main.java

import game.Game;

public class Main {
    public static void main(String[] args) {
        int numPlayers = 4;          // Number of players
        long limitSeconds = 0;       // 0 = no time limit
        Game game = new Game(numPlayers, limitSeconds);
        game.start();
    }
}
