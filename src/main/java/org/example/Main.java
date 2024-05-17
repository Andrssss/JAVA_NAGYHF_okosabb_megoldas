package org.example;

public class Main {
    public static void main(String[] args) {
        // szerver
        //new WaitingFrame(1);
        Object lock = new Object();
        //new Ending_Frame(1,2,1);
        new Field(1,true,lock);
        //new WaitingFrame(2);




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
        System.out.println("Main vege ;)");
    }
}