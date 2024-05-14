package org.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Allat implements Runnable {
    @Override
    public void run() {

    }





    public synchronized String randomMozgas(ArrayList<Falak> falak) {
        // Generálunk véletlenszerű irányokat
        int irany_x =-5;
        int irany_y = ThreadLocalRandom.current().nextInt(-1, 2); // -1, 0, vagy 1 lehet az irány

        // Beállítjuk az új pozíciót a véletlenszerű irányok alapján
        int ujX = (int) (this.hely.x + irany_x);
        int ujY = (int) (this.hely.y + irany_y);


        // Hogy a falak ne engedjék át --------------------------------------------------
        boolean x_fal_utkozes = false;
        boolean y_fal_utkozes = false;
        for(Falak f : falak){
            if(ujX >= f.hely.x-5 && (Math.abs(ujY - f.hely.y-5)<5 )) x_fal_utkozes = true;
            //if(ujY > f.hely.y-5 && Math.abs(ujY - f.hely.y-5)<5) y_fal_utkozes = true;
        }




        // Hogy Y ne mehessen ki a palyarol ---------------------------------------------
        if( ujY > 0  && ujY < Field.getPalyameret_y()-5  &&  !y_fal_utkozes)  this.hely.y = ujY;
        if (ujY > Field.getPalyameret_x()-5 )             this.hely.y = Field.getPalyameret_y()-5;
        if (ujY < 0 )                                this.hely.y = 0;


        // Új érték
        if (ujX > 0  && ujX < Field.getPalyameret_x() && !x_fal_utkozes ) this.hely.x = ujX;

        // Ne menjen ki a pályáról ------------------------
        if(!jobbra_atmehet && ujX >= Field.getPalyameret_x()-5 ) this.hely.x = Field.getPalyameret_x()-5;
        if(!balra_atmehet && ujX <= 0) this.hely.x = 0;


        // /JOBBRA, BALRA ÁTMEGY ---------------------------
        if (ujX >= Field.getPalyameret_x() && jobbra_atmehet) {
            this.hely.x = 0;
            return "jobbra";
        }
        if (ujX <= 0  && balra_atmehet ) {
            this.hely.x = Field.getPalyameret_x()-5;
            return "balra";
        }
        return "marad";
    }
    void meghal(){
        eletben_van = false;
        cubeColor =  new Color(0,0,0);
    }
    public void setGazdi(int ide){
        this.gazdi = ide;
        switch (ide){
            case 1 :
                jobbra_atmehet = true;
                balra_atmehet = false;
                break;
            case 2 :
                jobbra_atmehet = false;
                balra_atmehet = true;
                break;
        }
    }



    protected Vectorr hely = new Vectorr();
    protected Color cubeColor;
    protected boolean eletben_van;
    protected int XX = Field.getPalyameret_x();
    protected int YY = Field.getPalyameret_y();
    protected int gazdi;
    protected boolean jobbra_atmehet;
    protected boolean balra_atmehet;

}
