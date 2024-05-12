package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawWallsTask implements ActionListener {
    private Field field;

    public DrawWallsTask(Field _field) {
        this.field = _field;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(!field.fal_list.isEmpty()){
            for(Falak f : field.fal_list){
                field.label1 = new JLabel();
                field.label1.setBounds((int) f.hely.x, (int) f.hely.y, 5,5);
                System.out.println((int) f.hely.x + " , " +  (int) f.hely.y);
            }
            System.out.println("nem ures");
        }
        else{
            System.out.println("Ures a falak");
        }
        System.out.println("fasza");

        field.repaint();
    }
}
