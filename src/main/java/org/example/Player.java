package org.example;


import org.example.Frames.Field;

import java.net.Socket;

public abstract class Player {
    protected Object lock;
    protected Field myField;
    protected Thread myField_thread;
    protected Socket clientSocket;





    /**
     * Amikor megkapja a "game_over 6" üzenetet, akkor abból a "6" a másik bárányai. Ez a függvény ezt a számot vágja le.
     * @param kapott_allat "game_over 6" szerű üzenetek
     * @return A szám, hogy másiknak mennyi báránya van az üzenet alapján.
     */
    protected int convert_StringMessege_to_int(String kapott_allat){
        String remainingText = kapott_allat.substring(9);
        int number=0;
        try {
            number = Integer.valueOf(remainingText);

        } catch (NumberFormatException e) {
            System.err.println("Invalid integer input");
        }
        System.out.println("Kliens :  szerver baranyai : " + number);
        return number;
    }
}
