package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;


public class Field_Panel extends JPanel implements ActionListener {
    private final int Ennyi_ideig_van_jatek =  50; // 50mp
    private final int falak_meghalasi_ideje = 30; // 3mp
    Field_Panel(Field field,Object _barany_monitor,Object _farkas_monitor,Object _falak_monitor){
        barany_monitor = _barany_monitor;
        farkas_monitor = _farkas_monitor;
        falak_monitor = _falak_monitor;

        ennyi_ideje_megy_a_game = 0;
        myfield = field;
        this.setPreferredSize(new Dimension(Field.palyameret_x,Field.palyameret_y));
        this.setBackground(Color.green);


        //timer.start(); // ez itt nem sexy
    }




    protected static Color palya_szine = new Color(0, 128, 60);
    private ArrayList<Barany> baranyok = new ArrayList<>();
    private ArrayList<Farkas> farkasok= new ArrayList<>();
    private ArrayList<Falak> falak = new ArrayList<>();
    private final Object farkas_monitor;
    private final Object barany_monitor;
    private final Object falak_monitor;
    private Timer timer;
    private final Field myfield;
    private int ennyi_ideje_megy_a_game;


    public void start(){
        timer = new Timer(100, this);
        timer.start();
    }







    public void paint(Graphics g) {
        ennyi_ideje_megy_a_game++;

        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(palya_szine);
        g2d.fillRect(0, 0, 500, 500);

        if (ennyi_ideje_megy_a_game > Ennyi_ideig_van_jatek) {
            myfield.setGame_over(true);
        }

        // --------------------- BARANYOK ------------------------------------
        synchronized (barany_monitor) {
            Iterator<Barany> baranyIterator = baranyok.iterator();
            while (baranyIterator.hasNext()) {
                Barany b = baranyIterator.next();
                g2d.setColor(b.cubeColor);
                g2d.fillRect((int) b.hely.x, (int) b.hely.y, 5, 5);
            }
        }

        // --------------------- FARKASOK  ------------------------------------
        synchronized (farkas_monitor) {
            Iterator<Farkas> farkasIterator = farkasok.iterator();
            while (farkasIterator.hasNext()) {
                Farkas f = farkasIterator.next();
                g2d.setColor(f.cubeColor);
                g2d.fillRect((int) f.hely.x, (int) f.hely.y, 5, 5);
            }
        }

        // --------------------- FALAK ------------------------------------
        ArrayList<Falak> falak_amiket_ki_kell_torolni = new ArrayList<>();
        synchronized (falak_monitor) {
            Iterator<Falak> iterator = falak.iterator();
            while (iterator.hasNext()) {
                Falak f = iterator.next();
                if (f.ennyi_maodperce_el >= falak_meghalasi_ideje) {
                    falak_amiket_ki_kell_torolni.add(f);
                }
                g2d.setColor(f.cubeColor);
                g2d.fillRect((int) f.hely.x, (int) f.hely.y, 5, 5);
                f.ennyi_maodperce_el++;
            }

            for (Falak f : falak_amiket_ki_kell_torolni) {
                falak.remove(f);
            }
        }
    }






    /*private void drawObjects(Graphics2D g2d, List<? extends Allat> objects) {
        if (!objects.isEmpty()) {
            g2d.setColor(objects.get(0).getCubeColor()); // Set color based on first object
            for (Rajzolhato object : objects) {
                g2d.fillRect((int) object.getHely().x, (int) object.getHely().y, 5, 5);
            }
        }
    }*/


    @Override
    public void actionPerformed(ActionEvent e) { // ez fog másodpercenként
        if(!myfield.game_over){
            myfield.AllatokMozog();
            myfield.OsztMeghaltal_e();

            // ITT KELL A MOZGAST MEGVALOSITANI - VAGY NEM TUDOM

            synchronized (barany_monitor){  this.baranyok = myfield.getBaranyok();  }
            synchronized (farkas_monitor){  this.farkasok = myfield.getFarkasok();  }
            synchronized (falak_monitor) {  this.falak    = myfield.getFalak();     }



            repaint();
        }
        else{
            timer.stop();
            System.out.println("Field_panel : STOP");
            myfield.End_is_here();
        }
    }
}
