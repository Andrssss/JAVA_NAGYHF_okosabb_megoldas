package org.example;

public class Main {
    public static void main(String[] args) {

        new WaitingFrame(1);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new WaitingFrame(2);
    }
}