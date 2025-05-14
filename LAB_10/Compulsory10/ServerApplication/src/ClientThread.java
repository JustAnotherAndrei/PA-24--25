import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    private final Socket socket;
    private final GameServer server;

    public ClientThread(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String request;
            while ((request = in.readLine()) != null) {
                System.out.println("Received from client: " + request);

                if (request.equalsIgnoreCase("stop")) {
                    out.println("Server stopped");
                    server.stopServer(); // stops the whole server
                    break;
                } else {
                    out.println("Server received the request: " + request);
                }
            }
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                System.err.println("Error closing client socket: " + ex.getMessage());
            }
        }
    }
}
