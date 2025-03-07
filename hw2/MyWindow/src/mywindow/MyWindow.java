

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mywindow;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author kaitlynhuynh
 * kh3599@nyu.edu   N15825875
 */
public class MyWindow {

    /**
     * @param args the command line arguments
     */
    private JButton[] buttons;
    private int numOfButtons;
    public static void main(String[] args) {
        System.out.println("Main started");
        // Create an instance of MyWindow object
        MyWindow mw = new MyWindow();
    }
    
    public MyWindow() {
        // Initialize attributes and button array
        numOfButtons = 8; 
        buttons = new JButton[numOfButtons];
        // Create instances of a frame and panel
        JFrame jf = new JFrame("HW2 Window");
        JPanel jp = new JPanel();
        // 4x2 window
        jp.setLayout(new GridLayout(4,2)); 
        jf.add(jp);
        jf.setSize(1000, 1000);
        int colorMax = 255;
        int colorMin = 0;
        int colorR;
        int colorG;
        int colorB;
        Color buttonColor;
        Random rand = new Random();
        // Populate array with randomly-colored button objects, bind action listener
        for (int i = 0; i < numOfButtons; i++) {
            if (buttons[i] == null) {
                buttons[i] = new JButton("JButton #" + i);
                buttons[i].setOpaque(true); // for color visibility
                buttons[i].setBorderPainted(false);
                buttons[i].setFocusPainted(false); // remove highlight on pressed-button
                buttons[i].addActionListener(new MyWindowButtonActionListener());
                colorR = rand.nextInt(colorMax - colorMin + 1);
                colorG = rand.nextInt(colorMax - colorMin + 1);
                colorB = rand.nextInt(colorMax - colorMin + 1);
                buttonColor = new Color(colorR, colorG, colorB);
                buttons[i].setBackground(buttonColor);
                System.out.println("Created JButton at position " + i);
            }
        }
        for (int i = 0; i < numOfButtons; i++) {
            System.out.println("Adding button #" + i + " to the JPanel");
            jp.add(buttons[i]);
        }
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    class MyWindowButtonActionListener implements ActionListener {
        @Override 
        public void actionPerformed(ActionEvent e) {
            Random rand = new Random();
            int colorMax = 255;
            int colorMin = 0;
            int colorR;
            int colorG;
            int colorB;
            Color buttonColor;
            // Iterate through array to modify colors
            for (int i = 0; i < numOfButtons; i++) {
                if (e.getSource() != buttons[i]) { // Button was not pressed, change color
                    System.out.println("Button " + i + " changing color");
                    colorR = rand.nextInt(colorMax - colorMin + 1);
                    colorG = rand.nextInt(colorMax - colorMin + 1);
                    colorB = rand.nextInt(colorMax - colorMin + 1);
                    // Generate random color and set
                    buttonColor = new Color(colorR,colorG,colorB);
                    buttons[i].setBackground(buttonColor);
                } else { // Button was pressed, do nothing
                    System.out.println("Action triggered, button " + i + " was pressed, won't change color");
                }
            }
        }
    }
};


