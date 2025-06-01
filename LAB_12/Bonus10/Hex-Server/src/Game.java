import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a single Hex game of size N×N. Manages:
 *  - the board state (0=empty, 1=BLUE, 2=RED)
 *  - whose turn it is (1=BLUE, 2=RED)
 *  - whether an AI is playing as RED (if isAI==true)
 *  - detecting valid moves
 *  - detecting wins via pathfinding
 *  - notifying both players (or player+AI) of moves and wins
 */
public class Game {
    public enum Color {
        BLUE(1), RED(2);
        private final int val;
        Color(int v) { val = v; }
        public int toInt() { return val; }
        public static Color fromInt(int v) {
            if (v == 1) return BLUE;
            else if (v == 2) return RED;
            else return null;
        }
    }

    private final int gameId;
    private final int size;
    private final int[][] board;
    private Color turn;             // whose turn it is now
    PlayerHandler bluePlayer; // the human playing BLUE
    PlayerHandler redPlayer;  // the human playing RED (null if AI)

    private final boolean isAI;     // if true, RED is AI
    private final AI aiPlayer;      // only non-null if isAI==true

    private boolean finished = false;

    /**
     * Create a new Game of boardSize×boardSize.
     * @param gameId unique game identifier
     * @param boardSize N
     * @param isAI if true, RED side is controlled by AI
     */
    public Game(int gameId, int boardSize, boolean isAI) {
        this.gameId = gameId;
        this.size = boardSize;
        this.board = new int[boardSize][boardSize];
        for (int[] row : board) {
            Arrays.fill(row, 0);
        }
        this.turn = Color.BLUE; // BLUE always starts
        this.isAI = isAI;
        this.aiPlayer = isAI ? new AI() : null;
        // bluePlayer is set immediately when creator connects
        // redPlayer will be set when second human joins, or left null if isAI
    }

    /** Set the BLUE player (creator). Called exactly once. */
    public void setBluePlayer(PlayerHandler p) {
        this.bluePlayer = p;
    }

    /** Set the RED player (joiner). Called once for human-vs-human games. */
    public void setRedPlayer(PlayerHandler p) {
        this.redPlayer = p;
    }

    /** Return true if both human players are present (or if isAI). */
    public synchronized boolean isReady() {
        if (isAI) {
            // As soon as bluePlayer is set, the AI is effectively “present.”
            return bluePlayer != null;
        } else {
            return (bluePlayer != null && redPlayer != null);
        }
    }

    /** Returns the integer ID of this game. */
    public int getGameId() {
        return gameId;
    }

    /** Returns the board size N. */
    public int getSize() {
        return size;
    }

    /** Returns true iff RED is AI. */
    public boolean isAI() {
        return isAI;
    }

    /**
     * Attempt to make a move at (row, col) by player `p`.
     * Returns a textual response to send back to that player:
     *  - if invalid: "INVALID_MOVE <reason>"
     *  - if valid: "MOVE_ACCEPTED <row> <col>" and also notify opponent(s)
     * If this completes a winning path, also notifies both sides: "WIN <color>"
     *
     * This method is synchronized so that two simultaneous move attempts cannot collude.
     */
    public synchronized String makeMove(PlayerHandler p, int row, int col) {
        if (finished) {
            return "ERROR Game already finished";
        }
        Color who = getColorOfPlayer(p);
        if (who == null) {
            return "ERROR You are not in this game";
        }
        if (who != turn) {
            return "INVALID_MOVE Not your turn";
        }
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return "INVALID_MOVE Coordinates out of bounds";
        }
        if (board[row][col] != 0) {
            return "INVALID_MOVE Cell already occupied";
        }

        // Place the stone
        board[row][col] = who.toInt();

        // Acknowledge to the mover
        String selfMsg = String.format("MOVE_ACCEPTED %d %d", row, col);
        // Notify the opponent:
        PlayerHandler opponent = (who == Color.BLUE ? getRedEndpoint() : bluePlayer);
        if (opponent != null) {
            opponent.sendMessage("OPPONENT_MOVED " + row + " " + col);
        }

        // Check for win:
        if (checkWin(who)) {
            finished = true;
            String winMsg = "WIN " + who.name();
            // Notify both sides (if RED is AI, we still notify BLUE; AI doesn’t need a socket message)
            bluePlayer.sendMessage(winMsg);
            if (redPlayer != null) {
                redPlayer.sendMessage(winMsg);
            }
            return selfMsg;
        }

        // Advance turn
        turn = (who == Color.BLUE ? Color.RED : Color.BLUE);

        // If now it’s AI’s turn, immediately let AI pick and play
        if (isAI && turn == Color.RED) {
            int[] aiMove = aiPlayer.nextMove(board);
            if (aiMove != null) {
                int r = aiMove[0], c = aiMove[1];
                board[r][c] = Color.RED.toInt();
                // Notify human (BLUE) that AI moved
                bluePlayer.sendMessage("OPPONENT_MOVED " + r + " " + c);
                // Check if AI’s move won the game
                if (checkWin(Color.RED)) {
                    finished = true;
                    String winMsg2 = "WIN RED";
                    bluePlayer.sendMessage(winMsg2);
                    // AI “side” doesn’t need a real socket to receive win
                } else {
                    // Back to BLUE’s turn
                    turn = Color.BLUE;
                }
            } else {
                // Board full; no move
                finished = true;
                bluePlayer.sendMessage("WIN DRAW");
            }
        }

        return selfMsg;
    }

    /** Return the PlayerHandler (or null) who represents RED in this game. */
    private PlayerHandler getRedEndpoint() {
        if (isAI) {
            return null; // AI has no socket
        } else {
            return redPlayer;
        }
    }

    /**
     * Map a PlayerHandler to its Color. Returns null if p is not in this game.
     */
    private Color getColorOfPlayer(PlayerHandler p) {
        if (p == null) return null;
        if (p.equals(bluePlayer)) return Color.BLUE;
        if (!isAI && p.equals(redPlayer)) return Color.RED;
        return null;
    }

    /**
     * Check if <color> has a connecting path from its two opposite sides.
     * Uses DFS over the N×N board. O(N^2) per call.
     */
    private boolean checkWin(Color color) {
        boolean[][] visited = new boolean[size][size];
        if (color == Color.BLUE) {
            // start from any BLUE stone on row=0 (top edge)
            for (int c = 0; c < size; c++) {
                if (board[0][c] == Color.BLUE.toInt()) {
                    if (dfs(0, c, color, visited)) {
                        return true;
                    }
                }
            }
        } else {
            // RED: start from any RED stone on col=0 (left edge)
            for (int r = 0; r < size; r++) {
                if (board[r][0] == Color.RED.toInt()) {
                    if (dfs(r, 0, color, visited)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Depth‐First Search from (r, c) over cells of the same color.
     * Returns true if we reach the opposite side (bottom edge for BLUE, right edge for RED).
     */
    private boolean dfs(int r, int c, Color color, boolean[][] visited) {
        if (visited[r][c]) return false;
        visited[r][c] = true;
        // Check if this cell is on the winning edge:
        if (color == Color.BLUE && r == size - 1) {
            return true;
        }
        if (color == Color.RED && c == size - 1) {
            return true;
        }
        // Visit all 6 neighbors in a Hex grid:
        int[][] deltas = { {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0} };
        for (int[] d : deltas) {
            int nr = r + d[0], nc = c + d[1];
            if (nr >= 0 && nr < size && nc >= 0 && nc < size) {
                if (!visited[nr][nc] && board[nr][nc] == color.toInt()) {
                    if (dfs(nr, nc, color, visited)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
