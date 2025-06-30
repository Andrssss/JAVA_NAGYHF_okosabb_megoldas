package org.example.Frames;
import org.example.Entities.Sheep;
import org.example.Entities.Wall;
import org.example.Entities.Wolf;
import org.example.Field_components.Field_Panel;
import org.example.Field_components.Field_client;
import org.example.Field_components.Field_server;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


/**
 * Ez lényegében a game field. Ez egyben a Game-master is, írányít. A megjelenítésre másik szálat használok --Field-panel--,
 * hogy a nehezebb számítások ne öljét meg azt a szálat.
 */
public final class Field  extends JFrame  implements MouseListener, Runnable {
    private int allakot_szama = 500; // 300 farkas + 300 bárány -> 600 szál / pálya


    /**
     * Konstruktor
     * @param _playernumber Szerver-1, Kliens-2
     * @param _lock Azért van erre szükség, mert amikor vége van a játéknak szinkronizálni kell. A Palya-kliens/szerver megkapja
     *              a game-over üzenetet, ekkor vár ennek a szálnak a válaszára, hogy neki mennyi báránya van. Ezután hívja csak
     *              meg az --Ending-frame-- -et. Enélkül sokszor a Palya-kliens/szerver általában gyorsabban fut le, mint ez.
     */
    public Field(int _playernumber, Object _lock) {
        lock = _lock;
        playernumber = _playernumber;
        game_over = false;
        panel = new Field_Panel(this,barany_monitor,farkas_monitor,falak_monitor); // todo , ez itt indit egy szalat WTF
        frame.add(panel);               // TODO
        frame.setTitle("PLAYER -  " + playernumber + " - FIELD");

        frame.addMouseListener(this);
        frame.pack();
        if(playernumber ==1) frame.setLocation(100, 100);
        if(playernumber ==2) frame.setLocation(700, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
    }


    /**
     * Panelt indít, állatokat indít és ha vége van a játéknak, akkor futása leáll.
     * Panel-nak hívom jelen esetbe a --Field_Panel-- -t. Itt lesz a grafikus megjelenítés műveletei definiálva.
     */
    @Override
    public void run() {
        System.out.println("Field fut P"  + playernumber);
        panel.start();
        frame.setVisible(true);
        synchronized (lock){ Allatok_inditasa(); }

        boolean running = true;
        while(running){
            if(game_over) running = false;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Field fut P"  + playernumber + "  STOP");
    }
























    // ------------------------------ FÜGGVENYEK  ------------------------------------
    // -------------------------------------------------------------------------------

    /**
     * Állatokat indítja, lock azért kell, mert a rajzolással összeakadhat. És akár game-over -el
     */
    private void Allatok_inditasa(){
        synchronized (lock){
                int farkaso_szama = allakot_szama;
                int baranyok_szama = allakot_szama;
                for (int i = 0; i < baranyok_szama; i++) {
                    Sheep barany = new Sheep();
                    barany.setGazdi(playernumber);
                    synchronized (b_list) {b_list.add(barany);} // Ez sok fejfájást okozott
                    //synchronized (barany_monitor) {b_list.add(barany);} // Ez sok fejfájást okozott
                    // olyan nettó 2 óra depresszió
                    barany.start();
                }
                for (int i = 0; i < farkaso_szama; i++) {
                    Wolf f = new Wolf();
                    f.setGazdi(playernumber);
                    synchronized (f_list){f_list.add(f);}
                    //synchronized (farkas_monitor){f_list.add(f);}
                    f.start();
                }
        }
    }

    /**
     * Bárányok halálozását követi ez a függvény. Rajzolással össze tud akadni, szóval monitorokkal kell védeni.
     * Nem feltétlenűl optimális, de biztonságos, ami prioritás volt.
     */
    public void OsztMeghaltal_e() {
        ArrayList<Sheep> remove_b_list = new ArrayList<>();

        synchronized (f_list) {
        //synchronized (farkas_monitor) {
            for (Wolf f : f_list) {
                //synchronized (barany_monitor) {
                synchronized (b_list) {
                    Iterator<Sheep> baranyIterator = b_list.iterator();
                    while (baranyIterator.hasNext()) {
                        Sheep b = baranyIterator.next();
                        if ((Math.abs(f.hely.x - b.hely.x - 5) < 6) && (Math.abs(f.hely.y - b.hely.y - 5) < 6)) {
                            b.stopRunning();
                            remove_b_list.add(b);
                        }
                    }
                }
            }
        }

        synchronized (b_list) {
        //synchronized (barany_monitor) {
            for (Sheep b : remove_b_list) {
                b_list.remove(b);
            }
        }
    }

    /**
     * 80 sornyi öröm. Nem merem szépíteni, mert így se sok időm maradt más tárgyakra.
     * Szinkronizálni kellet --Field-Panel-- -el, illetve az okozott bajt, hogy bármikor jöhet egy új állat és ha épp
     * ez is ment és jött is egy új bárány, akkor teljesen meghallt.
     */
    public void AllatokMozog(){
        if(!game_over) {
            ArrayList<Sheep> remove_b_list = new ArrayList<>();
            ArrayList<Wolf> remove_f_list = new ArrayList<>();

            synchronized (b_list){
            //synchronized (barany_monitor){
                for (Sheep b : b_list) {
                    String eredmeny = b.randomMozgas(fal_list,falak_monitor);

                    if (!eredmeny.equals("marad")) {
                        if (eredmeny.equals("jobbra")) {
                            if (b.jobbra_atmehet && b.gazdi == 1) {
                                int y_hely = (int) b.hely.y;
                                //b.stopRunning();
                                try {
                                    Field_server.sendLine("Barany "+ 0 +" "+ y_hely);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                remove_b_list.add(b);
                            }
                        } else if (eredmeny.equals("balra")) {
                            if (b.balra_atmehet && b.gazdi == 2) {
                                int y_hely = (int) b.hely.y;
                                //b.stopRunning();
                                try {
                                    Field_client.sendLine("Barany "+ (palyameret_y-5) +" "+ y_hely);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                remove_b_list.add(b);
                            }
                        }
                    }
                }
            }

            synchronized (f_list) {
            //synchronized (farkas_monitor) {
                for (Wolf b : f_list) {
                    String eredmeny = b.randomMozgas_Farkas(fal_list,f_list,farkas_monitor,falak_monitor);

                    if (!eredmeny.equals("marad")) {
                        if (eredmeny.equals("jobbra")) {
                            if (b.jobbra_atmehet && b.gazdi == 1) {
                                int y_hely = (int) b.hely.y;
                                //b.stopRunning();
                                try {
                                    Field_server.sendLine("Farkas " + 0 + " " + y_hely);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                remove_f_list.add(b);
                            }
                        } else if (eredmeny.equals("balra")) {
                            if (b.balra_atmehet && b.gazdi == 2) {
                                int y_hely = (int) b.hely.y;
                                //b.stopRunning();
                                try {
                                    Field_client.sendLine("Farkas " + (palyameret_y - 5) + " " + y_hely);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                remove_f_list.add(b);
                            }
                        }
                    }
                }
            }

            synchronized (b_list) { // sok fejfájás...
            //synchronized (barany_monitor) { // sok fejfájás...
                for (Sheep b : remove_b_list) {
                    b.stopRunning();
                    b_list.remove(b);
                }
            }
            synchronized (f_list) {
            //synchronized (farkas_monitor) {
                for (Wolf b : remove_f_list) {
                    b.stopRunning();
                    f_list.remove(b);
                }
            }
        }
    }


    /**
     * Ha jön bárány, akkor itt fog a listába belekerülni és elindítódni, persze csak, ha folyik a játék
     * @param x kapott x érték
     * @param y kapott y érték
     */
    public void addBarany(int x,int y){
        //System.out.println("Barany - y : " + " Player" + playernumber);
        Sheep b = new Sheep(x,y);
        b.setGazdi(playernumber);
        //synchronized (barany_monitor) {  b_list.add(b);  }
        synchronized (b_list) {  b_list.add(b);  }
        //b.run(); EZ úgy megszopatott, de úgy .... Már negyedjére XDDDD
        //  szerintem egy teljes napom elment rá.... A tudás hatalom gyermekem
        if(game_over){System.err.println("Barany nem indul, mert game over");}
        else{
            b.start();
        }
    }
    /**
     * Ha jön farkas, akkor itt fog a listába belekerülni és elindítódni, persze csak, ha folyik a játék
     * @param x kapott x érték
     * @param y kapott y érték
     */
    public void addFarkas(int x,int y){
        //System.out.println("Farkas - y : " + " Player" + playernumber);
        Wolf f = new Wolf(x, y);
        f.setGazdi(playernumber);
        synchronized (f_list){  f_list.add(f);  }
        //synchronized (farkas_monitor){  f_list.add(f);  }
        if(game_over){System.err.println("Farkas nem indul, mert game over");}
        else{
            f.start();
        }
    }

    /**
     * Összeszámolja a bárányokat és leállítja a szálakat. Majd a megfelelő helyre elküldi az eredményt --Palya-klies-- / --Palya-szerver--
     */
    public void End_is_here(){
        System.out.println("Field : End_is_here()");
        System.out.println("Field : game_over :" + game_over);
        if(game_over){
            synchronized (lock){
                System.out.println("Field : belepett a végére");
                //synchronized (farkas_monitor){ for(Farkas b : f_list){ b.stopRunning(); }}
                synchronized (f_list){ for(Wolf b : f_list){ b.stopRunning(); }}
                //synchronized (barany_monitor){for(Barany b : b_list){  ennyi_baranyom_van++;  b.stopRunning(); }}
                synchronized (b_list){for(Sheep b : b_list){  ennyi_baranyom_van++;  b.stopRunning(); }}

                if(playernumber == 1) Field_server.ennyi_baranyod_van(ennyi_baranyom_van);
                if(playernumber == 2) Field_client.ennyi_baranyod_van(ennyi_baranyom_van);
                lock.notifyAll();
                //System.out.println("Player" +playernumber +" - "+ ennyi_baranyom_van);
                frame.dispose();
            }
        }
    }
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------





    // --------------------------  GETTER / SETTER  ----------------------------------
    // -------------------------------------------------------------------------------

    /**
     * A --Field-panel-- -nak ez a függvény küldi el a barikat. Ő ott lemásolja és rajzolja őket.
     * @return Küldi a teljese listát
     */
    public LinkedList<Sheep> getBaranyok() {return b_list; }
    /**
     * A --Field-panel-- -nak ez a függvény küldi el a farkasokat. Ő ott lemásolja és rajzolja őket.
     * @return Küldi a teljese listát
     */
    public LinkedList<Wolf> getFarkasok(){ return f_list;}
    /**
     * A --Field-panel-- -nak ez a függvény küldi el a falakat. Ő ott lemásolja és rajzolja őket.
     * @return Küldi a teljese listát
     */
    public LinkedList<Wall> getFalak(){ return fal_list; }
    /**
     * A palya x dimenziója
     * @return dimenzió
     */
    public static int getPalyameret_x() {return palyameret_x;}
    /**
     * A palya y dimenziója
     * @return dimenzió
     */
    public static int getPalyameret_y() {return palyameret_y;}

    /**
     * --Palya-kliens-- és --Palya-szerver-- állítja be, ha például a kapcsolat megszakad. Hogy álljon le a játék.
     * Vagy ha valaki őket bezárásra utasítja. Például, ha kapnak egy "game-over" üzenetet egy másik játékostól
     * @param allitas true érték mindig, de azért a lehetőséget meghagyom... :)
     */
    public void setGame_over(boolean allitas) { this.game_over = allitas;}

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------






    // ----------------------------  MOUSE LISTENER  --------------------------------- (FALAK MIATT)
    // -------------------------------------------------------------------------------

    /**
     * Nem használom
     * @param e the event to be processed
     */
    @Override public void mouseClicked(MouseEvent e) {}

    /**
     * Falak hozzáadására ezt használom. Valamiért sokkal jobb, mint a mouseClicked() ... Elvileg mind1, melyik gombot nyomod meg.
     * @param e the event to be processed
     */
    @Override public void mousePressed(MouseEvent e) {
        if(!game_over){
            int x = e.getX();
            int y = e.getY();

            if(fal_list.size()<max_hanydb_fal){
                Wall f1 = new Wall(x,y);
                synchronized (falak_monitor) { fal_list.add(f1); }
            }
        }
    }
    /**
     * Nem használom
     * @param e the event to be processed
     */
    @Override public void mouseReleased(MouseEvent e) {}
    /**
     * Nem használom
     * @param e the event to be processed
     */
    @Override public void mouseEntered(MouseEvent e) {}
    /**
     * Nem használom
     * @param e the event to be processed
     */
    @Override public void mouseExited(MouseEvent e) {}
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------





    //  ------------------------  PRIVATE VALTOZOK JATEKHOZ  -------------------------
    // -------------------------------------------------------------------------------
    private LinkedList<Sheep> b_list =  new LinkedList<>(); // mondanom sem kell de ez fényévekkel gyorsabb, mint ArrayList... elképesztő
    private LinkedList<Wolf> f_list =  new LinkedList<>();
    private LinkedList<Wall> fal_list =  new LinkedList<>();
    private Object farkas_monitor = new Object(); // ezek a monitorok a Field és Field_monitor közti
    private Object barany_monitor = new Object(); // harcot vívja meg... Meg néha amikor mozgást végzi
    private Object falak_monitor = new Object(); // a Field, akkor a ciklus közepén jön egy új bárány
    private Object lock; // ez a lock a Palya_szerver / Palya_kliens és Field között harcol. Mert
    // külömben előbb elkűldi a Palya_vmi, hogy hány báránya van, mielőtt megszámolta volna .
    private static int max_hanydb_fal = 30;
    protected static final int palyameret_x = 500;
    protected static final int palyameret_y = 500;
    private JFrame frame = new JFrame();
    private int playernumber; // 1 - SERVER, 2 - KLIENS
    private Field_Panel panel;
    public boolean game_over; // def :  false
    private int ennyi_baranyom_van=0; // ide számolja a végén, hogy mennyi van és küldi el a másik játékosnak

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
}
