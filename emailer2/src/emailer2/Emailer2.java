/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package emailer2;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
/**
 *
 * @author kaitlynhuynh
 */
public class Emailer2 {
    private static String department;
    private static int deptCount;
    private JButton[] deptButtons; 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Emailer e = new Emailer();
    }
    
    public Emailer2() {
        String url = "jdbc:mysql://localhost/user_info"; 
        try {
            Connection connection = java.sql.DriverManager.getConnection(url, "root", "2003");
        } catch (SQLException e) {
            //
        }
        JFrame jf  = new JFrame("Emailer");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2)); 
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setBackground(Color.yellow); // might not show
        rightPanel.setBackground(Color.green);
        JPanel leftPanelUpper = new JPanel();
        JPanel leftPanelLower = new JPanel();
        leftPanel.setLayout(new GridLayout(2,1));  
        leftPanelUpper.setBackground(Color.magenta);
        leftPanelLower.setBackground(Color.cyan);
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        jf.add(mainPanel);
        jf.setSize(1000, 1000);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Creating a File object for directory
        File selectDept = new File("./src/emailer/Departments");
        System.out.println(selectDept.getAbsolutePath());
        //List of all files and directories
        String contents[] = selectDept.list();
        System.out.println("List of files and directories in the specified directory:");
        for(int i=0; i<contents.length; i++) {
           deptCount++;
           System.out.println(contents[i] + deptCount);
        }
        deptButtons = new JButton[deptCount];
        leftPanelUpper.setLayout(new GridLayout(deptCount,1)); 
        leftPanel.add(leftPanelUpper); 
        leftPanel.add(leftPanelLower);
        for (int i = 0; i < deptCount; i++) {
            deptButtons[i] = new JButton(contents[i]);
            System.out.println("Department Button: " + contents[i]);
            leftPanelUpper.add(deptButtons[i]);
            deptButtons[i].addActionListener(new DepartmentButtonListener());
        }
        jf.setVisible(true);
    }
    class DepartmentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < deptCount; i++) {
                if (e.getSource() == deptButtons[i]) {
                    System.out.println(deptButtons[i].getText() + " was pressed");
                    if (department == deptButtons[i].getText()) {
                        System.out.println(department + " unselected ");
                        department = null;         
                    } else {
                        department = deptButtons[i].getText();
                    }
                    
                }
            }
        }
    }
}
