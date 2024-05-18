package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePortFrame extends JFrame implements ActionListener {
    private final JFrame frame = new JFrame();
    private final JLabel label = new JLabel("hello");
    private final JButton button3 = new JButton("exit");
    private final JButton button1 = new JButton("add ip");
    private final JButton button2 = new JButton("add port");
    private final JTextField textField1 = new JTextField();
    private final JTextField textField2 = new JTextField();
    protected int  playernumber;

    /**
     * Itt lehet IP  illetve PORT számot változtatni.
     * @param _playernumber Szerver oldalon nem megengedett az IP cím változtatása, lehetőséget se adok rá.
     */
    ChangePortFrame(int _playernumber){
        playernumber = _playernumber;
        label.setBounds(0,0,100,50);
        label.setFont(new Font(null,Font.PLAIN,25));

        button2.addActionListener(this);
        button3.addActionListener(this);

        // HA nem a szerver, mert neki nem itt lehet IP-t valtoztatni. Az fura lenne
        if(playernumber != 1){
            button1.addActionListener(this);
            textField1.setPreferredSize(new Dimension(250,50));
            textField1.setText(Palya_kliens.getHost());
        }


        textField2.setPreferredSize(new Dimension(250,50));

        if(playernumber == 1)       {   textField2.setText(Palya.getPORT_NUMBER());         }
        else if(playernumber != 2)  {  textField2.setText(Palya_kliens.getPORT_NUMBER());   }


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        setLayout(new GridLayout(1, 2, 10, 10));
        frame.setLayout(new FlowLayout());


        if(playernumber != 1)  frame.add(button1);
                               frame.add(button2);
                               frame.add(button3);
        if(playernumber != 1)  frame.add(textField1);
                               frame.add(textField2);

        if(playernumber ==1) frame.setLocation(200, 200);
        if(playernumber ==2) frame.setLocation(800, 200);
        frame.setTitle("PLAYER -  " + playernumber);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Nézi a függvény, hogy a Frame-en hova kattittontunk
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        /// ------------------------- BUTTON 1 ------------------------------------
        if(e.getSource()==button1) {
            if(playernumber == 1) System.out.println("Player 1 nem valtothat ip itt.");
            else{
                Palya_kliens.setHost(textField1.getText());
            }
        }

        /// ------------------------- BUTTON 2 ------------------------------------
        else if(e.getSource()==button2){
            String input_data = textField2.getText();
            if(isInteger(input_data).equals(" "))  {  textField2.setText("Ez nem valid port"); }
            else{
                int number = Integer.parseInt(input_data);
                if(playernumber == 1)       Palya.setPORT_NUMBER(number);
                else                        Palya_kliens.setPORT_NUMBER(number);
            }
        }

        /// ------------------------- BUTTON 3 ------------------------------------
        else if(e.getSource()==button3){
            frame.dispose();
            new WaitingFrame(playernumber);
        }
    }


    /**
     * Amikor PORT - ot szeretne változtatni, vizsgálja a függvény, hogy tényleg integer-e és nem engedi megváltoztatni, ha nem az.
     * Ezt jelzi a felhasználónak egy furcsa módon.
     * @param str A felhasználó által beállított PORT
     * @return Bool érték, hogy int helyes e a Regex-e
     */
    public static String isInteger(String str) {
        if (str.isEmpty() || !str.matches("^-?\\d+$")) {
            return " ";
        }
        return str; // String is a valid integer
    }
}
