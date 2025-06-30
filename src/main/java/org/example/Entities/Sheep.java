package org.example.Entities;

import java.awt.*;


public class Sheep extends Animal implements Runnable{
    /**
     * Elindít egy külön szálat az állatnak. Enélkül a fő szál ami indít, megkéne várnia, hogy lefut ez a szál
     */
    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    /**
     * A mozgást lehetett volna ide írni, de nekem a kapitalizált rendszer irányítás szempontjából szimpatikusabb volt.
     * Interrupt-al az alvó szálat is ki lehet lőni, szuper hasznos.
     */
    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }


    /**
     * Így lehet máshonnan meghívni a szál leállítását. Ez dobni fog egy interruptot és megállítja a run()-t.
     */
    public void stopRunning() {
        running = false;
        if (thread != null) {
            thread.interrupt();  // Megszakítja a szálat, ha éppen alszik, ha nem
        }
    }

    /**
     * Alap értékek beállítása, random helyre spannol.
     */
    public Sheep() {
        eletben_van = true;
        hely.random();
        cubeColor = new Color(255, 255, 255);
    }

    /**
     * Alap értékek beállítása.
     * @param x x helyen
     * @param y y helyen
     */
    public Sheep(int x, int y) {
        if(x>XX) x = 495;
        if(x<0) x = 0;
        if(y>YY) y = 495;
        if(x<0) y = 495;

        eletben_van = true;
        hely.x = x;
        hely.y = y-30;
        cubeColor = new Color(255, 255, 255);
    }
    boolean running = true;
    private Thread thread;

    }
