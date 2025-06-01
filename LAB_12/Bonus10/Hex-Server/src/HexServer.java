import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The server’s main entry point. Listens on port 12345 (hardcoded).
 * For each incoming connection, spins up a PlayerHandler thread.
 */
public class HexServer {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        System.out.println("Starting Hex server on port " + PORT + "...");
        try (ServerSocket listener = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = listener.accept();
                System.out.println("New connection from "
                        + clientSocket.getInetAddress().getHostAddress());
                try {
                    PlayerHandler handler = new PlayerHandler(clientSocket);
                    handler.start();
                } catch (IOException e) {
                    System.err.println("Failed to create handler: " + e.getMessage());
                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            System.err.println("Could not start server: " + e.getMessage());
        }
    }
}
