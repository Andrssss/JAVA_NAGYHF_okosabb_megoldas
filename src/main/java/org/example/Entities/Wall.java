package org.example.Entities;

import java.awt.*;

/**
 * Falak, olyanok, mint az állatok, csak nem mozognak.
 */
public class Wall extends Animal {
    public int ennyi_maodperce_el;


    /**
     * Pozicióit beállítja. Térben és időben elhelyezi, ahogy illik egy Történelem érettségin. Megadott idő után eltűnik.
     * @param x helye
     * @param y helye
     */
    public Wall(int x, int y) { // ide kell létrehozni
        ennyi_maodperce_el=0;
        eletben_van = true;
        hely.x = (x-10) ;
        hely.y = y-30;
        cubeColor = new Color(70,70,70);
    }

    /**
     * Szép dolog üres konstruktort is létrehozni szerintem.
     */
    public Wall() {}
}
