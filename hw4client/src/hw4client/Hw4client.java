/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hw4client;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.sql.*;
import java.sql.Connection;
import java.util.*;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author kaitlynhuynh
 */
public class Hw4client {

    /**
     * @param args the command line arguments
    */
    
    public static void main(String[] args) {
        // TODO code application logic here
        Client c = new Client("127.0.0.1", 5190, Socket("127.0.01", 5190));
        c.start();
//        System.out.println("Client created");

    }

}

class Client extends Thread {
    private String ip;
    private int portNo;
    private String username;
    private String password;
    private Socket clientSocket;
    Client(String ip_addr, int port_num, Socket clientSocket) {
        ip = ip_addr;
        portNo = port_num;
        clientSocket = clientSocket;
//        Scanner sin = new Scanner(System.in);
//        System.out.println("Enter a username: ");
//        username = sin.nextLine();
//        System.out.println("Enter a password: ");
//        password = sin.nextLine();
//        System.out.println("Credentials entered: " + username + " " + password);
    }
    public void run() {
//        Scanner sin = new Scanner(System.in);
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

