import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 1234;

        try (
                Socket socket = new Socket(host, port);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Connected to the server. Type your commands:");

            while (true) {
                String command = scanner.nextLine();
                if (command.equalsIgnoreCase("exit")) {
                    System.out.println("Client exited.");
                    break;
                }

                out.println(command);
                String response = in.readLine();
                System.out.println("Server: " + response);
            }

        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}
