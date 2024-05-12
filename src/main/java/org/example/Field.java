package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.TimerTask;


public final class Field  extends JFrame  implements MouseListener, Runnable {
    protected ArrayList<Barany> b_list =  new ArrayList<>();
    protected ArrayList<Farkas> f_list =  new ArrayList<>();
    protected ArrayList<Falak> fal_list =  new ArrayList<>();

      public ArrayList<Barany> getBaranyok(){
          return b_list;
      }
    public ArrayList<Farkas> getFarkasok(){
        return f_list;
    }
    public ArrayList<Falak> getFalak(){
        return fal_list;
    }







    Field_Panel panel;

    private class DrawWallsTask extends TimerTask {
        @Override
        public void run() {
            // Falak kirajzolása
            for (Falak f : fal_list) {
                 System.out.println(f.hely.x+" - "+ f.hely.y);
            }

            // Át kell rajzolni a frame-t
            frame.repaint();
        }
    }



    @Override
    public void run() {
        /*try{
            while(!game_over){
                if(!fal_list.isEmpty()){
                    for(Falak f : fal_list){
                        label1 = new JLabel();
                        label1.setBounds((int) f.hely.x, (int) f.hely.y, 5,5);
                        System.out.println((int) f.hely.x + " , " +  (int) f.hely.y);
                    }
                    System.out.println("nem ures");
                }
                else{
                    System.out.println("Ures a falak");
                }
                System.out.println("fasza");
                // todo
                // itt kell majd a falakat a bárányoknak adni, mert ugye a falak folyamatosan változnak

                frame.repaint();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/

    }


















    Field(int _playernumber,boolean readyornot) {
        game_over = false;
        cur_hanydb_fal=0;
        readyToRockAndRoll = readyornot;
        playernumber = _playernumber;
        /*timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!fal_list.isEmpty()){
                    System.out.println("nem ures");
                    WallsPanel wallsPanel = new WallsPanel(fal_list);

                    // Clear the existing content of the frame
                    //frame.getContentPane().removeAll();

                    // Add the WallsPanel to the frame
                    frame.getContentPane().add(wallsPanel);

                    // Repaint the frame
                    frame.repaint();
                    /*for(Falak f : fal_list){
                        label1 = new JLabel();
                        label1.setBounds((int) f.hely.x, (int) f.hely.y, 5,5);
                        //System.out.println((int) f.hely.x + " , " +  (int) f.hely.y);
                        frame.add(label1);
                    }
                    System.out.println("nem ures");*/
             /*   }
                else{
                    System.out.println("Ures a falak");
                }
                //frame.repaint();
            }
        });*/

        //timer = new Timer(1000, new DrawWallsTask(this));
        //timer.start(); // Az időzítő elindítása




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
        //frame.setSize(palyameret_x,palyameret_y);
        //frame.getContentPane().setBackground( palya_szine );
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);


        Allatok_inditasa();
    }



    public void setReadyToRockAndRoll(boolean _readyToRockAndRoll) {
        this.readyToRockAndRoll = _readyToRockAndRoll;
        System.out.println(readyToRockAndRoll);
        frame.repaint();
    }

    private void Allatok_inditasa(){
        if(readyToRockAndRoll) {
            //timer.start();

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


    /*@Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw walls
        for (Falak f : fal_list) {
            g.fillRect((int) f.hely.x, (int) f.hely.y, 5, 5); // Adjust size as needed
            System.out.println("Clicked at: " + f.hely.x + ", " + f.hely.y );
        }
    }*/

























    private Timer timer;
    private boolean readyToRockAndRoll;

    private static int max_hanydb_fal = 20;
    private  int cur_hanydb_fal;
    protected static final Color palya_szine = new Color(0, 128, 60);
    public static int getPalyameret_x() {return palyameret_x;}
    public static int getPalyameret_y() {return palyameret_y;}
    protected static final int palyameret_x = 500;
    protected static final int palyameret_y = 500;
    protected JFrame frame = new JFrame();
    protected int playernumber;
    protected JLabel label1;
    protected boolean game_over;





    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if(cur_hanydb_fal<max_hanydb_fal){
            Falak f1 = new Falak(x,y);
            fal_list.add(f1);
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
