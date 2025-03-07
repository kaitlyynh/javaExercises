package chatgui;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.*;


public class ChatGUI extends JFrame {

    private JTextArea messageArea;
    private JTextField messageField;
    private JButton sendButton;
    private Socket socket;
    private BufferedReader serverInput;
    private PrintWriter serverOutput;

    public ChatGUI() {
        super("Chat Client");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Panel for holding the chat messages
        messageArea = new JTextArea(15, 30);
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        add(scrollPane, BorderLayout.CENTER);

        // Panel for holding the input field and send button
        JPanel inputPanel = new JPanel();
        messageField = new JTextField(20);
        sendButton = new JButton("SEND");
        inputPanel.add(messageField);
        inputPanel.add(sendButton);
        add(inputPanel, BorderLayout.SOUTH);

        // Event listener for send button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        setVisible(true);

        // Connect to the server
        String serverAddress = JOptionPane.showInputDialog("Enter server address:");
        String username = JOptionPane.showInputDialog("Enter username:");
        String password = JOptionPane.showInputDialog("Enter password:");

        try {
            socket = new Socket(serverAddress, 5190);
            serverOutput = new PrintWriter(socket.getOutputStream(), true);
            serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverOutput.println(username);
            serverOutput.println(password);
        } catch (IOException e) {
            showMessage("Error connecting to server: " + e.getMessage());
        }

        // Start a thread to receive messages from the server
        new Thread(new Runnable() {
            @Override
            public void run() {
                receiveMessages();
            }
        }).start();
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            serverOutput.println(message);
            messageField.setText("");
        }
    }

    private void receiveMessages() {
        try {
            String message;
            while ((message = serverInput.readLine()) != null) {
                showMessage(message);
            }
        } catch (IOException e) {
            showMessage("Error receiving message from server: " + e.getMessage());
        }
    }

    private void showMessage(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                messageArea.append(message + "\n");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChatGUI();
            }
        });
    }
}
