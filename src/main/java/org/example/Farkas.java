package org.example;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.floor;
import static java.lang.Math.sqrt;

public class Farkas extends Allat implements Runnable {
    private static Vectorr emozg = new Vectorr(); // Farkas lendülete (előző mozgásvektora)
    private static final float vFarkas = 5; // FARKAS MERETE


    Farkas() {
        hely.random();
        eletben_van = true;
        cubeColor = new Color(255, 0, 0);
    }

    @Override
    public void run() {

    }


    /// EZ VAN LEGKOZELEBB AZ EREDETIHEZ, DE SOKAT KELL CSISZOLNI, FARKASOK PALYAN KIVUL ...............................
    /*public void uldozBaranyok() {
        // Legközelebbi bárány kiszemelése
        float minTav = Float.MAX_VALUE; // "Végtelen" távolság
        Barany celBarany = null;
        for (Barany barany : Jatekos.baranyok) {
            float tav = tavolsag_floatban(barany.hely);
            if (tav < minTav) {
                celBarany = barany;
                minTav = tav;
            }
        }

        // Farkas mozgása a bárány felé
        if (celBarany != null) {
            Vectorr mozgasIrany = new Vectorr(celBarany.hely.x - hely.x, celBarany.hely.y - hely.y);
            mozgasIrany.normalizalt().szammal_szorzas(vFarkas); // Mozgás irányának normalizálása és sebesség beállítása
            hely.plusEquals(mozgasIrany); // Farkas mozgása a bárány felé
        }

        // Ha a farkas kiment a pályáról, visszahozzuk
        if (hely.x < 0) {
            hely.x += Palya.palyameret_x;
        } else if (hely.x > Palya.palyameret_x) {
            hely.x -= Palya.palyameret_x;
        }
        if (hely.y < 0) {
            hely.y += Palya.palyameret_y;
        } else if (hely.y > Palya.palyameret_y) {
            hely.y -= Palya.palyameret_y;
        }

        if (minTav <= vFarkas * vFarkas && celBarany != null) {
            celBarany.meghal();
        }
    }*/


    /// VEGTELEN SEBESSEGGEL OSSZE-VISSZA UGRALNAK---------------------------------------------------------------------
    /*public void uldozBaranyok2() {
        // Legközelebbi bárány keresése
        float minTav = Float.MAX_VALUE; // "Végtelen" távolság
        Barany celpont = null;
        for (Barany barany : Jatekos.baranyok) {
            float tav = tavolsag_floatban(barany.hely);
            if (tav < minTav) {
                celpont = barany;
                minTav = tav;
            }
        }

        // Ha van célpont, üldözni kell
        if (celpont != null) {
            Vectorr irany = celpont.hely.minus(hely).normalizalt(); // Irány a célpont felé
            Vectorr mozg = irany.szammal_szorzas(vFarkas);//.plusEquals(emozg); // Farkas mozgása (sebesség + lendület)
            mozg.plusEquals(emozg);
            // Új pozíció frissítése
            hely.plusEquals(mozg);

            // Korlátozás a pálya belsejére
            if (hely.x < 0) hely.x += XX;
            else if (hely.x > XX) hely.x -= XX;
            if (hely.y < 0) hely.y += YY;
            else if (hely.y > YY) hely.y -= YY;

            // Lendület frissítése
            emozg = mozg;
        }
        if (minTav < vFarkas * vFarkas && celpont != null) {
            celpont.meghal();
        }
    }
    //----------------------------------------------------------------------------------------------------------------


    ///FARKASOK VÉGTELEN SEBESSEGGEL VANNNAK BARANYOK KOLRUL
    public void uldozBaranyok3() {
        // Legközelebbi bárány kiszemelése
        float minTav = Float.MAX_VALUE; // "Végtelen" távolság
        Barany ot_uldozi = null;
        for (Barany barany : Jatekos.baranyok) {

            Vectorr tavV = barany.tavolsag(hely);
            float tav = tavV.hosszNegyzet();
            if (tav < minTav ) {
                if(barany.eletben_van){
                    ot_uldozi = barany;
                    minTav = tav;
                    //  System.out.println();
                }


            }
            //System.out.println(barany.hely.y);
        }

        // Legközelebbi bárány felé mozgás
        if (ot_uldozi != null) {
            Vectorr helye = new Vectorr(ot_uldozi.hely.x,ot_uldozi.hely.y);
            helye.minus(hely);
            helye.division(2);
            this.hely.plusEquals(helye);


            Vectorr irany = ot_uldozi.hely.minus(hely).normalizalt(); // Irány a célpont felé
            Vectorr mozg = irany.szammal_szorzas(vFarkas);//.plusEquals(emozg); // Farkas mozgása (sebesség + lendület)
            mozg.plusEquals(emozg);
            //mozg.division(emozg.szam_hozzaad(mozg.hossz() * vFarkas)); // Normálás és sebesség beállítása
            //hely.plusEquals(mozg); // Mozgás végrehajtása
            //emozg = helye.szammal_szorzas(2); // Lendület frissítése
        }

        // Ha elérték, akkor megeszik


        // Visszahozza, ha túlmegy a szélein
        hely.x = Math.max(0, Math.min(hely.x, XX));
        hely.y = Math.max(0, Math.min(hely.y, YY));

        if (minTav < vFarkas * vFarkas && ot_uldozi != null) {
            ot_uldozi.meghal();
        }
    }*/


}
