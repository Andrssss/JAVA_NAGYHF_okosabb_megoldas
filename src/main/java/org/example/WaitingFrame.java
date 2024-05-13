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
    static String messege = " ";
    private static Field f1;
    // nem fasza, hiba forras

    public static void setMessege(String mess){
        messege = mess;
    }

    private Palya_kliens p;





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
        if(playernumber ==1) frame.setLocation(100, 100);
        if(playernumber ==2) frame.setLocation(700, 100);
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



    private void KLIENS_PROBAL_CSATLAKOZNI() {
        Field f2 = new Field(2,true);
        //Palya_kliens kliens = new Palya_kliens("localhost",f2);
        Thread t1 = new Thread(new Palya_kliens("localhost",f2));
        if(messege.equals("success")){
            // HA EZT ITT NEM TESZED MEG, AKKOR 1 SZÁLUNK FUT EZ MEG A KLIENS MEG A FIELD XDDD
            // JAVA EZ MI A FASZOM, LEHET ÉN VAGYOK A BUTA DE FÉL KURVA NAPOM ELMENT EZZEL
            t1.start();
            // new Thread(new Palya_kliens("localhost",f2)).start();
            f2.run();
            frame.dispose();
        }else{
            reconnect();
        }
    }

    public void reconnect(){
        System.err.println("Nem sikerult csatlakozni a szerverhez.");
        button1.setText("Reconnect");
        label2.setText("Erros cant connect");
    }





    private void SZERVER_INDUL() throws InterruptedException {
        /*if(messege.equals("success")){
            System.out.println("XXXX");
            f1 = new Field(1,true);
            f1.run();
            frame.dispose();
        }*/
        // WAITING -------------------------
        //else{
            //button1.setVisible(false);
            //button2.setVisible(false);
            //label2.setVisible(false);
            //label1.setText("Waiting for Player2");
            f1 = new Field(1,true);
            new Thread(new Palya(f1)).start();


            // Az istenért nem akar működni
            // todo
            // todo
            //this.f1 = new Field(1,false); ---> ez az eredeti
            f1.run();
            frame.dispose();
            // todo
            // todo
            /// -----------------------------------------------------
            /// ------------ide kell vmi ami figyeli    -------------
            /// ------------hogy csatlakozott-e a masik -------------
            /// -----------------------------------------------------
        //}
    }


}
