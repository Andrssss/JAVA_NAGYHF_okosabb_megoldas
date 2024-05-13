package org.example;

public class Main {
    public static void main(String[] args) {

        new WaitingFrame(1);



        new WaitingFrame(2);


        /*while(true){
            int activeThreads = Thread.activeCount();
            System.out.println("Jelenleg futó szálak száma: " + activeThreads);
            // main
            // server socket
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }*/
    }
}