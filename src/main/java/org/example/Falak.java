package org.example;

import java.awt.*;

public class Falak extends Allat {
    Falak(int x, int y) { // ide kell létrehozni
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
