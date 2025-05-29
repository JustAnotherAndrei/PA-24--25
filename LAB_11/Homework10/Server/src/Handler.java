import game.HexGame;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class Handler implements Runnable {
    private static final Map<String, HexGame> games = new ConcurrentHashMap<>();
    private final Socket socket;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("Welcome to Hex!");
            String currentGameId = null;
            String playerName = null;

            String line;
            while ((line = in.readLine()) != null) {
                String[] parts = line.split(" ");
                switch (parts[0]) {
                    case "create": {
                        currentGameId = parts[1];
                        int size = Integer.parseInt(parts[2]);
                        long time = Long.parseLong(parts[3]);
                        games.put(currentGameId, new HexGame(currentGameId, size, time));
                        out.println("Game " + currentGameId + " created.");
                        break;
                    }
                    case "join": {
                        currentGameId = parts[1];
                        playerName = parts[2];
                        HexGame game = games.get(currentGameId);
                        if (game != null && game.addPlayer(playerName)) {
                            out.println("Joined game " + currentGameId);
                        } else {
                            out.println("Failed to join.");
                        }
                        break;
                    }
                    case "move": {
                        int x = Integer.parseInt(parts[1]);
                        int y = Integer.parseInt(parts[2]);
                        HexGame game = games.get(currentGameId);
                        String res = game.submitMove(playerName, x, y);
                        out.println(res);
                        game.getBoard().display();
                        break;
                    }
                    default:
                        out.println("Unknown command.");
                }
                if (currentGameId != null) {
                    HexGame game = games.get(currentGameId);
                    if (game != null && game.isFinished()) {
                        out.println("Game over! Winner: " + game.getWinner());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
