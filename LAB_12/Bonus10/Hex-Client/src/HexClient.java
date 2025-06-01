import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * A simple console‐based Hex client. Connects to server at host:port,
 * then allows the user to type commands (“CREATE …”, “JOIN …”, “MOVE …”, “QUIT”).
 *
 * All communication is line‐oriented. This client:
 *   - Spawns one thread to read all server messages and print them to stdout.
 *   - In the main thread, reads user lines from stdin, sends them to server.
 */
public class HexClient {
    private final String serverHost;
    private final int serverPort;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public HexClient(String host, int port) {
        this.serverHost = host;
        this.serverPort = port;
    }

    public void start() {
        try {
            socket = new Socket(serverHost, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Thread to read from server and print to console
            Thread readerThread = new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        System.out.println("[SERVER] " + response);
                        if (response.startsWith("GOODBYE")) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Connection closed.");
                }
            });
            readerThread.start();

            // Main thread: read from stdin, send to server
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = userInput.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                out.println(line);
                if (line.equalsIgnoreCase("QUIT")) {
                    break;
                }
            }

            readerThread.join();
            socket.close();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;
        if (args.length >= 1) {
            host = args[0];
        }
        if (args.length >= 2) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Usage: java HexClient [host] [port]");
                System.exit(1);
            }
        }
        System.out.println("Connecting to Hex server at " + host + ":" + port + "...");
        HexClient client = new HexClient(host, port);
        client.start();
    }
}
