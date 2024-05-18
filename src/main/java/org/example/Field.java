package org.example;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public final class Field  extends JFrame  implements MouseListener, Runnable {
    Field(int _playernumber,boolean readyornot,Object _lock) {
        lock = _lock;
         READY_TO_SAND = true;
        playernumber = _playernumber;
        game_over = false;
        cur_hanydb_fal=0;
        readyToRockAndRoll = true;

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

    public void End_is_here(){
        System.out.println("Field : End_is_here()");
        System.out.println("Field : game_over :" + game_over);
        if(game_over){
            synchronized (lock){
                System.out.println("Field : belepett a végére");
                for(Barany b : b_list){
                    ennyi_baranyom_van++;
                }
                for(Farkas b : f_list){
                    b.stopRunning();
                }
                for(Barany b : b_list){
                    b.stopRunning();
                }

                if(playernumber == 1) Palya_szerver.ennyi_baranyod_van(ennyi_baranyom_van);
                if(playernumber == 2) Palya_kliens.ennyi_baranyod_van(ennyi_baranyom_van);
                lock.notify();
                //System.out.println("Player" +playernumber +" - "+ ennyi_baranyom_van);
                frame.dispose();
            }
        }
    }






















    // ------------------------------ FÜGGVENYEK  ------------------------------------
    // -------------------------------------------------------------------------------
    public void OsztMeghaltal_e() {
        ArrayList<Barany> remove_b_list = new ArrayList<>();

        synchronized (farkas_monitor) {
            for (Farkas f : f_list) {
                synchronized (barany_monitor) {
                    Iterator<Barany> baranyIterator = b_list.iterator();
                    while (baranyIterator.hasNext()) {
                        Barany b = baranyIterator.next();
                        if ((Math.abs(f.hely.x - b.hely.x - 5) < 6) && (Math.abs(f.hely.y - b.hely.y - 5) < 6)) {
                            b.meghal();
                            remove_b_list.add(b);
                        }
                    }
                }
            }
        }

        synchronized (barany_monitor) {
            for (Barany b : remove_b_list) {
                b_list.remove(b);
            }
        }
    }



    private void Allatok_inditasa(){
        synchronized (lock){
                if(readyToRockAndRoll) {
                    int farkaso_szama = 10;
                    int baranyok_szama = 10;
                    for (int i = 0; i < baranyok_szama; i++) {
                        Barany barany = new Barany();
                        barany.setGazdi(playernumber);
                        b_list.add(barany); // Add Baranyok object to the list

                        //Thread thread = new Thread(barany);
                        //thread.setName("Barany" + (i + 1)); // Set unique thread names
                        //thread.start();
                        barany.start();
                    }
                    for (int i = 0; i < farkaso_szama; i++) {
                        Farkas f = new Farkas();
                        f.setGazdi(playernumber);
                        f_list.add(f); // Add Baranyok object to the list

                        //Thread thread = new Thread(f);
                        //thread.setName("Farkas" + (i + 1)); // Set unique thread names
                        //thread.start();
                        f.start();
                    }
                }
        }
    }

    public void addBarany(int x,int y){
        //System.out.println("Barany - y : " + " Player" + playernumber);
        Barany b = new Barany(x,y);
        b.setGazdi(playernumber);
        synchronized (barany_monitor) {  b_list.add(b);  }
        //b.run(); EZ úgy megszopatott, de úgy .... Már negyedjére XDDDD
        //  szerintem egy teljes napom elment rá.... A tudás hatalom gyermekem
        if(game_over){System.err.println("Barany nem indul, mert game over");}
        else{
            b.start();
            //new Thread(b).start();
        }
    }

    public void addFarkas(int x,int y){
        //System.out.println("Farkas - y : " + " Player" + playernumber);
        Farkas f = new Farkas(x, y);
        f.setGazdi(playernumber);
        synchronized (farkas_monitor){  f_list.add(f);  }
        //f.run();
        if(game_over){System.err.println("Farkas nem indul, mert game over");}
        else{
            f.start();
            //new Thread(f).start();
        }
    }

    public void AllatokMozog(){
        if(!game_over) {
            ArrayList<Barany> remove_b_list = new ArrayList<>();
            ArrayList<Farkas> remove_f_list = new ArrayList<>();

            synchronized (barany_monitor){
                for (Barany b : b_list) {
                    String eredmeny = b.randomMozgas(fal_list,falak_monitor);

                    if (!eredmeny.equals("marad")) {
                        if (eredmeny.equals("jobbra")) {
                            if (b.jobbra_atmehet && b.gazdi == 1 && READY_TO_SAND) {
                                int y_hely = (int) b.hely.y;
                                //b.stopRunning();

                                try {
                                    Palya_szerver.sendLine("Barany "+ 0 +" "+ y_hely);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                remove_b_list.add(b);
                            }
                        } else if (eredmeny.equals("balra")) {
                            if (b.balra_atmehet && b.gazdi == 2 && READY_TO_SAND) {
                                int y_hely = (int) b.hely.y;
                                //b.stopRunning();
                                try {
                                    Palya_kliens.sendLine("Barany "+ (palyameret_y-5) +" "+ y_hely);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                remove_b_list.add(b);
                            }
                        }
                    }
                }
            }

            synchronized (farkas_monitor) {
                for (Farkas b : f_list) {
                    String eredmeny = b.randomMozgas_Farkas(fal_list,f_list,farkas_monitor,falak_monitor);

                    if (!eredmeny.equals("marad")) {
                        if (eredmeny.equals("jobbra")) {
                            if (b.jobbra_atmehet && b.gazdi == 1 && READY_TO_SAND) {
                                int y_hely = (int) b.hely.y;
                                //b.stopRunning();
                                try {
                                    Palya_szerver.sendLine("Farkas " + 0 + " " + y_hely);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                remove_f_list.add(b);
                            }
                        } else if (eredmeny.equals("balra")) {
                            if (b.balra_atmehet && b.gazdi == 2 && READY_TO_SAND) {
                                int y_hely = (int) b.hely.y;
                                //b.stopRunning();
                                try {
                                    Palya_kliens.sendLine("Farkas " + (palyameret_y - 5) + " " + y_hely);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                remove_f_list.add(b);
                            }
                        }
                    }
                }
            }

            synchronized (barany_monitor) {
                for (Barany b : remove_b_list) {
                    b.stopRunning();
                    b_list.remove(b);
                }
            }
            synchronized (farkas_monitor) {
                for (Farkas b : remove_f_list) {
                    b.stopRunning();
                    f_list.remove(b);
                }
            }
        }
        else{  // IF GAME OVER
            // todo megallitani az osszes allat
            // todo osszeszamolni a baranyokat
            // todo elküldeni a Palyanak és ott osszehasonlitani
            //
        }
    }




    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------





    // --------------------------  GETTER / SETTER  ----------------------------------
    // -------------------------------------------------------------------------------

    public ArrayList<Barany> getBaranyok() { return b_list; }
    public ArrayList<Farkas> getFarkasok(){ return f_list;}
    public ArrayList<Falak> getFalak(){ return fal_list; }
    public static int getPalyameret_x() {return palyameret_x;}
    public static int getPalyameret_y() {return palyameret_y;}
    public void    setGame_over(boolean allitas) { this.game_over = allitas;}

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------






    // ----------------------------  MOUSE LISTENER  --------------------------------- (FALAK MIATT)
    // -------------------------------------------------------------------------------
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {
        if(!game_over){
            int x = e.getX();
            int y = e.getY();

            if(fal_list.size()<max_hanydb_fal){
                Falak f1 = new Falak(x,y);
                fal_list.add(f1);
            }
        }
    }
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------





    //  ------------------------  PRIVATE VALTOZOK JATEKHOZ  -------------------------
    // -------------------------------------------------------------------------------
    private ArrayList<Barany> b_list =  new ArrayList<>();
    private ArrayList<Farkas> f_list =  new ArrayList<>();
    private ArrayList<Falak> fal_list =  new ArrayList<>();
    private Object farkas_monitor = new Object();
    private Object barany_monitor = new Object();
    private Object falak_monitor = new Object();
    private Object halottak_monitor = new Object();
    private boolean readyToRockAndRoll;
    public static boolean READY_TO_SAND;
    private static int max_hanydb_fal = 30;
    private  int cur_hanydb_fal;
    protected static final int palyameret_x = 500;
    protected static final int palyameret_y = 500;
    private JFrame frame = new JFrame();
    private int playernumber;
    private Field_Panel panel;
    protected boolean game_over; // def :  false
    private int ennyi_baranyom_van=0;
    private Object lock;
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
}
