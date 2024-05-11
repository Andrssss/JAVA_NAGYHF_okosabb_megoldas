package org.example;

import java.awt.*;

public class Falak extends Allat {
    Falak() {
        eletben_van = true;
        hely.random();
        cubeColor = new Color(255, 255, 255);
    }

    Falak(int x, int y, int XX, int YY) {
        eletben_van = true;
        hely.x = (x-10) ;
        hely.y = y-30;
        cubeColor = new Color(70,70,70);
    }



    @Override
    void meghal() {
        super.meghal();
    }
}
