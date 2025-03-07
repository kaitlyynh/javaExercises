
package myclient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyClient {
public static void main(String args[]){
    Socket s= null;
    ServerSocket ss= null;
    System.out.println("Server is listening");
    try{
        ss = new ServerSocket(5190); // Try to establish connection
    }
    catch(IOException e) {
        e.printStackTrace();
        System.out.println("Error with server connection.");
    }
    while(true){
        try {
            // Connect to MySQL database
            String url = "jdbc:mysql://localhost/" + "user_info";
            Connection connection = java.sql.DriverManager.getConnection(url, "root", "2003");
            // Connect to MySQL database
            s= ss.accept();
            ThreadForServer t =new ThreadForServer(s, connection);
            t.start();
        }
        catch (SQLException ex) {
            System.out.println("Connection failed: " + ex);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Connection had an error");
        }
    }

}

}

class ThreadForServer extends Thread{  

    String line = null;
    BufferedReader is = null;
    PrintWriter os = null;
    Socket s = null;
    Connection c = null;

    public ThreadForServer(Socket s, Connection c){
        s = s;
        c = c;
    }

    public void run() {
    try {
        os =new PrintWriter(s.getOutputStream());
        is = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }
    catch(IOException e){
        System.out.println("There was an IO error with the program.");
    }

    try {
        // Retrieve credentials
        String clientUsername = is.readLine(); // read inputted username
        String clientPassword = is.readLine(); // read inputted password
        System.out.println(clientUsername + ' ' + clientPassword);
        // Retrieve credentials
        // Authenticate user against database
        String usersTable = "users";
        String loginsTable = "logins";
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM " + usersTable);
        boolean match = false;
        while (rs.next()) {
            String usernameOfEntry = rs.getString("username");
            String passwordOfEntry = rs.getString("password");
            if (usernameOfEntry.equals(clientUsername) && passwordOfEntry.equals(clientPassword)) {
                match = true; 
                String queryString = "INSERT INTO " + loginsTable + " VALUES (?, ?)";
                PreparedStatement ps = c.prepareStatement(queryString);
                ps.setString(1, clientUsername);
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date());
                ps.setTimestamp(2, Timestamp.valueOf(timeStamp));
                ps.executeUpdate(); // Uncomment when you want to add to table
                break;
            }
        }
        if (!match) { // User credentials are not correct (or not in the database)
            os.println("500 Invalid");
            os.flush();
            s.close();
            is.close();
            os.close();
            return;
        }
        // Authenticate user against databse
        os.println("200 Valid");
        os.flush();
        line=is.readLine(); 
        while(line.compareTo("QUIT")!=0){
            if (!line.equals(null)) {
                line = clientUsername + ": " + line;
                os.println(line);
                os.flush();
                line=is.readLine();
            }
            // For testing, to print out what the username and password receieved was
            line = clientUsername + ": " + line;
            os.println(line);
            os.flush();
            System.out.println(clientUsername + ": " + line);
            line=is.readLine();
        }   
    } 
    catch (IOException e) {
        System.out.println("IO Error");
    }
    catch (SQLException ex) {
            System.out.println("Connection failed: " + ex);
    }
    s.close();
    os.close();
    is.close();
}