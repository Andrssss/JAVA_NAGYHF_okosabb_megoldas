package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class WaitingFrame extends JFrame implements ActionListener {
    protected Label label1;
    protected TextField text;
    protected JButton button1;
    protected JButton button2;
    protected JFrame frame = new JFrame();
    protected int playernumber;



    public WaitingFrame(int _playernumber){
        playernumber = _playernumber;

        if(playernumber==1)label1 = new Label("localhost : "+Palya.getPORT_NUMBER() );
        if(playernumber==2)label1 = new Label( Palya_kliens.getHost()+" : "+Palya_kliens.getPORT_NUMBER() );
        label1.setBounds(100,60,150,15);
        //label2 = new Label(Palya.getPORT_NUMBER());
        //label2.setBounds(100,60,100,10);

        button1 = new JButton("Wait in def port: HERE");
        button1.setBounds(25,100,200,50);
        button1.addActionListener(this);

        button2 = new JButton("Change IP and/or PORT");
        button2.setBounds(250,100,200,50);
        button2.addActionListener(this);

        frame.add(button1);
        frame.add(label1);
        frame.add(button2);
        frame.setTitle("PLAYER -  " + playernumber);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLayout(null);
        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button1){
            // SZERVER -----------------------------------------------------
            // VÁRAKOZO KÉPERNYŐ --> PÁLYA   (AHOL VAN GAME OVER)
            if(playernumber==1) {
                Field f1 = new Field(1);
                f1.run();
            }


            // KLIENS -----------------------------------------------------
            // ha sikerűl, akkor PÁLYA,
            // ha nem, akkor várokijelzőn, hogy nem sikerült a connection
            if(playernumber==2) {

            }


        }
        else if(e.getSource()==button2){
            frame.dispose();
            ChangePortFrame myWindow = new ChangePortFrame(playernumber);
        }
    }
}
