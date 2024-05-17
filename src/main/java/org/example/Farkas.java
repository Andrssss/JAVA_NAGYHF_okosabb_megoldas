package org.example;
import java.awt.*;

public class Farkas extends Allat implements Runnable {


    boolean running = true;
    public void stopRunning() {
        this.running = false; // Set the running flag to false to stop the thread
    }

    @Override
    public void run() {
        // TODO -------------------------
        while (running) {
            //System.out.println("Farkas fut");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    Farkas() {
        hely.random();
        eletben_van = true;
        cubeColor = new Color(255, 0, 0);
    }

    Farkas(int x, int y) {
        if(x>XX) x = 495;
        if(x<0) x = 0;
        if(y>YY) y = 495;
        if(x<0) y = 495;

        eletben_van = true;
        hely.x = x;
        hely.y = y-30;
        cubeColor = new Color(255, 0, 0);
    }
}
