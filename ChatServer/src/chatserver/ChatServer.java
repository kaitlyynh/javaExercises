import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

package ChatServer; 

public class ChatServer {
    private ServerSocket serverSocket;
    private Connection dbConnection;
    private List<ClientHandler> connectedClients; // List to track connected clients

    public ChatServer() {
        connectedClients = new ArrayList<>(); // Initialize the list
        try {
            serverSocket = new ServerSocket(5190);
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost/chatdb", "username", "password");
            System.out.println("Chat Server is running on port 5190...");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                connectedClients.add(clientHandler); // Add new client to the list
                clientHandler.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Authenticate client
                boolean isAuthenticated = authenticateClient();

                if (isAuthenticated) {
                    out.println("200"); // Authentication successful
                    String inputLine;

                    // Log the connection
                    logConnection();

                    // Receive and broadcast messages
                    while ((inputLine = in.readLine()) != null) {
                        broadcastMessage(username + ": " + inputLine);
                    }
                } else {
                    out.println("500"); // Authentication failed
                    clientSocket.close();
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            } finally {
                connectedClients.remove(this); // Remove client from the list upon disconnect
            }
        }

        private boolean authenticateClient() throws IOException, SQLException {
            String[] credentials = in.readLine().split(":");
            String username = credentials[0];
            String password = credentials[1
