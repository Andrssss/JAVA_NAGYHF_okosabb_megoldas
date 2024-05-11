package org.example;

import java.awt.*;
import java.util.ArrayList;

public class Barany extends Allat {

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


    /*public String menekul(Falak falak) {
        // Szomszédos bárányok keresése
        ArrayList<Barany> szomszedok = new ArrayList<Barany>();
        float szomszed_tavolsag = 5; // Szomszédsági távolság

        /*for (Barany b : Palya.baranyok) {
            if (b != this && tavolsag_floatban(b.hely) < szomszed_tavolsag) {
                szomszedok.add(b);
            }
        }*/

        // Vezető bárány kiválasztása (opcionális)
  /*      Barany vezeto = this; // Alapértelmezett vezető: önmaga
        for (Barany b : szomszedok) {
            // Vezető kiválasztási feltétel (pl. legnagyobb tapasztalat)
            //if (b.tapasztalat > vezeto.tapasztalat) {
            //  vezeto = b;
            //}
        }

        // Legközelebbi farkas kiszemelése a csoport számára
        float minTav = Float.MAX_VALUE; // "Végtelen" távolság
        Farkas o_uldozi = null;
*/
        /*for (Farkas farkas : Palya.farkasok) {
            float tav = tavolsag_floatban(farkas.hely);
            if (tav < minTav) {
                o_uldozi = farkas;
                minTav = tav;
            }
        }*/



        // Menekülési irány kiszámítása a vezető alapján
 /*       Farkas.Vectorr menekulesIrany = new Farkas.Vectorr();
        if (o_uldozi != null && vezeto != null) {
            float vBarany = 3; // Sebesség

            // Irány a legközelebbi farkastól
            Farkas.Vectorr farkasIrany = new Farkas.Vectorr(o_uldozi.hely.x - vezeto.hely.x, o_uldozi.hely.y - vezeto.hely.y);

            // Csapatkohézió (vonzerő a szomszédok felé)
            Farkas.Vectorr csoportIrany = new Farkas.Vectorr();
            for (Barany b : szomszedok) {
                csoportIrany.plusEquals(new Farkas.Vectorr(b.hely.x - vezeto.hely.x, b.hely.y - vezeto.hely.y));
            }

            // Kombinált menekülési irány
            menekulesIrany = farkasIrany.minus(csoportIrany).normalizalt();
            menekulesIrany.szammal_szorzas(vBarany);
        }


*/
/*

        // Új pozíció számítása és korlátozása
        int ujX =  (int)(hely.x + menekulesIrany.x);
        int ujY =  (int)(hely.y + menekulesIrany.y);
        Farkas.Vectorr visszakuldeni = new Farkas.Vectorr(ujX,ujY);

*/
        // RANDOOM MOZGAS -----------------------------
        //Random random = new Random();
        //ujX += random.nextInt(5) + 1;
        //ujY += random.nextInt(5) + 1;
        // --------------------------------------------

        // Bárány pozíció frissítése



        // A BARANY NEM TUDJA, HOGY HANYADIK PLAYERE
        // DE A PALYA SZELEIT TUDNI FOGJA. ÉS KÜLÖNBÖZŐ PLAYERHEZ, MAS PALYA
        // if (ujX >=  Palya.palyameret_x-5 &&  )
        //if( ujX > 0                      && player = 1 )
        //if( ujX > 0                      && player = 2 )
        //if (ujX >=  Palya.palyameret_x-5 && player = 2 )


/*
        if( ujY > 0  && ujY < Palya.palyameret_y-5 ) this.hely.y = ujY;
        if (ujY > Palya.palyameret_x-5 )             this.hely.y = Palya.palyameret_y-5;
        if (ujY < 0 )                                this.hely.y = 0;


        //frame.setSize(palyameret_x+14, palyameret_y+37);
        if (ujX > 0  && ujX < Palya.palyameret_x-5 ) {
            this.hely.x = ujX;
        }
        if (ujX > Palya.palyameret_x-5 && Palya_rajzol.getAtmehetJobbra(jatekosHely)  ) {
            System.out.println(jatekosHely);
            this.hely.x = 0;
            return "jobbra";
        }
        if (ujX < 0  && Palya_rajzol.getAtmehetBalra(jatekosHely) ) {
            this.hely.x = Palya.palyameret_x-5;
            return "balra";
        }


        return "marad";

    }*/



}
