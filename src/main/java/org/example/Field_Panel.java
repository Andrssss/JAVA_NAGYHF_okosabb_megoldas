package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class Field_Panel extends JPanel implements ActionListener {
    protected static Color palya_szine = new Color(0, 128, 60);
    protected static final int palyameret_x = 500;
    protected static final int palyameret_y = 500;
    public ArrayList<Barany> baranyok = new ArrayList<>();
    public ArrayList<Farkas> farkasok= new ArrayList<>();;
    public ArrayList<Falak> falak = new ArrayList<>();;
    Timer timer;
    Field myfield;

    Field_Panel(Field field){
        myfield = field;
        this.setPreferredSize(new Dimension(palyameret_x,palyameret_y));
        this.setBackground(Color.green);
        timer = new Timer(100, this);
        timer.start();
    }

    public void paint(Graphics g){
        System.out.println("repaint");
        super.paint(g);
        Graphics g2d = (Graphics2D) g;
        g2d.setColor(palya_szine);
        g2d.fillRect(0, 0, 500, 500);


        if(!baranyok.isEmpty()) g2d.setColor(baranyok.get(0).cubeColor);
        for (Barany b : baranyok) {
            // Csak a saját bárányait rajzolja ki
            //if (b.jatekosHely == jatekosSzam) {
                g2d.fillRect((int) b.hely.x, (int) b.hely.y, 5, 5);
            //}
        }
        if(!farkasok.isEmpty()) g2d.setColor(farkasok.get(0).cubeColor);
        for (Farkas b : farkasok) {
            // Csak a saját bárányait rajzolja ki
            //if (b.jatekosHely == jatekosSzam) {
                g2d.fillRect((int) b.hely.x, (int) b.hely.y, 5, 5);
            //}
        }
        if(!falak.isEmpty())  g2d.setColor(falak.get(0).cubeColor);
        for(Falak f : falak){
            //if(f.jatekosHely == jatekosSzam){
                g2d.fillRect((int) f.hely.x, (int) f.hely.y, 5, 5);
            //}
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) { // ez fog másodpercenként

        // ITT KELL A MOZGAST MEGVALOSITANI - VAGY NEM TUDOM
        System.out.println("get animals");
        // ennek esetleg egetne külön klass
        this.baranyok = myfield.getBaranyok();
        this.farkasok = myfield.getFarkasok();
        this.falak    = myfield.getFalak();


        repaint();
    }
}
