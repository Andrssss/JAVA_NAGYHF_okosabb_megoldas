package org.example;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

import static java.awt.SystemColor.window;

public class Palya_szerver implements Runnable {
    protected Socket clientSocket;
    protected BufferedReader clientReader;
    protected PrintWriter clientWriter;




    //----------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------
    // Ez a monitor biztositja a szalakat. Beiras es Kivetel nem tortenhet egyszerre !!!
    // (vagy egyszerre tobb szalon)
    private final static  Object monitor_tarolohoz = new Object();
    //----------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------







    /**
     * KONSTRUKTOR
     * @param clientSocket Bejovo kereseket kapja a Tarolo_szervertol. Hogy kit kell kiszolgalni az adott szalon
     * @throws IOException Tarolo_szerver kapja el.
     */
    Palya_szerver(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.clientWriter = new PrintWriter(clientSocket.getOutputStream()); //
    }


    /**
     * Minden beszelgetesnek kulon szala van. 2 dolog van amit vedeni kell a tobbszalusagtol -> check_out_item() & addnewitem()
     */
    public void run() {
        try {
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            
            if(clientSocket.isConnected()) {
                System.out.println("PLAYER1       - KAPCSOLAT ELINDULT A : " + clientSocket.getPort() + " -AL");
                // itt kell elinditani a jatekot
                // todo
            }
            else{
                // itt kell a varakozo képernyőt elindítani

            }

        }
        catch (java.net.SocketTimeoutException ste) {
            // Itt fut le, ha timeout történik
            System.out.println("PLAYER1       - TIMEOUT");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try{
            clientSocket.close();
            System.out.println("PLAYER1       - CLOSE SOKET: " + clientSocket.getPort());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





    public static void main(String[] args) {
        //WaitingFrame frame = new WaitingFrame(1);

        // ha button2-t megnyomod akkor start palya thread

        new Thread(new Palya()).start();
    }

}