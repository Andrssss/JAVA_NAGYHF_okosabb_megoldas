package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class WaitingFrame extends JFrame implements ActionListener {
    protected Label label1;
    protected Label label2;
    protected TextField text;
    protected JButton button1;
    protected JButton button2;
    protected JFrame frame = new JFrame();
    protected int playernumber;
    private static String messege = " ";

    public static void setMessege(String mess){
        messege = mess;
    }

    private Palya_kliens p;


    public WaitingFrame(int _playernumber,Palya_kliens new_p){
        this.p= new_p;


        playernumber = _playernumber;

        if(playernumber==1)label1 = new Label("localhost : "+Palya.getPORT_NUMBER() );
        if(playernumber==2)label1 = new Label( Palya_kliens.getHost()+" : "+Palya_kliens.getPORT_NUMBER() );
        label2 = new Label( "ready to connect");
        label1.setBounds(100,60,150,15);
        label2.setBounds(100,190,150,15);


        if(playernumber==1)button1 = new JButton("Wait in def port: HERE");
        if(playernumber==2)button1 = new JButton("Connect to SERVER");
        button1.setBounds(25,100,200,50);
        button1.addActionListener(this);

        button2 = new JButton("Change IP and/or PORT");
        button2.setBounds(250,100,200,50);
        button2.addActionListener(this);

        frame.add(button1);
        frame.add(label1);
        frame.add(label2);
        frame.add(button2);
        frame.setTitle("PLAYER -  " + playernumber);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLayout(null);
        frame.setVisible(true);
    }



    public WaitingFrame(int _playernumber){
        playernumber = _playernumber;

        if(playernumber==1)label1 = new Label("localhost : "+Palya.getPORT_NUMBER() );
        if(playernumber==2)label1 = new Label( Palya_kliens.getHost()+" : "+Palya_kliens.getPORT_NUMBER() );
        label2 = new Label( "ready to connect");
        label1.setBounds(100,60,150,15);
        label2.setBounds(100,190,150,15);


        if(playernumber==1)button1 = new JButton("Wait in def port: HERE");
        if(playernumber==2)button1 = new JButton("Connect to SERVER");
        button1.setBounds(25,100,200,50);
        button1.addActionListener(this);

        button2 = new JButton("Change IP and/or PORT");
        button2.setBounds(250,100,200,50);
        button2.addActionListener(this);

        frame.add(button1);
        frame.add(label1);
        frame.add(label2);
        frame.add(button2);
        frame.setTitle("PLAYER -  " + playernumber);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLayout(null);
        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // EZ A CSATLAKOZO GOMB
        if(e.getSource()==button1){
            switch (playernumber) {
                case 1:
                    try {
                        SZERVER_INDUL();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                case 2:
                    KLIENS_PROBAL_CSATLAKOZNI();
                    break;

                default:
                    System.out.println("Valami felre ment");
            }
        }
        // EZ A IP / PORT VALTOZTATO GOMB
        else if(e.getSource()==button2){
            frame.dispose();
            new ChangePortFrame(playernumber);
        }
    }



    private void KLIENS_PROBAL_CSATLAKOZNI(){
        System.out.println(playernumber + " - csatlakozik");
        try {
            // ezzál már lehet csatlakozni másik gépre, LAN-on woooooow
            // new Thread(new JokeClient("192.168.249.31")).start();

            // TODO
            // EZ ITT AZÉRT FOS, MERT NEM KÉNE LÉTREHOZNI MÉG EGY PÉLDÁNYT, ÍGY 2X FUT LE A KONSTUKTOR
            // RÁ KELL JÖNNI, HOGY HOGYAN TUDOM INNEN INDÍTANI AZ A PÉLDÁNYT, A BAJ CSAK AZ, HOGY NEM TUDOM HOGYAN KELL ÁTADNI
            new Thread(new Palya_kliens("localhost")).start();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        // EZ A SZÜKSÉGES ROSSZ, ENÉLKÜL ELŐBB OLVASNÁ EZ A SZÁL A VÁLASZT, MINTHOGY MEGPROBÁLT VOLNA CSATLAKOZNI
        // KÖSZÖNÖM JAVA A 2 ÓRÁS SZENVEDÉST, UTOLSÓ 2 HÉTBEN ERRE HOGY NE LENNE IDŐ
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(playernumber + " - uzenetet olvas");

        if(messege.equals("success")){
            Field f1 = new Field(playernumber);
            f1.run();
            frame.dispose();
            // ide ér, de a másik ablakot nem csukja be
            // todo
        }else{
            button1.setText("Reconnect");
            label2.setText("Erros cant connect");
        }
    }










    private void SZERVER_INDUL() throws InterruptedException {
        if(messege.equals("success")){
            Field f1 = new Field(1);
            f1.run();
            frame.dispose();
        }
        // WAITING -------------------------
        else{
            //frame.getContentPane().removeAll();
            button1.setVisible(false);
            button2.setVisible(false);
            label2.setVisible(false);
            label1.setText("Waiting for Player2");
            // ezen a ponton várakozik
            // todo
            new Thread(new Palya()).start();


            Field f1 = new Field(1);
            f1.run();
            frame.dispose();
             /// -----------------------------------------------------
             /// ------------ide kell vmi ami figyeli    -------------
             /// ------------hogy csatlakozott-e a masik -------------
             /// -----------------------------------------------------
        }
    }
}

