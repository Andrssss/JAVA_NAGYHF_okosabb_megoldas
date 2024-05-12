package org.example;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public final class Field  extends JFrame  implements MouseListener, Runnable {
    public void AllatokMozog(){
        for (Barany b : b_list) {
            String eredmeny = b.randomMozgas(fal_list);
            //System.out.println("P" + b.jatekosHely + " "+atlehet_menni_balra() + " String : " +eredmeny);


            if(!eredmeny.equals("sima")){
                if(eredmeny.equals("jobbra")){
                        //System.out.println("Jobbra P" +b.jatekosHely );
                        //if (b.jatekosHely==1) {
                        //    b.setJatekosHely(2);
                        //}
                        //else if (b.jatekosHely==2) jatekosSzam=3;
                        //else System.out.println("Valaki jobbra akar bemmi. De nem tud.");
                    }
                    else if(eredmeny.equals("balra")){
                        //if (b.jatekosHely==2) b.setJatekosHely(1);
                        //else if (b.jatekosHely==3) b.setJatekosHely(3);
                        //else System.out.println("Valaki jobbra akar bemmi. De nem tud.");
                    }
                }
        }
        for (Farkas b : f_list) {
                String eredmeny = b.randomMozgas(fal_list);

                if(!eredmeny.equals("sima")){
                    if(eredmeny.equals("jobbra")){
                        //System.out.println("Jobbra P" +b.jatekosHely );
                        /*if (b.jatekosHely==1) {
                            b.setJatekosHely(2);
                        }
                        else if (b.jatekosHely==2) jatekosSzam=3;
                        else System.out.println("Valaki jobbra akar bemmi. De nem tud.");*/
                    }
                    else if(eredmeny.equals("balra")){
                        //if (b.jatekosHely==2) b.setJatekosHely(1);
                        //else if (b.jatekosHely==3) b.setJatekosHely(3);
                        //else System.out.println("Valaki jobbra akar bemmi. De nem tud.");
                    }
                }
        }
    }


















    @Override
    public void run() {}
    Field(int _playernumber,boolean readyornot) {
        game_over = false;
        cur_hanydb_fal=0;
        readyToRockAndRoll = readyornot;
        playernumber = _playernumber;


        label1 = new JLabel(String.valueOf(readyToRockAndRoll));
        label1.setBounds(100,60,150,15);

        //if(playernumber==1) label1 = new JLabel("localhost : "+Palya.getPORT_NUMBER() );
        //if(playernumber==2) label1 = new JLabel( Palya_kliens.getHost()+" : "+Palya_kliens.getPORT_NUMBER() );


        panel = new Field_Panel(this);

        frame.setTitle("PLAYER -  " + playernumber + " - FIELD");
        //frame.add(label1);
        frame.add(panel);
        frame.addMouseListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);


        Allatok_inditasa();
    }






























    private void Allatok_inditasa(){
        if(readyToRockAndRoll) {
            int farkaso_szama = 2;
            int baranyok_szama = 2;

            for (int i = 0; i < baranyok_szama; i++) {
                b_list.add(new Barany());
            }
            for (int i = 0; i < farkaso_szama; i++) {
                f_list.add(new Farkas());
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
    public ArrayList<Barany> getBaranyok() {
        return b_list;
    }
    public ArrayList<Farkas> getFarkasok(){
        return f_list;
    }
    public ArrayList<Falak> getFalak(){
        return fal_list;
    }
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
    protected static int max_hanydb_fal = 20;
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
