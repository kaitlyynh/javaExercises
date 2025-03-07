/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package colorfulwindow;
import java.util.*;
import java.awt.*;

import javax.swing.*;

/**
 *
 * @author kaitlynhuynh
 */
public class ColorfulWindow {

    /**
     * @param args the command line arguments
     */
    private JButton[] buttons;
    private int numOfButtons;
    private boolean[] changeColor;
    public static void main(String[] args) {
        System.out.println("Main started");
        // Create an instance of ColorfulWindow object
        ColorfulWindow cw = new ColorfulWindow();
    }
    
    public ColorfulWindow() {
        // Initialize attributes and button array
        numOfButtons = 8; 
        buttons = new JButton[numOfButtons];
        changeColor = new boolean[numOfButtons];
        for (int i = 0; i < numOfButtons; i++) {    
            changeColor[i] = true;
        }
        // Create instances of a frame and panel
        JFrame jf = new JFrame("HW3 Colorful Window");
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                buttons[i].addActionListener(new ColorfulWindowButtonActionListener());
                colorR = rand.nextInt(colorMax - colorMin + 1);
                colorG = rand.nextInt(colorMax - colorMin + 1);
                colorB = rand.nextInt(colorMax - colorMin + 1);
                buttonColor = new Color(colorR, colorG, colorB);
                buttons[i].setBackground(buttonColor);
                jp.add(buttons[i]);
                System.out.println("Created JButton at position " + i);
            }
        }
        // Create a thread instance and activate it
        Thread setColors = new SetColor();
        setColors.start();
    }
    class ColorfulWindowButtonActionListener implements ActionListener {
        @Override 
        public void actionPerformed(ActionEvent e) {
            // Iterate through array to modify colors
            for (int i = 0; i < numOfButtons; i++) {
                if (e.getSource() != buttons[i]) { // Button was not pressed, do nothing
                    System.out.println("Button " + i + " continue changing color");
                } else { // Button was pressed, toggle color changing
                    changeColor[i] = !changeColor[i]; // toggle on toggle off
                    System.out.println("Action triggered, button " + i + " was pressed, toggle color changing");
                }
            }
        }
    }
    class SetColor extends Thread {
        @Override
        public void run() {
            Random rand = new Random();
            int colorMax = 255;
            int colorMin = 0;
            while (true) {
                try {
                    // Freeze for 1 second before changing colors 
                    Thread.sleep(1000);
                    for (int i = 0; i < numOfButtons; i++) {
                        // Change color if color changing is toggled
                        if (changeColor[i]) {
                            int aColorR = rand.nextInt(colorMax - colorMin + 1);
                            int aColorG = rand.nextInt(colorMax - colorMin + 1);
                            int aColorB = rand.nextInt(colorMax - colorMin + 1);
                            Color aButtonColor = new Color(aColorR, aColorG, aColorB);
                            buttons[i].setBackground(aButtonColor);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
