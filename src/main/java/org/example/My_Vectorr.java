package org.example;

import org.example.Frames.Field;

import java.util.concurrent.ThreadLocalRandom;
import static java.lang.Math.floor;


/**
 * Csokorba összeszedve az összes vector művelet, amire szükség lett volna
 */
public class My_Vectorr {
    public double x;
    public double y;

    /**
     * konstruktor
     * @param xx palya meret x
     * @param yy palya meret y
     */
    My_Vectorr(double xx, double yy){
        this.x = xx;
        this.y = yy;
    }
    public My_Vectorr(){}

    /**
     * Ha allatot szeretnenk letrehozni, random helyre, akkor ez a right way
     */
    public void random()
    {
        float x_randomNumber = ThreadLocalRandom.current().nextInt(Field.getPalyameret_x());
        float y_randomNumber = ThreadLocalRandom.current().nextInt(Field.getPalyameret_y());
        x = floor(x_randomNumber % Field.getPalyameret_x());
        y = floor(y_randomNumber % Field.getPalyameret_y());
    }


    /*
    float hosszNegyzet()
    {
        return (float) (x*x + y*y);
    }
    float hossz()
    {
        return (float) sqrt(x*x + y*y);
    }

    Vectorr minus(Vectorr v){
        Vectorr temp = new Vectorr();
        temp.x = this.x - v.x;
        temp.y = this.y - v.y;
        return temp;
    }

    void plusEquals(Vectorr v){
        this.x += v.x;
        this.y += v.y;
    }

    Vectorr szam_hozzaad(float v){
        this.x += v;
        this.y += v;
        return this;
    }
    void vektorrak_szorzas(Vectorr v){
        this.x *= v.x;
        this.y *= v.y;
    }

    Vectorr szammal_szorzas(float v){
        this.x *=(double) v;
        this.y *=(double) v;
        return this;
    }

    void division(Vectorr v){
        this.x /= v.x;
        this.y /= v.y;
    }

    void multiplication(float f){
        this.x *= f;
        this.y *= f;
    }

    void division(float f){
        this.x /= f;
        this.y /= f;
    }

    public Vectorr normalizalt() {
        double hossz = Math.sqrt(x * x + y * y);
        if (hossz != 0) {
            return new Vectorr(x / hossz, y / hossz);
        } else {
            // Ha a vektor hossza 0, akkor visszaadhatunk egy alapértelmezett vektort, például (0, 0)
            return new Vectorr(0, 0);
        }
    }
    */
}