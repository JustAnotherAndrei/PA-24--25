package game;

import java.util.ArrayList;
import java.util.List;

public class HexGame {
    private final String gameId;
    private final Board board;
    private final long timeLimitSec;
    private final List<Player> players = new ArrayList<>();
    private int turnIndex = 0;
    private boolean finished = false;
    private String winner = null;

    public HexGame(String gameId, int size, long timeLimitSec) {
        this.gameId = gameId;
        this.board = new Board(size);
        this.timeLimitSec = timeLimitSec;
    }

    public synchronized boolean addPlayer(String name) {
        if (players.size() >= 2) return false;
        char sym = players.isEmpty() ? 'X' : 'O';
        players.add(new Player(name, sym, timeLimitSec));
        return true;
    }

    public synchronized String submitMove(String playerName, int x, int y) {
        if (finished) return "Game over.";
        Player current = players.get(turnIndex);
        if (!current.getName().equals(playerName)) {
            return "Not your turn.";
        }
        if (!current.endTurn()) {
            finished = true;
            winner = players.get(1 - turnIndex).getName();
            return "Time's up! " + winner + " wins.";
        }
        if (!board.place(x, y, current.getSymbol())) {
            current.startTurn();
            return "Invalid move.";
        }
        // ar trb sa implementez aici verificarea pt win
        current.startTurn();
        turnIndex = 1 - turnIndex;
        return "Move accepted.";
    }

    public Board getBoard() {
        return board;
    }

    public boolean isFinished() {
        return finished;
    }

    public String getWinner() {
        return winner;
    }
}
