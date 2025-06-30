package org.example.Frames;

import org.example.Field_components.Field;
import org.example.Field_components.Field_client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


/**
 * Ez a Frame a főablak lesz, innen lehet elindítani a csatlakozást, PORT/IP-t valtoztatni, vagy kilépni
 */
public class WaitingFrame extends JFrame implements ActionListener {
    private Label label1;
    private final Label label2;
    private JButton button1;
    private final JButton button2;
    private final JButton button3;
    private final JFrame frame = new JFrame();
    private final int playernumber;
    private static String messege = " ";
    private Field MyPalya = null;


    /**
     * KONSTRUKTOR
     * @param _playernumber Ez a cím miatt fontos és azért mert másképp kezeljük a SZERVERT és a KLIENST. És máshova is hozzuk létre az ablakot.
     */
    public WaitingFrame(int _playernumber){
        playernumber = _playernumber;

        if(playernumber==1)label1 = new Label("localhost : "+ Field.getPORT_NUMBER() );
        if(playernumber==2)label1 = new Label( Field_client.getHost()+" : "+ Field_client.getPORT_NUMBER() );
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


    /**
     * Ezt arra használom, hogy megnézzem, hogy a --Palya_kliens-- -nek sikerült-e csatlakozni, mert ha nem akkor pár dolgot változtatok
     * pl: "Csatlakozás" -> "retry"
     * @param mess A --Palya_kliens-- küldi, hogy sikerült-e csatlakozni
     */
    public static void setMessege(String mess){  messege = mess;  }

    /**
     * Saját magát hívja meg. Becsukja a Frame-et
     */
    public void close(){  frame.dispose();  }


    /**
     * Ha playernumber == 2, akkor kliens típusú csatlakozásról van szó.
     */
    private void KLIENS_PROBAL_CSATLAKOZNI() {
        //Object _lock= new Object();
        //Palya_kliens p1 = new Palya_kliens("localhost",_lock);
        Field_client p1 = new Field_client();
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


    /**
     * Ha a szerver csatlakozásra vár, ott van time out és ezt kiírja a GUI-ra, ha megtörténik
     */
    public void timeout(){
        label1.setText("ERROR : timeout (100sec)");
    }

    /**
     * Ha playernumber == 1, akkor kliens típusú csatlakozásról van szó. Ha kliens még nem csatlakozott, akkor "Waiting for Player2"
     * és megvárja még csatlakozik.
     */
    private void SZERVER_INDUL() throws InterruptedException {
        Object _lock2= new Object();
        MyPalya = new Field(this,_lock2);
        new Thread(MyPalya).start();

        // WAITING -------------------------
            button1.setVisible(false);
            button2.setVisible(false);
            label2.setVisible(false);
            label1.setText("Waiting for Player2");
    }


    /**
     * Nézi, hogy melyik gombot nyomta meg a Felhasználó
     * @param e the event to be processed
     */
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
            Field.running = false;
            frame.dispose();
        }
    }
}
