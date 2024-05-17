package org.example;

public class Main {
    public static void main(String[] args) {
        // szerver
        Allat.balra();

        new WaitingFrame(1);
        new WaitingFrame(2);
        //Object lock = new Object();Field f1 = new Field(1,true,lock);f1.run();
        //try {Thread.sleep(500);} catch (InterruptedException e) {throw new RuntimeException(e);}
        //f1.SetGame_over();




        while(true){
            int activeThreads = Thread.activeCount();
            System.out.println("Jelenleg futó szálak : "+ activeThreads);
            if(activeThreads == 2) break ;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}