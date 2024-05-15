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
    public ArrayList<Farkas> farkasok= new ArrayList<>();
    public ArrayList<Falak> falak = new ArrayList<>();
    Timer timer;
    Field myfield;
    private int ennyi_ideje_megy_a_game;
    private final int Ennyi_ideig_van_jatek=  30; // 10mp
    private final int falak_meghalasi_ideje = 30; // 3mp





    Field_Panel(Field field){
        ennyi_ideje_megy_a_game = 0;
        myfield = field;
        this.setPreferredSize(new Dimension(palyameret_x,palyameret_y));
        this.setBackground(Color.green);

        timer = new Timer(100, this);
        timer.start();
    }






    public void paint(Graphics g){
        ennyi_ideje_megy_a_game++;

        /*int seconds = ennyi_ideje_megy_a_game / 10;
        int minutes = seconds / 60;
        seconds %= 60;*/

        super.paint(g);
        Graphics g2d = (Graphics2D) g;
        g2d.setColor(palya_szine);
        g2d.fillRect(0, 0, 500, 500);

        if(ennyi_ideje_megy_a_game>Ennyi_ideig_van_jatek) {
            //g2d.drawString("GAME OVER",100,100);
            Field.setGame_over(true);
        }

        //String s = "Time: " + String.format("%02d:%02d", minutes, seconds);
        //g2d.drawString("Valami",100,100);

        // --------------------- BARANYOK ------------------------------------
        if(!baranyok.isEmpty()) g2d.setColor(baranyok.getFirst().cubeColor);
        for (Barany b : baranyok) {
                g2d.fillRect((int) b.hely.x, (int) b.hely.y, 5, 5);
        }
        // --------------------- FARKASOK  ------------------------------------
        if(!farkasok.isEmpty()) g2d.setColor(farkasok.getFirst().cubeColor);
        for (Farkas b : farkasok) {
                g2d.fillRect((int) b.hely.x, (int) b.hely.y, 5, 5);
        }
        // --------------------- FALAK ------------------------------------
        if(!falak.isEmpty()) g2d.setColor(falak.getFirst().cubeColor);
        ArrayList<Falak> falak_amiket_ki_kell_torolni = new ArrayList<>();
        for(Falak f : falak){
            if(f.ennyi_maodperce_el>=falak_meghalasi_ideje){
                falak_amiket_ki_kell_torolni.add(f);
            }
                g2d.fillRect((int) f.hely.x, (int) f.hely.y, 5, 5);
            f.ennyi_maodperce_el++;
        }
        for (Falak f: falak_amiket_ki_kell_torolni){
            falak.remove(f);
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
        if(!Field.game_over){
            myfield.AllatokMozog();

            // ITT KELL A MOZGAST MEGVALOSITANI - VAGY NEM TUDOM
            this.baranyok = myfield.getBaranyok();
            this.farkasok = myfield.getFarkasok();
            this.falak    = myfield.getFalak();

            repaint();
        }
        else{
            timer.stop();
            System.out.println("Field_panel : STOP");
        }
    }
}
