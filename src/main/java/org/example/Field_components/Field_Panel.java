package org.example.Field_components;

import org.example.Entities.Sheep;
import org.example.Entities.Wall;
import org.example.Entities.Wolf;
import org.example.Frames.Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


public class Field_Panel extends JPanel implements ActionListener {
    private final int Ennyi_ideig_van_jatek =  120; // 5mp
    private final int falak_meghalasi_ideje = 30; // 3mp


    /**
     * KONSTRUKTOR
     * @param field A field, amire rajzol. Erre azért van szükség, mert ez a szál számolja az eltelt időt és szól a --Field-- - nek
     *              ha vége van a játékos időnek. Bezárásra kéri fel.
     * @param _barany_monitor Erre azért van szükség, hogy ne akadjon össze a --Field-- -el (minden 10.-ik futtatásra sikerült)
     * @param _farkas_monitor Erre azért van szükség, hogy ne akadjon össze a --Field-- -el
     * @param _falak_monitor Erre azért van szükség, hogy ne akadjon össze a --Field-- -el
     */
    public Field_Panel(Field field, Object _barany_monitor, Object _farkas_monitor, Object _falak_monitor){
        barany_monitor = _barany_monitor;
        farkas_monitor = _farkas_monitor;
        falak_monitor = _falak_monitor;

        ennyi_ideje_megy_a_game = 0;
        myfield = field;
        this.setPreferredSize(new Dimension(Field.getPalyameret_x(), Field.getPalyameret_y()));
        this.setBackground(Color.green);
    }




    protected static Color palya_szine = new Color(0, 128, 60);
    private LinkedList<Sheep> baranyok = new LinkedList<>();
    private LinkedList<Wolf> farkasok= new LinkedList<>();
    private LinkedList<Wall> falak = new LinkedList<>();
    private final Object farkas_monitor;
    private final Object barany_monitor;
    private final Object falak_monitor;
    private Timer timer;
    private final Field myfield;
    private int ennyi_ideje_megy_a_game;


    /**
     * Elindításával a timer szál is elindul.
     */
    public void start(){
        timer = new Timer(100, this);
        timer.start();
    }


    /**
     * Ez a függvény fogja kirajzolni a mindent. Ha egy fal ideje letelt, akkor törli a listából.
     * @param g  the <code>Graphics</code> context in which to paint
     */
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
        //synchronized (barany_monitor) {
        synchronized (baranyok) {
            Iterator<Sheep> baranyIterator = baranyok.iterator();
            while (baranyIterator.hasNext()) {
                Sheep b = baranyIterator.next();
                g2d.setColor(b.cubeColor);
                g2d.fillRect((int) b.hely.x, (int) b.hely.y, 5, 5);
            }
        }

        // --------------------- FARKASOK  ------------------------------------
        //synchronized (farkas_monitor) {
        synchronized (farkasok) {
            Iterator<Wolf> farkasIterator = farkasok.iterator();
            while (farkasIterator.hasNext()) {
                Wolf f = farkasIterator.next();
                g2d.setColor(f.cubeColor);
                g2d.fillRect((int) f.hely.x, (int) f.hely.y, 5, 5);
            }
        }

        // --------------------- FALAK ------------------------------------
        ArrayList<Wall> falak_amiket_ki_kell_torolni = new ArrayList<>();
        //synchronized (falak_monitor) {
        synchronized (falak) {
            Iterator<Wall> iterator = falak.iterator();
            while (iterator.hasNext()) {
                Wall f = iterator.next();
                if (f.ennyi_maodperce_el >= falak_meghalasi_ideje) {
                    falak_amiket_ki_kell_torolni.add(f);
                }
                g2d.setColor(f.cubeColor);
                g2d.fillRect((int) f.hely.x, (int) f.hely.y, 5, 5);
                f.ennyi_maodperce_el++;
            }

            for (Wall f : falak_amiket_ki_kell_torolni) {
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


    /**
     * Ez fog adot időközönként meghívódni. Ekkor elkéri a --Field-- -től a az objektumokat.
     * Majd mozgatást kér.
     * Majd az elhullot állatok kiszelektálását.
     * És csak ezután rajzolja ki őket.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) { // ez fog másodpercenként meghívodni
        if(!myfield.game_over){
            myfield.AllatokMozog();
            myfield.OsztMeghaltal_e();

            synchronized (baranyok){
            //synchronized (barany_monitor){
                this.baranyok = myfield.getBaranyok();
            }
            synchronized (farkasok){
            //synchronized (farkas_monitor){
                this.farkasok = myfield.getFarkasok();
            }
            synchronized (falak) {
            //synchronized (falak_monitor) {
                this.falak    = myfield.getFalak();
            }

            repaint();
        }
        else{
            timer.stop();
            System.out.println("Field_panel : STOP");
            myfield.End_is_here();
        }
    }
}
