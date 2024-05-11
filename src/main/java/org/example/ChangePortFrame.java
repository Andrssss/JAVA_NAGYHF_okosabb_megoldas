package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePortFrame extends JFrame implements ActionListener {
    JFrame frame = new JFrame();
    JLabel label = new JLabel("hello");
    JButton button3 = new JButton("exit");
    JButton button1 = new JButton("add ip");
    JButton button2 = new JButton("add port");
    JTextField textField1 = new JTextField();
    JTextField textField2 = new JTextField();
    protected int  playernumber;
    ChangePortFrame(int _playernumber){
        playernumber = _playernumber;
        label.setBounds(0,0,100,50);
        label.setFont(new Font(null,Font.PLAIN,25));


        button2.addActionListener(this);
        button3.addActionListener(this);
        //JButton button2 = new JButton("add port");
        //JButton button3 = new JButton("exit");



        if(playernumber == 2){
            button1.addActionListener(this);
            //JButton button1 = new JButton("add ip");
            textField1.setPreferredSize(new Dimension(250,50));
            textField1.setText(Palya_kliens.getHost());
            System.out.println(Palya_kliens.getHost());
        }


        textField2.setPreferredSize(new Dimension(250,50));

        if(playernumber == 1){
            textField2.setText(Palya.getPORT_NUMBER());
        } else if(playernumber == 2){
            textField2.setText(Palya_kliens.getPORT_NUMBER());


        }




        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);

        setLayout(new GridLayout(1, 2, 10, 10)); // 1 row, 2 columns, 10px horizontal gap, 10px vertical gap

        frame.setLayout(new FlowLayout());

        if(playernumber == 2)  frame.add(button1);
        frame.add(button2);
        frame.add(button3);
        if(playernumber == 2)   frame.add(textField1);
        frame.add(textField2);


        frame.setTitle("PLAYER -  " + playernumber);
        frame.pack();
        frame.setVisible(true);
    }


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


            if(isInteger(input_data).equals(" ")){
                textField2.setText(String.valueOf("Ez nem valid port"));
            }
            else{
                int number = Integer.parseInt(input_data);

                if(playernumber == 1)       Palya.setPORT_NUMBER(number);
                else if(playernumber == 2)  Palya_kliens.setPORT_NUMBER(number);
            }
        }

        /// ------------------------- BUTTON 3 ------------------------------------
        else if(e.getSource()==button3){
            frame.dispose();
            WaitingFrame myWindow = new WaitingFrame(playernumber);
        }
    }


    public static String isInteger(String str) {
        if (str.isEmpty() || !str.matches("^-?\\d+$")) {
            return " ";
        }
        return str; // String is a valid integer
    }
}
