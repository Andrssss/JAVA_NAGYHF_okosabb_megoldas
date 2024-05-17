package org.example;

import java.awt.*;

public class Falak extends Allat {
    public int ennyi_maodperce_el;



    Falak(int x, int y) { // ide kell létrehozni
        ennyi_maodperce_el=0;
        eletben_van = true;
        hely.x = (x-10) ;
        hely.y = y-30;
        cubeColor = new Color(70,70,70);
    }
}
