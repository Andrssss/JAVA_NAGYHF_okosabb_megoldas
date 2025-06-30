package org.example.Entities;

import java.awt.*;

public class Wolf extends Animal implements Runnable {
    public void start() {
        thread = new Thread(this);
        thread.start();
    }
    public void stopRunning() {
        running = false;
        if (thread != null) {
            thread.interrupt();  // Megszakítja a szálat, ha éppen alszik
        }
    }
    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // Amikor a szál megszakad, az InterruptedException dobódik
                // Ellenőrizni kell a running változót, hogy leállítsuk a szálat
                Thread.currentThread().interrupt();  // Visszaállítja az interrupted flag-et
                break;  // Kilépünk a ciklusból és a run metódusból, leállítva a szálat
            }
        }
    }


    public Wolf() {
        hely.random();
        eletben_van = true;
        cubeColor = new Color(255, 0, 0);
    }

    public Wolf(int x, int y) {
        if(x>XX) x = 495;
        if(x<0) x = 0;
        if(y>YY) y = 495;
        if(y<0) y = 0;

        eletben_van = true;
        hely.x = x;
        hely.y = y-30;
        cubeColor = new Color(255, 0, 0);
    }



    boolean running = true;
    private Thread thread;
}
