package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public final class Field  extends JFrame  implements MouseListener, Runnable {
    protected static final Color palya_szine = new Color(0, 128, 60);

    public static int getPalyameret_x() {return palyameret_x;}
    public static int getPalyameret_y() {return palyameret_y;}

    protected static final int palyameret_x = 500;
    protected static final int palyameret_y = 500;
    protected JFrame frame = new JFrame();
    protected int playernumber;
    protected Label label1;



    Field(int _playernumber) {
        playernumber = _playernumber;
        if(playernumber==1)label1 = new Label("localhost : "+Palya.getPORT_NUMBER() );
        if(playernumber==2)label1 = new Label( Palya_kliens.getHost()+" : "+Palya_kliens.getPORT_NUMBER() );


        frame.setTitle("PLAYER -  " + playernumber);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(palyameret_x,palyameret_y);
        frame.getContentPane().setBackground( palya_szine );
        frame.setLayout(null);
        frame.setVisible(true);
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



    @Override
    public void run() {
        /*try {
            this.wait(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        frame.repaint();
    }
}
