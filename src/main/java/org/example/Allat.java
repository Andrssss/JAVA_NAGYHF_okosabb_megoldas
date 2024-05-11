package org.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Allat implements Runnable {
    protected Vectorr hely = new Vectorr();
    protected Color cubeColor;
    protected boolean eletben_van;
    protected int jatekosHely;


    public void setJatekosHely(int ide){
        this.jatekosHely = ide;
    }

    protected int XX = Field.getPalyameret_x();
    protected int YY = Field.getPalyameret_y();


    public synchronized String randomMozgas(ArrayList<Falak> falak) {
        // Generálunk véletlenszerű irányokat
        int irany_x =6;
        int irany_y = ThreadLocalRandom.current().nextInt(-1, 2); // -1, 0, vagy 1 lehet az irány

        // Beállítjuk az új pozíciót a véletlenszerű irányok alapján
        int ujX = (int) (this.hely.x + irany_x);
        int ujY = (int) (this.hely.y + irany_y);


        // Hogy a falak ne engedjék át --------------------------------------------------
        boolean x_fal_utkozes = false;
        boolean y_fal_utkozes = false;
        for(Falak f : falak){
            if(ujX > f.hely.x-5 && Math.abs(ujY - f.hely.y-5)<5) x_fal_utkozes = true;
            if(ujY > f.hely.y-5 && Math.abs(ujY - f.hely.y-5)<5) y_fal_utkozes = true;
        }




        // Hogy Y ne mehessen ki a palyarol ---------------------------------------------
        if( ujY > 0  && ujY < Field.getPalyameret_y()-5  &&  !y_fal_utkozes)  this.hely.y = ujY;
        if (ujY > Field.getPalyameret_x()-5 )             this.hely.y = Field.getPalyameret_y()-5;
        if (ujY < 0 )                                this.hely.y = 0;









        if (ujX > 0  && ujX < Field.getPalyameret_x() && !x_fal_utkozes ) this.hely.x = ujX;


        /// EZEK ITT JÓL MŰKÖDTEK  ----------------------------------------------------------------------
  /*
        // Ne menjen ki a pályáról ------------------------
        if(!Palya_rajzol.getAtmehetJobbra(this.jatekosHely) && ujX >= Field.getPalyameret_x()-5 ) this.hely.x = Field.getPalyameret_x()-5;
        if(!Palya_rajzol.getAtmehetBalra(this.jatekosHely) && ujX <= 0) this.hely.x = 0;


        // /JOBBRA, BALRA ÁTMEGY ---------------------------
        if (ujX >= Field.getPalyameret_x() && Palya_rajzol.getAtmehetJobbra(jatekosHely)) {

            this.hely.x = 0;
            return "jobbra";
        }
        if (ujX <= 0  && Palya_rajzol.getAtmehetBalra(jatekosHely) ) {
            this.hely.x = Field.getPalyameret_x()-5;
            return "balra";
       }
 */
        return "marad";
    }





    @Override
    public void run() {

    }




    void meghal(){
        eletben_van = false;
        cubeColor =  new Color(0,0,0);
    }

    /*Vectorr tavolsag(Vectorr honnan) {
        if (honnan != null) {
            return new Vectorr(honnan.x - this.hely.x, honnan.y - this.hely.y);
        } else {
            throw new IllegalArgumentException("honnan parameter cannot be null");
        }
    }

    float tavolsag_floatban(Vectorr honnan){
        return (float) (honnan.x - this.hely.x + honnan.y - this.hely.y);
    }*/
}
