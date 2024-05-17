package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class WaitingFrame extends JFrame implements ActionListener {
    private Label label1;
    private final Label label2;
    private JButton button1;
    private final JButton button2;
    private final JButton button3;
    private final JFrame frame = new JFrame();
    private final int playernumber;
    private static String messege = " ";
    private Palya MyPalya = null;








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

        button3 = new JButton("Exit");
        button3.setBounds(250,170,200,50);
        button3.addActionListener(this);

        frame.add(button1);
        frame.add(label1);
        frame.add(label2);
        frame.add(button2);
        frame.add(button3);
        if(playernumber ==1) frame.setLocation(100, 100);
        if(playernumber ==2) frame.setLocation(700, 100);
        frame.setTitle("PLAYER -  " + playernumber);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLayout(null);
        frame.setVisible(true);
    }







    public static void setMessege(String mess){  messege = mess;  }
    public void close(){  frame.dispose();  }





    private void KLIENS_PROBAL_CSATLAKOZNI() {
        Object _lock= new Object();
        Palya_kliens p1 = new Palya_kliens("localhost",_lock);
        Thread t1 = new Thread(p1);
        if(messege.equals("success")){
            t1.start();
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
        Object _lock2= new Object();
        MyPalya = new Palya(this,_lock2);
        new Thread(MyPalya).start();

        // WAITING -------------------------
            button1.setVisible(false);
            button2.setVisible(false);
            label2.setVisible(false);
            label1.setText("Waiting for Player2");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // EZ A CSATLAKOZO GOMB ------------------------------------------
        if(e.getSource()==button1){
            switch (playernumber) {
                case 1:
                    try {  this.SZERVER_INDUL(); }
                    catch (InterruptedException ex) {  this.reconnect();  }
                    break;
                case 2:
                    this.KLIENS_PROBAL_CSATLAKOZNI();
                    break;
                default:
                    System.out.println("Valami felre ment");
            }
        }
        // EZ A IP / PORT VALTOZTATO GOMB  -------------------------------
        else if(e.getSource()==button2){
            frame.dispose();
            new ChangePortFrame(playernumber);
        }
        // EZ PEDIG AZ EXIT ----------------------------------------------
        else if(e.getSource()==button3){
            if(MyPalya != null) {
                MyPalya.close();
            }
            Palya.running = false;
            frame.dispose();
        }
    }
}
