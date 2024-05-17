package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ending_Frame extends JFrame implements ActionListener {
    protected Label label1;
    protected JButton button1;
    protected JButton button2;
    protected JFrame frame = new JFrame();
    protected int playernumber;


    Ending_Frame(int en_baranyaim, int masik_baranyai ,int _playernumber){
        String szoveg;
        if(en_baranyaim>masik_baranyai)      szoveg = "Winner";
        else if(en_baranyaim<masik_baranyai) szoveg = "Loser";
        else                                 szoveg = "Egual";

        playernumber = _playernumber;
        label1 = new Label( "You are the : " + szoveg);
        label1.setBounds(100,60,150,15);
        //label2 = new Label( "ready to connect");

        //button1 = new JButton("Retry");
        //button1.setBounds(25,100,200,50);
        //button1.addActionListener(this);


        button2 = new JButton("exit");
        button2.setBounds(25,100,200,50);
        button2.addActionListener(this);
        // todo----------------
        // todo----------------
        //frame.add(button1);
        frame.add(button2);
        // todo ez okozhat bajt
        // todo----------------
        frame.add(label1);
        //frame.add(label2);

        //if(playernumber ==1) frame.setLocation(100, 100);
        if(playernumber ==2) frame.setLocation(700, 100);
        frame.setTitle("PLAYER -  " + playernumber);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        /*if(e.getSource()==button1){
            if(playernumber==1) // todo valami  ha szerver, akkor a wárakozás player 2 -re kell
            new WaitingFrame(playernumber);
            Palya.running = false;
            frame.dispose();
        }*/
        if(e.getSource()==button2){
            if(playernumber == 1) Palya.running = false;
            if(playernumber == 2) Palya_kliens.running = false;
            frame.dispose();
        }
    }
}
