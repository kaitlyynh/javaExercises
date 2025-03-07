/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cs3913spring2024collections;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
/**
 *
 * @author dkatz
 */
public class CS3913Spring2024Collections {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Producer> pal = new ArrayList<Producer>();
        ArrayList cal = new ArrayList();
        
        
        Buffer b = new Buffer();
        for (int i=0; i<10; i++){
            pal.add(new Producer(b));
        }
        for (int i=0; i<10; i++){
            cal.add(new Consumer(b));
        }
        for (Producer p : pal)
            p.start();
        /*for (int i = 0; i<cal.size(); i++){
            Consumer c = (Consumer) cal.get(i);
            c.start();
        }
        */
        Iterator<Consumer> ic = cal.iterator();
        while (ic.hasNext()){
            Consumer c = ic.next();
            c.start();
        }
    }
    
}

class Producer extends Thread{
    Buffer buff;
    Producer(Buffer newBuff){buff = newBuff;}
    public void run(){
        Random r = new Random();
        while(true){
            int rand = r.nextInt();
            try{
                buff.addToBuffer(rand);
            }
            catch(Exception e){
                System.out.println("Producer Error: ("+e.toString()+")Buffer length="+buff.size);
                System.exit(1);
            }
        }
    }
}
class Consumer extends Thread{
    Buffer buff;
    Consumer(Buffer newBuff){buff = newBuff;}
    public void run(){
        while(true){
            try{
                int rand = buff.getFromBuffer();
                System.out.println(rand);
            }
            catch(Exception e){
                System.out.println("Consumer Error: ("+e.toString()+")Buffer length="+buff.size);
                System.exit(1);
            }
        }
    }
}

class Buffer{
    int data[];
    int size;
    Buffer(){ data =new int[500]; size=0;}
    void addToBuffer(int item){
        boolean done = false;
        while(!done){
            try{
                Thread.currentThread().sleep(50);
            }catch(InterruptedException e){}
            synchronized(this){
                if (size<data.length){
                   data[size++]=item;
                   done = true;
                }
            }
        }
    }
    int getFromBuffer(){
        boolean done = false;
        int item=0;
        while (!done){
            try{
                Thread.currentThread().sleep(50);
            }catch(InterruptedException e){}
            synchronized(this){
                if (size>0){
                    item = data[--size];
                    done=true;
                }
            }
        }
        return item;
    }
    int getSize(){return size;}
}