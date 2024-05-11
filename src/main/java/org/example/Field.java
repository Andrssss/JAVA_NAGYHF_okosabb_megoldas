package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public final class Field  extends JFrame  implements MouseListener, Runnable {
    private boolean readyToRockAndRoll;
    public ArrayList<Barany> baranyok;
    public ArrayList<Farkas> farkasok;
    public ArrayList<Falak> falak;
    protected static final Color palya_szine = new Color(0, 128, 60);
    public static int getPalyameret_x() {return palyameret_x;}
    public static int getPalyameret_y() {return palyameret_y;}
    protected static final int palyameret_x = 500;
    protected static final int palyameret_y = 500;
    protected JFrame frame = new JFrame();
    protected int playernumber;
    protected JLabel label1;
    ArrayList<Barany> b_list =  new ArrayList<>();
    ArrayList<Farkas> f_list =  new ArrayList<>();



    Field(int _playernumber,boolean readyornot) {
        readyToRockAndRoll = readyornot;
        playernumber = _playernumber;
        label1 = new JLabel(String.valueOf(readyToRockAndRoll));
        label1.setBounds(100,60,150,15);

        //if(playernumber==1) label1 = new JLabel("localhost : "+Palya.getPORT_NUMBER() );
        //if(playernumber==2) label1 = new JLabel( Palya_kliens.getHost()+" : "+Palya_kliens.getPORT_NUMBER() );

        frame.setTitle("PLAYER -  " + playernumber + " - FIELD");
        frame.add(label1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(palyameret_x,palyameret_y);
        frame.getContentPane().setBackground( palya_szine );
        frame.setLayout(null);
        frame.setVisible(true);



        if(readyToRockAndRoll) {
            int farkaso_szama = 2;
            int baranyok_szama = 2;

            for (int i = 0; i < baranyok_szama; i++) {
                b_list.add(new Barany());
            }
            for (int i = 0; i < farkaso_szama; i++) {
                f_list.add(new Farkas());
            }


            for(int i=0;i<baranyok_szama;i++){
                b_list.get(i).run();
            }
            for(int i=0;i<farkaso_szama;i++){
                f_list.get(i).run();
            }
        }
    }



    public void setReadyToRockAndRoll(boolean _readyToRockAndRoll) {
        this.readyToRockAndRoll = _readyToRockAndRoll;
        System.out.println(readyToRockAndRoll);
        frame.repaint();
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        System.out.println("Clicked at: (" + x + ", " + y );
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


// hát ez itt izgi lesz
    @Override
    public void run() {
        // todo
        // itt kell majd a falakat a bárányoknak adni, mert ugye a falak folyamatosan változnak

        //frame.repaint();
    }
}
