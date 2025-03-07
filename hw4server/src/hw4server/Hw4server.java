/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hw4server;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.text.*;
/**
 *
 * @author kaitlynhuynh
 */
public class Hw4server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Hw4server my_server = new Hw4server();
        
        
    }
    public Hw4server() {
        try {
            // Connect to the MySQL database
            int portno = 5190;
            String user = "root";
            String password = "2003";
            String databaseName = "user_info";
            String tableName = "logins";
            System.out.println("HW 4 MAIN");
            String url = "jdbc:mysql://localhost/" + databaseName; // Port 3306 by default
            Connection connection = java.sql.DriverManager.getConnection(url, user, password);
            System.out.println("Connection successful.");
            // Connect to the MySQL database
            InputStreamReader inputStreamReader = null;
            OutputStreamWriter outputStreamWriter = null;
            BufferedReader bufferedReader = null;
            BufferedWriter bufferedWriter = null;
            // Open socket to listen
            ServerSocket ss = new ServerSocket(portno);
            boolean cont = true;
            while (cont == true) {
                System.out.println("Listening for client...");
                Socket client = ss.accept();  
                System.out.println("Client accepted");
                // access what client enters
                inputStreamReader = new InputStreamReader(client.getInputStream());
                // access what client receievers
                outputStreamWriter = new OutputStreamWriter(client.getOutputStream());
                // read contents of what client enters from input stream
                bufferedReader = new BufferedReader(inputStreamReader);
                // read contents of what client ...
                bufferedWriter = new BufferedWriter(outputStreamWriter);
//                String inputtedUsername = bufferedReader.readLine();
//                bufferedWriter.newLine();
//                bufferedWriter.flush();
//                String inputtedPassword = bufferedReader.readLine();
//                bufferedWriter.newLine();
//                bufferedWriter.flush();
//                System.out.println("Server reads: " + inputtedUsername + " " + inputtedPassword);
//                Statement s = connection.createStatement();
                // Threads
                Client localClient = new Client("127.0.0.1", portno);
                localClient.start();
                // Works
                while (true) {
//                
                    String msgFromClient = bufferedReader.readLine();
                    System.out.println( msgFromClient);
                    bufferedWriter.write("MSG Receieved.");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                // Works
                // Threads
//                String queryString = "INSERT INTO " + tableName + " VALUES (?, ?, ? ,?)";
//                PreparedStatement ps = connection.prepareStatement(queryString);
//                ps.setString(1, "127.0.0.1");
//                String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date());
//                ps.setTimestamp(2, Timestamp.valueOf(timeStamp));
//                ps.setString(3, inputtedUsername);
//                ps.setString(4, inputtedPassword);
                // Authenticate Log in
//                ResultSet rs = s.executeQuery("SELECT * FROM " + tableName);
                // Table is populated
//                boolean match = false;
//                while (rs.next()) {
//                    String usernameOfEntry = rs.getString("username");
//                    String passwordOfEntry = rs.getString("password");
//                     User inputted an exact match with the database
//                    if (usernameOfEntry.equals(inputtedUsername) && passwordOfEntry.equals(inputtedPassword)) {
//                        match = true;
//                        break;
//                    } 
//                    int code;
//                    if (match) {
//                        code = 200; 
//                        System.out.println("200 VALID " + inputtedUsername + " has joined.");
//                    } else {
//                        code = 500;
//                        System.out.println("500 INVALID " + inputtedUsername + " has been disconnected.");
//                        return;
//                }
//                }   
//                ps.executeUpdate(); 
//                s.executeQuery(query);
//                System.out.println("Query executed");
                    // Works
                    bufferedWriter.write("MSG Receieved.");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    if (msgFromClient.equalsIgnoreCase("EXIT")) {
                        cont = false;
                    }
            }
            }
            ss.close();
            inputStreamReader.close();
            outputStreamWriter.close();
            bufferedReader.close();
            bufferedWriter.close();
                    // Works
        } 

        catch (SQLException ex) {
            System.out.println("Connection failed: " + ex);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    // Client Handler 
    class Client extends Thread {
    private String ip;
    private int portNo;
    private String username;
    private String password;
    private Socket clientSocket;
    public Client(String ip_addr, int port_num, Socket clientSocket) {
        ip = ip_addr;
        portNo = port_num;
        clientSocket = clientSocket;
    }
    @Override
    public void run() {
        Scanner sin = new Scanner(System.in);
//        System.out.println("Enter a username: ");
//        username = sin.nextLine();
//        System.out.println("Enter a password: ");
//        password = sin.nextLine();
//        System.out.println("Credentials entered: " + username + " " + password);
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        
        try {
            socket = new Socket(ip, portNo);
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            
            Scanner scanner = new Scanner(System.in);
            // Authenticate
//            bufferedWriter.write(username);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
//            bufferedWriter.write(password);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
            // Authenticate
            
            boolean cont = true;
            while (cont == true) {
                String msgToSend = scanner.nextLine();
                bufferedWriter.write(msgToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                
                System.out.println("Server: " + bufferedReader.readLine());
                
                if (msgToSend.equalsIgnoreCase("EXIT")) {
                    cont = false;
                }
            }
            socket.close();
            inputStreamReader.close();
            outputStreamWriter.close();
            bufferedReader.close();
            bufferedWriter.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    // Client Handler
}




