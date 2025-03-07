package dummyfile;
import java.io.*;
import java.net.*;
import java.util.*;
        
public class Dummyfile {
    public static void main(String args[]) {
        try {
            

            Socket s = new Socket( "10.18.240.178", 1234);
            PrintStream sout = new PrintStream(s.getOutputStream());
            System.out.println("Connected");
            Scanner sin = new Scanner(s.getInputStream());
            boolean cont = true;
            DataOutputStream out=new DataOutputStream(s.getOutputStream());
            String x = sin.nextLine().trim();
            int mynum = Integer.valueOf(x.substring(4, 7));
            System.out.println(mynum);
            System.out.println(x);
            while (cont && sin.hasNext()) {
                String line = sin.nextLine();
                System.out.println(line);
                line = line.trim();
                if (line.equalsIgnoreCase("EXIT")) {
                    cont = false;
                } else {
                    sout.print(line);

                }
            }
            System.out.println("DONE");
            s.close();
        } catch (IOException ex) {}
        
    }
}