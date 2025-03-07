/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hw4;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author kaitlynhuynh
 */
public class Server {

    Server() {
        System.out.println("Attempt on Port 5190");
        int targetPort = 5190;
        try {
            ServerSocket ss = new ServerSocket(targetPort);
            while (true) {
                Socket client = ss.accept();
                System.out.println("Got connection from: " + client.getInetAddress().toString());
                System.out.println("Got connection on port 5190!");
            }
        } catch (Exception e) {}
        
    }
}
