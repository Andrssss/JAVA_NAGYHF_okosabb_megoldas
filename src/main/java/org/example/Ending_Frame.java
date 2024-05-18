package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ending_Frame extends JFrame implements ActionListener {
    protected Label label1;
    protected JButton button2;
    protected JFrame frame = new JFrame();
    protected int playernumber;

    /**
     * Nincs túl komplikálva. A játék lefutása után kiírja, hogy nekünk és neki hány báránya van és aszerint itélkezik
     * @param en_baranyaim Én bárányaim
     * @param masik_baranyai Ő bárányai, lányok nem játszanak ezért His
     * @param _playernumber A cím miatt fontos csak. nem vizsgálunk vele semmit
     */
    Ending_Frame(int en_baranyaim, int masik_baranyai ,int _playernumber){

        if(en_baranyaim == -1 && masik_baranyai == -1 ){
            label1 = new Label("Kapcsolat megszakadt");
        }
        else{
            String szoveg;
            if(en_baranyaim>masik_baranyai)      szoveg = "Winner";
            else if(en_baranyaim<masik_baranyai) szoveg = "Loser";
            else                                 szoveg = "Egual";
            label1 = new Label( "You are the : " + szoveg + "( Mine : " + en_baranyaim + "  His : " + masik_baranyai+" )");
        }

        playernumber = _playernumber;
        label1.setBounds(100,60,250,15);


        button2 = new JButton("exit");
        button2.setBounds(25,170,200,50);
        button2.addActionListener(this);

        frame.add(button2);
        frame.add(label1);

        if(playernumber ==1) frame.setLocation(100, 100);
        if(playernumber ==2) frame.setLocation(700, 100);
        frame.setTitle("PLAYER -  " + playernumber);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLayout(null);
        frame.setVisible(true);
    }


    /**
     * Figyeli, hogy hova kattintottunk a Frame-en
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button2){
            if(playernumber == 1) {
                Palya.running = false;
                System.out.println("Ending_Frame : Palya running false");
            }
            if(playernumber == 2) {
                Palya_kliens.running = false;
                System.out.println("Ending_Frame : Palya_kliens running false");
            }
            frame.dispose();
        }
    }
}
