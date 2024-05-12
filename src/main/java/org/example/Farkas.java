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
