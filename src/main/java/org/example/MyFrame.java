package org.example;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyFrame extends JFrame implements MouseListener {
    protected JLabel label;
    protected Button button1;
    MyFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setLayout(null);


        label = new JLabel();
        label.setBounds(0,0,500,500);
        label.setBackground(Color.GREEN);
        label.setOpaque(true);
        label.addMouseListener(this);

        //button1 = new Button("Default port: "+ Palya.PORT_NUMBER);


        this.add(label);
        this.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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



    public void changeColour(String color){
        if(color.equals("green"))  label.setBackground(Color.green);
        else if(color.equals("black"))  label.setBackground(Color.black);
    }







}
