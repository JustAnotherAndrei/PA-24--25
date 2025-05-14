import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    private boolean running = true;
    private ServerSocket serverSocket;

    public GameServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (running) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                ClientThread clientThread = new ClientThread(clientSocket, this);
                clientThread.start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    public void stopServer() {
        running = false;
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing server: " + e.getMessage());
        }
        System.out.println("Server stopped.");
    }

    public static void main(String[] args) {
        int port = 1234; // default port
        new GameServer(port);
    }
}
