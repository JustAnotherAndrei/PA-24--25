import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Manages all active games on the server. Tracks:
 *   - gameId → Game instance
 *   - assigning numeric IDs
 *   - pairing join requests to existing games
 *
 * All public methods are synchronized for thread safety.
 */
public class GameManager {
    private static final GameManager instance = new GameManager();
    private final AtomicInteger nextGameId = new AtomicInteger(1);
    final Map<Integer, Game> games = new HashMap<>();

    private GameManager() { }

    /** Singleton accessor. */
    public static GameManager getInstance() {
        return instance;
    }

    /**
     * Create a new game of size N, optionally with AI. Returns the new gameId.
     * The caller (creator) should call game.setBluePlayer(...) immediately after.
     */
    public synchronized int createGame(int boardSize, boolean isAI, PlayerHandler creator) {
        int id = nextGameId.getAndIncrement();
        Game g = new Game(id, boardSize, isAI);
        g.setBluePlayer(creator);
        games.put(id, g);
        return id;
    }

    /**
     * Let a human client join an existing game as RED. If the game does not exist,
     * or already has two humans (and is not AI), returns false. Otherwise returns true.
     * The caller then should know to send "JOINED ..." to p, and notify both sides.
     */
    public synchronized boolean joinGame(int gameId, PlayerHandler p) {
        Game g = games.get(gameId);
        if (g == null) {
            return false;
        }
        if (g.isAI()) {
            // We created game with AI; no second human slot
            return false;
        }
        // If redPlayer is already present, cannot join
        if (g.isReady()) {
            return false;
        }
        // Attach as RED
        g.setRedPlayer(p);
        return true;
    }

    /**
     * Attempt to forward a MOVE request from a player p in gameId.
     * Returns whatever Game.makeMove(...) returns (e.g. "INVALID_MOVE ..." or "MOVE_ACCEPTED ...").
     * If game not found, returns "ERROR No such game".
     */
    public synchronized String forwardMove(int gameId, PlayerHandler p, int r, int c) {
        Game g = games.get(gameId);
        if (g == null) {
            return "ERROR No such game";
        }
        return g.makeMove(p, r, c);
    }
}
