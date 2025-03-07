package emailer;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author kaitlynhuynh
 * Email: thejavareminder9000@gmail.com  
 * Password: javareminder9000 
 * App device password: hzkp axyz znkv kjuk
 */
public class Emailer {
    private static String department;
    private static int deptCount;
    private JButton[] deptButtons; 
    private boolean[] deptButtonsPressed;
    private DefaultTableModel table; //previously "model"
    private JPanel rightPanel; 
    private JPanel leftPanelLower;

    private static JTextField weekField;
    private static String path;
    private JButton fileButtons[];
    private static String currFile;
    private JButton sendEmail;
    
    private JTextArea messageTextArea;

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Emailer e = new Emailer();

    }
    
    public Emailer() {
        resetEmailStatus(); 
        setupUI(); 
        setupRightPanel(); 
        
    }

    
    private void setupUI(){
        path = "./src/emailer/Departments";

        JFrame jf  = new JFrame("Send Automated Shift");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout()); 
        JPanel leftPanel = new JPanel();
        rightPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.yellow); // might not show
        rightPanel.setBackground(Color.green);
        JPanel leftPanelUpper = new JPanel();
        leftPanelLower = new JPanel();
        JPanel leftPanelMiddle = new JPanel();
        leftPanel.setLayout(new GridLayout(2,1));  
        leftPanelUpper.setBackground(Color.lightGray);
        leftPanelLower.setBackground(Color.cyan);
        leftPanel.add(leftPanelUpper); 
        leftPanel.add(leftPanelLower); 
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        leftPanel.setPreferredSize(new Dimension(300, jf.getHeight()));
        jf.add(mainPanel);
        jf.setSize(1000, 1000);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Creating a File object for directory
        File selectDept = new File("./src/emailer/Departments");
        System.out.println(selectDept.getAbsolutePath());
        //List of all files and directories
        String contents[] = selectDept.list();
       // System.out.println("List of files and directories in the specified directory:");
        for(int i=0; i<contents.length; i++) {
           deptCount++;
        }
        //Set up button arrays
        deptButtonsPressed = new boolean[deptCount];
        deptButtons = new JButton[deptCount];
        leftPanelUpper.setLayout(new GridLayout(deptCount + 2,1));
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Departments"); 
        leftPanelUpper.setBorder(titledBorder);

        Color[] colors = {Color.LIGHT_GRAY.brighter(), Color.PINK.brighter(), Color.ORANGE};
        String[] symbols = {"Security","Healthcare", "Education"};
        
        // iternates through all the different department creates buttons, graphics and adds them to panel
        for (int i = 0; i < deptCount; i++) {
            deptButtons[i] = new JButton(contents[i]);
            System.out.println("Department Button: " + contents[i]);
            deptButtons[i].setBackground(Color.lightGray);
            deptButtons[i].setForeground(Color.BLACK); 
            deptButtons[i].setOpaque(true);
            deptButtons[i].setBorderPainted(true);
            // add the graphics for each of the departments 
            GraphicsButton button = new GraphicsButton(contents[i],symbols[i]);
            leftPanelUpper.add(deptButtons[i]);
            leftPanelUpper.add(button);
            // when a department is clicked on take action 
            deptButtons[i].addActionListener(new DepartmentButtonListener());
        }
        // Add file buttons dynamically to lower left panel
        File directory = new File(path);
        String[] files = directory.list();
        leftPanelLower.setLayout(new GridLayout(files.length + 1, 1));
        TitledBorder fileBorder = BorderFactory.createTitledBorder("Files"); 
        leftPanelLower.setBorder(fileBorder);
        // Add field for user to input week
        JLabel weekLabel = new JLabel("For the week of:");
        weekField = new JTextField();
        
        leftPanelUpper.add(weekLabel); // Align label to the top
        leftPanelUpper.add(weekField); // Align text field in the center
        // Action toolbar
        TitledBorder titledBorder2 = BorderFactory.createTitledBorder("Action toolbar"); 
        leftPanelLower.setBorder(titledBorder2);
        sendEmail = new JButton("Send Emails");
        sendEmail.addActionListener(new SendEmailActionListener()); // action listern for box selected 
        leftPanelUpper.add(sendEmail);
        leftPanelLower.add(new JLabel("Please select a department"));
        jf.setVisible(true);
    }
    private void updateLowerPanelWithFileButtons() {
        File directory = new File(path);
        String[] files = directory.list();
        leftPanelLower.removeAll();
        leftPanelLower.setLayout(new GridLayout(files.length + 1, 1));
        TitledBorder fileBorder = BorderFactory.createTitledBorder("Files"); 
        leftPanelLower.setBorder(fileBorder);
        fileButtons = new JButton[files.length];
        String labelContent = "Please select a matching file, there are " + files.length;
        if (weekField.getText() == null || weekField.getText().equals("")) {
            labelContent += "\nPlease enter a week within the schedule";
        }
        JTextArea textArea = new JTextArea(labelContent);
        textArea.setBackground(new Color(204, 229, 255));
        leftPanelLower.add(textArea);
        for (int i = 0; i < files.length; i++) {
            fileButtons[i] = new JButton(files[i]);
            fileButtons[i].addActionListener(new SelectFileButtonListener());
            leftPanelLower.add(fileButtons[i]);
        }
        
        leftPanelLower.revalidate();
        leftPanelLower.repaint();
    }
    private void updateLowerPanelWithMessage() {
        leftPanelLower.removeAll(); // Remove all components
        leftPanelLower.setLayout(new BorderLayout()); // Reset layout
        JLabel messageLabel = new JLabel("Select a department!");
        messageLabel.setBackground(Color.CYAN);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanelLower.add(messageLabel, BorderLayout.CENTER); // Add message label
        leftPanelLower.revalidate();
        leftPanelLower.repaint();
    }
    // panel for displaying email subscribers 
    private void setupRightPanel() {
            table = new DefaultTableModel(new Object[]{"Name", "Email", "Role", "Email Status", "Select"}, 0){ // creates a table 
            @Override
            // using the wildcard <?> to allow flexability for the type of data that can be returned (string, bool etc.) 
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    case 3:
                        return String.class;
                    case 4:
                        return Boolean.class; // to make sure to use checkboxes set emailstatus to boolean
                    default:
                        return Object.class;
                }
            }
            };
       JTable table = new JTable(this.table);
       JScrollPane scroll = new JScrollPane(table); 
       scroll.setBorder(BorderFactory.createTitledBorder("Select a Department"));
       rightPanel.add(scroll, BorderLayout.CENTER);
       
       // Add a JTextArea to display messages being constructed
        messageTextArea = new JTextArea();
        messageTextArea.setEditable(false); // Make it non-editable
        JScrollPane messageScrollPane = new JScrollPane(messageTextArea);
        messageScrollPane.setBorder(BorderFactory.createTitledBorder("Messages Being Constructed"));
        messageScrollPane.setPreferredSize(new Dimension(300, 400));
        rightPanel.add(messageScrollPane, BorderLayout.SOUTH);
    }
    private void sendEmails(){
        String msg;
        String[] fileContents;
        try {
            //iterate through table 
            for (int i = 0; i < table.getRowCount(); i++) {
            Boolean isSelected = (Boolean) table.getValueAt(i, 4); // check which ones are selected 
            if (Boolean.TRUE.equals(isSelected)) {
                String email = (String) table.getValueAt(i, 1); // gets the email address
                String name = (String) table.getValueAt(i, 0); // gets the name
                fileContents = fileInAList(currFile, department);
                msg = constructMsg(fileContents, weekField.getText(), name);
//                sendEmail(msg, email); 
                sendEmail(email); // Send the email 
                
            // Update the JTextArea with the constructed message
                String newMsg = messageTextArea.getText() + "To: " + email + "\n" + msg;
                String deliniator = "- - - - - - - - - - - - - - - - - - - -";
                newMsg += (deliniator + deliniator + "\n\n");
                messageTextArea.setText(newMsg);

                updateEmailStatus(email, "yes"); // update status in the database
                table.setValueAt("yes", i, 3); // update the table model
            }
            }
        } catch (Exception e) {
            System.err.println("Error sending emails: " + ex.getMessage());
        }
        
    }
    private void sendEmail(String email){
        System.out.println("Sending email to " + email);
    }
    private void updateEmailStatus(String email, String status) {
        String url = "jdbc:mysql://localhost/user_info"; 
//        String url2 = "jdbc:mysql://localhost:3306/users?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=America/New_York";
        try {
            Connection connection = java.sql.DriverManager.getConnection(url, "root", "2003");
//            Connection connection = DriverManager.getConnection(url2,"root","");
           String query =  "UPDATE users SET email_status = ? WHERE email = ?"; 
           PreparedStatement preparedS = connection.prepareStatement(query);
           preparedS.setString(1, status);
           preparedS.setString(2, email);
           preparedS.executeUpdate(); 
           connection.close();
        } catch (SQLException e) {
            System.err.println("Error sending emails: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void resetEmailStatus() {
        String url = "jdbc:mysql://localhost/user_info"; 
//        String url2 = "jdbc:mysql://localhost:3306/users?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=America/New_York";
        try {
            Connection connection = java.sql.DriverManager.getConnection(url, "root", "2003");
//            Connection connection = DriverManager.getConnection(url2,"root","");
           String query =  "UPDATE users SET email_status = 'no'"; 
           PreparedStatement preparedS = connection.prepareStatement(query);
           preparedS.executeUpdate(); 
           connection.close();
        } catch (SQLException e) {
            System.err.println("Error resetting email statuses: " + e.getMessage());
            e.printStackTrace();
        }
    }
   
    private void getUserdata(String department){
           String url = "jdbc:mysql://localhost/user_info"; 
//         String url2 = "jdbc:mysql://localhost:3306/users?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=America/New_York";
        try {
            Connection connection = java.sql.DriverManager.getConnection(url, "root", "2003");
//           Connection connection = DriverManager.getConnection(url2,"root","");
          // System.out.println("Connection to the database was successful.");
           String query = "SELECT * FROM users WHERE Department = ?"; 
           PreparedStatement preparedStatement = connection.prepareStatement(query);
           preparedStatement.setString(1, department); 
           ResultSet resultSet = preparedStatement.executeQuery(); // gets the user info requested 
           table.setRowCount(0);// clears the previous results 
           
             // read all data from query 
           while (resultSet.next()) {
              String name = resultSet.getString("first") + " " + resultSet.getString("last");
              String email = resultSet.getString("email");
              String role = resultSet.getString("roles");
              String emailS = resultSet.getString("email_status"); 
              Boolean status = "yes".equalsIgnoreCase(emailS); // convert emailstatus to Boolean here
              table.addRow(new Object[]{name, email, role, emailS, status});  // new row to model
           }
           // update the border title "department" subscribers 
           TitledBorder border = (TitledBorder) ((JScrollPane) rightPanel.getComponent(0)).getBorder();
           border.setTitle(department + " Subscribers");
           resultSet.close();
           preparedStatement.close();
           connection.close();
           
       }catch (SQLException e) {
           System.err.println("Connection failed: " + e.getMessage());
           e.printStackTrace();
       }
    }
    private String[] listOfFiles(String deptName) {
        // return a list of each file in a specific department folder
        String filePath = "src/emailer/Departments/"; // navigate to departments
        File fileObj = new File("src/emailer/Departments/");
        String[] allDepartments = fileObj.list(); // list all deparments (e.g. healthcare, education)
        File fileObjDept = new File(filePath + "/" + deptName + "/");
        String[] allFiles = fileObjDept.list();
        return allFiles;
    }

    private String[] fileInAList(String fileName, String deptName) {
        // given a file name, read the contents and put each line
        // as an element in a list of strings
        try {
            String filePath = "src/emailer/Departments/" + deptName + "/" + fileName;
            System.out.println(filePath);
            File inputFile = new File(filePath);
            FileReader fr = new FileReader(inputFile);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            java.util.List<String> list = new java.util.ArrayList<String>();
            while ((line = br.readLine()) != null) {
                System.out.println("Content: " + line);
                list.add(line);
            }
            String[] stringArr = list.toArray(new String[list.size()]);
            br.close();
            fr.close();
            return stringArr;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }
        return new String[0]; // empty list
    }
    private String constructMsg(String[] fileContents, String weekOf, String fullName) {
            String lineFullName;
            String[] personDataList;
            String personData;
            String greeting = "Hello " + fullName + "! Here are your shifts for the week of " + weekOf + ":\n";
            int weekOfPosition = 1;
            boolean weekFound = false;
            String msg = greeting;
            String[] fileHeaderList = fileContents[1].split(",");
            System.out.println("fileHeader: "+ fileContents[1]);
            
            for (int i = 1; i < fileHeaderList.length; i++) {
                if (fileHeaderList[i].equals(weekOf)) {
                    weekOfPosition = i;
                    weekFound = true;
                    break;
                }
            }
            if (weekFound == false) { // week was not in selected schedule
                String invalidGreeting = "\nNOTIFICATION FROM TheJavaReminder9000!\n";
                String invalidMsg = "The email attempt failed to send.\n"
                        + "The week entered was not found in this schedule\n"
                        + "Please enter a valid week!\n";
                return invalidGreeting + invalidMsg;
            }
            int personIdx = 1;
            int counter = 0;
            for (int i = 1; i < fileContents.length; i++) {
                personData = fileContents[i];
                System.out.println("person data: " + personData);
                personDataList = fileContents[i].split(",");
                if (personDataList[0].equals(fullName)) {
                    personIdx = 1;
                    while (weekOfPosition + counter < fileHeaderList.length && counter < 7) {
                        msg += "\t" + personDataList[weekOfPosition + counter] + 
                                " shift on " + fileHeaderList[weekOfPosition + counter] + "\n";
                        counter++;
                    }
                }
            }
            if (msg.equals(greeting)) { // they had no shift
                msg += "\t No shifts for the week of " + weekOf + "\n";
            }
            msg += "\nThank you. \nHave a good day.\n";
            System.out.println("Msg: \n" + msg);
            
        return msg;
    }
    
    private static void sendEmail(String msg, String receiver) throws Exception {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        String emailFrom = "thejavareminder9000@gmail.com"; // tester
        String emailFromPassword = "hzkp axyz znkv kjuk"; // tester secret key
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailFrom, emailFromPassword);
            }
        });
        
        Message message = prepareMessage(session, emailFrom, receiver, msg);
        
        try {
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Email failed to send.");
        }
        System.out.println("Email sent: " + msg);
    }
    private static Message prepareMessage(Session session, String emailFrom, String receiver, String msg) 
            throws Exception {
        try {
            
            Message message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress(emailFrom));
            
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject("Reminder: You have a shift coming up!");
            message.setText(msg);
            
            return message;
        } catch (Exception e) {
            System.out.println("Error with prepareMessage");
            e.printStackTrace();
        }
        return null;
    }
    private void setAllToFalse() {
        // set all buttons to an unpressed state, and remove their colors
        for (int i = 0; i < deptCount; i++) {
            deptButtonsPressed[i] = false;
            deptButtons[i].setBackground(null);
        }
    }
    class DepartmentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < deptCount; i++) {
                if (e.getSource() == deptButtons[i]) { // selected
                    if (deptButtonsPressed[i]) { // it was already selected
                        System.out.println(deptButtons[i].getText() + " unselected ");
                        setAllToFalse();
                        department = null;  
                        updateLowerPanelWithMessage();
                    } else { // it was not pressed previously
                        System.out.println(deptButtons[i].getText() + " selected ");
                        department = deptButtons[i].getText();
                        setAllToFalse();
                        getUserdata(department); 
                        deptButtonsPressed[i] = true;
                        path = "./src/emailer/Departments/" + department;
                        updateLowerPanelWithFileButtons();
                        deptButtons[i].setBackground(Color.GREEN);
                    }
                }
            }
        }
    }  
    class SelectFileButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (fileButtons.length != 0) {
                for (int i = 0; i < fileButtons.length; i++) {
                    if (e.getSource() == fileButtons[i]) {
                        currFile = fileButtons[i].getText();
                        path = "./src/emailer/Departments/" + department + "/" + currFile;
                    }
                }
            }  
        }  
    }
    class SendEmailActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (department != null && weekField.getText() != null) {
                sendEmails();
                System.out.println("Requirements satisfied!");
            } else {
                System.out.println("Requirements not satisfied!");              
            }
        }  
    }
}