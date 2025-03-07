// A simple Client Server Protocol .. Client for Echo Server

package networkclient;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.*;
import java.awt.event.*;

public class NetworkClient {
    static private JTextArea messageArea;
    static private JTextArea messageArea2;
    static private JTextField messageField;
    static private JTextField messageField2;
    static private JButton sendButton;
    private Socket socket;
    static private String serverAddress;
    private static String username1;
    private static String password1;
    // added
    static PrintWriter os;
    // added
    
    static class ChatGUI {
        
        public ChatGUI() {
            // Create a main JFrame
            JFrame jf = new JFrame("Chat Client");
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jf.setSize(400, 300);
            
            // Create a JPanel to put on the main JFrame
            JPanel jp = new JPanel();
            // It will have 2 rows, one on the top and one on the bottom
            jp.setLayout(new GridLayout(2,1));
        
            // Storing contents of what the user typed in
            JPanel top = new JPanel();
            messageArea = new JTextArea(15, 30);
            messageArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(messageArea);
            top.add(scrollPane);
            jp.add(scrollPane, BorderLayout.CENTER);

            // Panel for holding the input field and send button
            JPanel inputPanel = new JPanel();
            messageField = new JTextField(20);
            sendButton = new JButton("Send a message");
            inputPanel.add(messageField);
            inputPanel.add(sendButton);
            jp.add(inputPanel, BorderLayout.SOUTH);
            jf.add(jp);
            sendButton.addActionListener(new ButtonListener());
            messageArea.append("Enter Data to echo Server ( Enter QUIT to end):\n");
        
        
            jf.setVisible(true);
            Socket s;
            String line;
            BufferedReader toRead = null;
            BufferedReader toFetch = null;
            os=null;
            serverAddress = JOptionPane.showInputDialog("Enter server address:");
            username1 = JOptionPane.showInputDialog("Enter username:");
            password1 = JOptionPane.showInputDialog("Enter password:");
        // Connect
        try {
            s =new Socket(serverAddress, 5190); // 
            os = new PrintWriter(s.getOutputStream());
            toRead = new BufferedReader(new InputStreamReader(System.in));
            toFetch=new BufferedReader(new InputStreamReader(s.getInputStream()));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        String response;

        try {
            os.println(username1);
            System.out.println("SENT" + username1);
            os.println(password1);
            line = toRead.readLine();
            if (line == "500 INVALID") { // Invalid, close connection
               toFetch.close();
               os.close();
               toRead.close();
               s.close();
               System.out.println("Connection Closed. Code 500, failed to connect.");
               return;
            }
            System.out.println("Here");
            line = messageField.getText().trim();
            while(!line.equals("QUIT")){
            if (line.equals("") || line.equals(null)) {
                continue;
            } 
            os.println(line);
            os.flush();
            line = messageField.getText().trim();
        }
            
        // Send what a client types to the server side
        os.println(line);
        os.flush();
        response = toFetch.readLine();
//        System.out.println("Server Response : "+ response); // For testing purposes
        line = toRead.readLine();
        
    }
    catch(IOException e){
        System.out.println("Closing the conenction");
        return;
    }

    }
    public void sendMessage(String username, String msg) {
        String msg2 = username + ": " + msg + '\n';
        messageArea.append(msg2);
        System.out.println("Sent out!");
        os.println(msg2);
    }
    public class ButtonListener implements ActionListener {
    @Override 
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == sendButton) {
                sendMessage(username1, messageField.getText());
                messageField.setText("");

            }
        }
        }
    }

    
public static void main(String args[]) throws IOException{
   
    ChatGUI cg = new ChatGUI(); // Make an instance of the ChatGUI

}
 
}