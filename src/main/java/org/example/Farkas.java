package org.example;
import java.awt.*;

public class Farkas extends Allat implements Runnable {




    @Override
    public void run() {
        System.out.println("Farkas fut");
    }


    Farkas() {
        hely.random();
        eletben_van = true;
        cubeColor = new Color(255, 0, 0);
    }
}
