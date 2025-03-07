
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package analogclockhw;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.text.SimpleDateFormat;

/**
 *
 * @author kaitlynhuynh
 */
public class AnalogClockHW extends JPanel implements Runnable {

    int xcenter = 250;
    int ycenter = 250;
    SimpleDateFormat timeVar = new SimpleDateFormat("s", Locale.getDefault());
    Date now;
    int prevxhour = 0;
    int prevyhour = 0;
    int prevxmin = 0;
    int prevymin = 0;
    int prevxsec = 0;
    int prevysec = 0;
    Thread clockthread;
    public static void main(String args[]) {
        JFrame jf = new JFrame();
        jf.setSize(500,500);
        AnalogClockHW clock = new AnalogClockHW();
        jf.add(clock);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        jf.setVisible(true);
        clock.start();
    }
    
    private void draw(Graphics g) {
        // Draw clock shape of white
        g.setColor(Color.white);
        g.fillOval(xcenter - 150, ycenter - 150, 300, 300);
        //Draw clock details
        g.setColor(Color.black);
        g.drawString("3", xcenter + 135, ycenter);
        g.drawString("6", xcenter, ycenter + 145);
        g.drawString("9", xcenter - 145, ycenter);
        g.drawString("12", xcenter - 5, ycenter - 135);
    }

    public void paint(Graphics g) {
        // To draw the hands on the clock
        int xhour;
        int yhour;
        int xmin;
        int ymin;
        int xsec;
        int ysec;
        int hour;
        int min;
        int sec;
        now = new Date();
        timeVar.applyPattern("h");
        hour = Integer.parseInt(timeVar.format(now));
        timeVar.applyPattern("m");
        min = Integer.parseInt(timeVar.format(now));
        timeVar.applyPattern("s");
        sec = Integer.parseInt(timeVar.format(now));
        
        // Equations to find rotation amount around the center
        xsec = (int) ((Math.cos((3.14f * sec) / 30 - 3.14f / 2) * 120) + xcenter + 5);
        ysec = (int) ((Math.sin((3.14f * sec) / 30 - 3.14f / 2) * 120) + ycenter + 5);
        xmin = (int) ((Math.cos((3.14f * min) / 30 - 3.14f / 2)) * 100 + xcenter);
        ymin = (int) ((Math.sin((3.14f * min) / 30 - 3.14f / 2) * 100) + ycenter);
        xhour = (int) ((Math.cos(((hour * 30) + min / 2) * 3.14f / 180 - (3.14f / 2)) * 80) + xcenter);
        yhour = (int) ((Math.sin(((hour * 30) + min / 2) * 3.14f / 180 - (3.14f / 2)) * 80) + ycenter);
        // Store previous "states" as reference points
        prevxsec = xsec;
        prevysec = ysec;
        prevxmin = xmin;
        prevymin = ymin;
        prevxhour = xhour;
        prevyhour = yhour;
        draw(g);
        g.setColor(Color.black);
        g.drawLine(xcenter, ycenter, xsec, ysec);
        g.setColor(Color.red);
        g.drawLine(xcenter, ycenter, xmin, ymin);
        g.setColor(Color.blue);
        g.drawLine(xcenter, ycenter, xhour, yhour);       
    }
    public void run() {
        while (clockthread != null) {
            try {
                Thread.sleep(100);
//                repaint(); weird behavior
            } catch (InterruptedException e) {
                System.out.println("Cought Interrupted Exception");
                e.printStackTrace();
            }
            repaint();
        }
    }
    public void start() {
        if (clockthread == null) {
            clockthread = new Thread(this);
            clockthread.start();
        }
    }

}
