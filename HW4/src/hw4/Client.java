/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hw4;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.sql.*;
import java.util.*;
/**
 *
 * @author kaitlynhuynh
 */
public class Client extends Thread {
    Socket client;
    Client(Socket newClient) {
        client = newClient;
    }
    public void run() {
        try {
            Scanner sin = new Scanner(client.getInputStream());
            PrintStream sout = new PrintStream(client.getOutputStream());
            String line = sin.nextLine();
            line = line.trim();
            boolean cont = true;
            while (sin.hasNext() && cont) {
                if (line.equalsIgnoreCase("EXIT")) {
                    cont = false;
                } else {
                    sout.print(line + "\r\n");
                }
            }
            client.close();
        } catch (Exception e) {}
    }
}
