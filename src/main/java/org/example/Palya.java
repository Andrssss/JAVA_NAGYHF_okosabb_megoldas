package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import static java.lang.String.valueOf;

public class Palya implements Runnable{
    protected ServerSocket serverSocket;
    protected  Socket clientSocket ;




    private static int PORT_NUMBER = 19999;
    public static String getPORT_NUMBER() {
        return valueOf(PORT_NUMBER);
    }
    public static void setPORT_NUMBER(int newport) {
        PORT_NUMBER = newport;
    }

    private static int timeout ;




    /**
     * KONSTUKTOR
     */
    public Palya()  {
        try {
            clientSocket = null;
            serverSocket = new ServerSocket(PORT_NUMBER);
            timeout = 100000;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Close socket
     */
    public void close() {
        try{
            serverSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * Parhuzamosan futtathato programresz
     */
    public void run() {
        try {
            serverSocket.setSoTimeout(timeout);

            while (!Thread.currentThread().isInterrupted()) {
                //System.out.println("To shut down shop : --close--, To open up : --open-- (def:open)");
                // EZT IRANA KI MINDIG AMIKOR UJ SOCKETET NYIT



                if (serverSocket != null) {

                    // TODO
                    // EZ AKKOR NYILIK MEG, HA SIKERÜLT A CONNECTION
                    if(clientSocket == null)  {
                        //WaitingFrame frame = new WaitingFrame(1);
                    }


                    try {
                        clientSocket = serverSocket.accept(); // itt elfogadja a kérést és nyit egy socketet
                        new Palya_szerver(clientSocket).run();

                    } catch (java.net.SocketTimeoutException ste) {
                        // Itt fut le, ha timeout történik
                        System.out.println("SERVER       - TIMEOUT");
                        break; // A fő ciklust leállítjuk a timeout után
                    } catch (IOException e) {
                        System.err.println("SERVER       - Failed to communicate with RAKTAR!");
                    }
                }
                else{
                    System.out.println("SERVER       - Server socket is not empty");
                }

            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("SERVER       - CLOSED");
    }
}
