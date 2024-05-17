package org.example;
import java.awt.*;


public class Barany extends Allat implements Runnable{
    boolean running = true;

    @Override
    public void run() {
        // TODO -------------------------
        while (running) {
            //System.out.println("Barany fut");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }


        //randomMozgas();
    }


    public void stopRunning() {this.running = false; }




    Barany() {
        eletben_van = true;
        hely.random();
        cubeColor = new Color(255, 255, 255);
    }

    Barany(int x, int y) {
        if(x>XX) x = 495;
        if(x<0) x = 0;
        if(y>YY) y = 495;
        if(x<0) y = 495;

        eletben_van = true;
        hely.x = x;
        hely.y = y-30;
        cubeColor = new Color(255, 255, 255);
    }


    @Override
    void meghal() {
        stopRunning();
    }
}
