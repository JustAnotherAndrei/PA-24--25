import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * A thread that handles one client connection. Reads ASCII‐text commands
 * from the client, interacts with GameManager, and writes back responses.
 *
 * Protocol (client→server):
 *   CREATE <size> [AI]
 *   JOIN <gameId>
 *   MOVE <row> <col>
 *   QUIT
 *
 * Server→client responses:
 *   GAME_CREATED <gameId>
 *   JOINED <gameId> <color>
 *   WAITING
 *   OPPONENT_JOINED
 *   MOVE_ACCEPTED <r> <c>
 *   OPPONENT_MOVED <r> <c>
 *   INVALID_MOVE <reason>
 *   WIN <color>
 *   ERROR <message>
 *   GOODBYE
 */
public class PlayerHandler extends Thread {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private Integer joinedGameId = null;
    private Game.Color myColor = null; // BLUE or RED

    public PlayerHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    /** Utility: send one line to this client. */
    public void sendMessage(String msg) {
        out.println(msg);
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                if (tokens.length == 0) {
                    continue;
                }
                String cmd = tokens[0].toUpperCase();

                switch (cmd) {
                    case "CREATE":
                        handleCreate(tokens);
                        break;
                    case "JOIN":
                        handleJoin(tokens);
                        break;
                    case "MOVE":
                        handleMove(tokens);
                        break;
                    case "QUIT":
                        handleQuit();
                        return; // end thread
                    default:
                        sendMessage("ERROR Unknown command");
                }
            }
        } catch (IOException e) {
            // Client disconnected abruptly
            System.err.println("Connection error: " + e.getMessage());
        } finally {
            cleanup();
        }
    }

    /** Handle: CREATE <size> [AI] */
    private void handleCreate(String[] tokens) {
        if (joinedGameId != null) {
            sendMessage("ERROR Already in a game");
            return;
        }
        if (tokens.length < 2) {
            sendMessage("ERROR Usage: CREATE <size> [AI]");
            return;
        }
        int size;
        try {
            size = Integer.parseInt(tokens[1]);
        } catch (NumberFormatException e) {
            sendMessage("ERROR Invalid board size");
            return;
        }
        if (size < 3 || size > 25) {
            sendMessage("ERROR Board size must be between 3 and 25");
            return;
        }
        boolean wantAI = false;
        if (tokens.length >= 3 && tokens[2].equalsIgnoreCase("AI")) {
            wantAI = true;
        }
        // Create the game:
        GameManager gm = GameManager.getInstance();
        int gameId = gm.createGame(size, wantAI, this);
        joinedGameId = gameId;
        myColor = Game.Color.BLUE; // creator is always BLUE
        sendMessage("GAME_CREATED " + gameId);

        if (wantAI) {
            // Immediately “join” as RED slot is AI itself. Tell player they are joined.
            sendMessage("JOINED " + gameId + " BLUE");
            // No need to send WAITING or OPPONENT_JOINED; AI is there immediately
        } else {
            // Human-vs-human: tell the creator to wait for someone to join
            sendMessage("WAITING");
        }
    }

    /** Handle: JOIN <gameId> */
    private void handleJoin(String[] tokens) {
        if (joinedGameId != null) {
            sendMessage("ERROR Already in a game");
            return;
        }
        if (tokens.length < 2) {
            sendMessage("ERROR Usage: JOIN <gameId>");
            return;
        }
        int gameId;
        try {
            gameId = Integer.parseInt(tokens[1]);
        } catch (NumberFormatException e) {
            sendMessage("ERROR Invalid gameId");
            return;
        }
        GameManager gm = GameManager.getInstance();
        boolean ok = gm.joinGame(gameId, this);
        if (!ok) {
            sendMessage("ERROR Cannot join game " + gameId);
            return;
        }
        joinedGameId = gameId;
        myColor = Game.Color.RED; // joiner is always RED
        sendMessage("JOINED " + gameId + " RED");

        // Notify the BLUE player that opponent arrived:
        Game g = GameManager.getInstance().games.get(gameId);
        PlayerHandler blue = g.bluePlayer;
        if (blue != null) {
            blue.sendMessage("OPPONENT_JOINED");
        }
    }

    /** Handle: MOVE <row> <col> */
    private void handleMove(String[] tokens) {
        if (joinedGameId == null) {
            sendMessage("ERROR Not in any game");
            return;
        }
        if (tokens.length < 3) {
            sendMessage("ERROR Usage: MOVE <row> <col>");
            return;
        }
        int r, c;
        try {
            r = Integer.parseInt(tokens[1]);
            c = Integer.parseInt(tokens[2]);
        } catch (NumberFormatException e) {
            sendMessage("ERROR Invalid coordinates");
            return;
        }
        String response = GameManager.getInstance().forwardMove(joinedGameId, this, r, c);
        sendMessage(response);
        // If the move ended the game, both players (and AI) have been notified inside Game.makeMove()
    }

    /** Handle: QUIT */
    private void handleQuit() {
        sendMessage("GOODBYE");
        // After this we let run() exit and cleanup happen
    }

    /** If the client disconnects abruptly, or quits, remove them from any game. */
    private void cleanup() {
        try {
            if (joinedGameId != null && myColor == Game.Color.BLUE) {
                // If BLUE disconnects mid‐game, notify RED if human:
                Game g = GameManager.getInstance().games.get(joinedGameId);
                if (g != null && !g.isAI() && g.redPlayer != null) {
                    g.redPlayer.sendMessage("ERROR Opponent disconnected");
                }
            } else if (joinedGameId != null && myColor == Game.Color.RED) {
                // If RED disconnects in a human‐vs‐human game, notify BLUE
                Game g = GameManager.getInstance().games.get(joinedGameId);
                if (g != null && g.bluePlayer != null) {
                    g.bluePlayer.sendMessage("ERROR Opponent disconnected");
                }
            }
            socket.close();
        } catch (IOException ignored) { }
    }
}
