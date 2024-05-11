package org.example;

import java.util.ArrayList;

public class Allatok_szalak implements Runnable{
    Allatok_szalak(){
        ArrayList<Barany> b_list =  new ArrayList<>();
        ArrayList<Farkas> f_list =  new ArrayList<>();
        int farkaso_szama = 1;
        int baranyok_szama = 1;

        for(int i=0;i<baranyok_szama;i++){
            b_list.add(new Barany());
        }
        for(int i=0;i<farkaso_szama;i++) {
            f_list.add(new Farkas());
        }


        for(int i=0;i<baranyok_szama;i++){
            b_list.get(i).run();
        }
        for(int i=0;i<farkaso_szama;i++){
            f_list.get(i).run();
        }
    }
    @Override
    public void run() {

    }
}
