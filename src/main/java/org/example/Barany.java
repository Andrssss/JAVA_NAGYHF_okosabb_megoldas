package org.example;
import java.awt.*;


public class Barany extends Allat implements Runnable{

    @Override
    public void run() {
        System.out.println("Barany fut");
        //while(true){
            //System.out.println("Barany fut");
        //}
        //randomMozgas();
    }







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
        super.meghal();
    }
}
