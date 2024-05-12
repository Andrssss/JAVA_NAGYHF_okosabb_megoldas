package org.example;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

public final class Field  extends JFrame  implements MouseListener, Runnable {
    Palya_kliens palya_kliens;
    Palya_szerver palya_szerver;


    public void addBarany(Barany b){
        b_list.add(b);
        b.run();
    }

    public void addFarkas(Farkas f){
        f_list.add(f);
        f.run();
    }

    public void AllatokMozog(){
        for (Barany b : b_list) {
            String eredmeny = b.randomMozgas(fal_list);
            //System.out.println( " String : " +eredmeny);


            if(!eredmeny.equals("sima")) {
                if (eredmeny.equals("jobbra")) {
                    if (b.jobbra_atmehet && b.gazdi == 1 && READY_TO_SAND) {
                        System.out.println("barany kuldes");
                        b.gazdi = 2;
                        //palya_szerver.sendObjects(b);
                        try {
                            Palya_szerver.sendLine("Barany - "+ b.hely.y);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        b_list.remove(b);

                    } else {
                        //System.out.println("Barany felrement valami");
                    }
                } else if (eredmeny.equals("balra")) {
                    if (b.balra_atmehet && b.gazdi == 2 && READY_TO_SAND) {
                        b.gazdi = 1;
                        //palya_kliens.sendObjects(b);
                        try {
                            Palya_kliens.sendLine("Barany - "+ b.hely.y);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        b_list.remove(b);
                        // todo
                    } else {
                        //System.out.println("Valami felrement");
                    }
                }
            }
        }
        for (Farkas b : f_list) {
                String eredmeny = b.randomMozgas(fal_list);

            if(!eredmeny.equals("sima")) {
                if (eredmeny.equals("jobbra")) {
                    if (b.jobbra_atmehet && b.gazdi == 1 && READY_TO_SAND) {
                        b.gazdi = 2;
                        try {
                            Palya_szerver.sendLine("Farkas - "+ b.hely.y);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        b_list.remove(b);
                        // todo

                    } else {
                        //System.out.println("Valami felrement");
                    }
                } else if (eredmeny.equals("balra")) {
                    if (b.balra_atmehet && b.gazdi == 2 && READY_TO_SAND) {
                        b.gazdi = 1;
                        //palya_kliens.sendObjects(b);
                        try {
                            Palya_kliens.sendLine("Farkas - "+ b.hely.y);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        b_list.remove(b);
                        // todo

                    } else {
                        //System.out.println("Valami felrement");
                    }
                }
            }
        }
    }





















    Field(int _playernumber,boolean readyornot) {
        if(_playernumber == 2 ) READY_TO_SAND = true;
        //this.palya_szerver = palya_szerver1;
        //this.palya_kliens = palya_kliens1;


        playernumber = _playernumber;

        game_over = false;
        cur_hanydb_fal=0;
        readyToRockAndRoll = readyornot;

        label1 = new JLabel(String.valueOf(readyToRockAndRoll));
        label1.setBounds(100,60,150,15);

        panel = new Field_Panel(this);

        frame.setTitle("PLAYER -  " + playernumber + " - FIELD");
        //frame.add(label1);
        frame.add(panel);
        frame.addMouseListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

    }

























    @Override
    public void run() {
        frame.setVisible(true);
        Allatok_inditasa();
    }

    private void Allatok_inditasa(){
        if(readyToRockAndRoll) {
            int farkaso_szama = 3;
            int baranyok_szama = 3;

            for (int i = 0; i < baranyok_szama; i++) {
                Barany b = new Barany();
                b.setGazdi(playernumber);
                b_list.add(b);
            }
            for (int i = 0; i < farkaso_szama; i++) {
                Farkas f = new Farkas();
                f.setGazdi(playernumber);
                f_list.add(f);
            }


            for (int i = 0; i < baranyok_szama; i++) {
                b_list.get(i).run();
            }
            for (int i = 0; i < farkaso_szama; i++) {
                f_list.get(i).run();
            }
        }
    }




    // GETTER / SETTER ---------------------------------------------------------------
    // -------------------------------------------------------------------------------
    public void setReadyToRockAndRoll(boolean _readyToRockAndRoll) {
        this.readyToRockAndRoll = _readyToRockAndRoll;
        System.out.println(readyToRockAndRoll);
        frame.repaint();
    }
    public ArrayList<Barany> getBaranyok() { return b_list; }
    public ArrayList<Farkas> getFarkasok(){ return f_list;}
    public ArrayList<Falak> getFalak(){ return fal_list; }
    public static int getPalyameret_x() {return palyameret_x;}
    public static int getPalyameret_y() {return palyameret_y;}
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------






    // MOUSE LISTENER ----------------------------------------------------------------
    // -------------------------------------------------------------------------------
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if(cur_hanydb_fal<max_hanydb_fal){
            cur_hanydb_fal++;
            Falak f1 = new Falak(x,y);
            fal_list.add(f1);
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------





    // PRIVATE VALTOZOK JATEKHOZ -----------------------------------------------------
    // -------------------------------------------------------------------------------
    protected ArrayList<Barany> b_list =  new ArrayList<>();
    protected ArrayList<Farkas> f_list =  new ArrayList<>();
    protected ArrayList<Falak> fal_list =  new ArrayList<>();
    protected boolean readyToRockAndRoll;
    public static boolean READY_TO_SAND;
    protected static int max_hanydb_fal = 30;
    protected  int cur_hanydb_fal;
    protected static final int palyameret_x = 500;
    protected static final int palyameret_y = 500;
    protected JFrame frame = new JFrame();
    protected int playernumber;
    protected JLabel label1;
    protected Field_Panel panel;
    protected boolean game_over;
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
}
