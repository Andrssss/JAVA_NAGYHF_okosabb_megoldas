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
