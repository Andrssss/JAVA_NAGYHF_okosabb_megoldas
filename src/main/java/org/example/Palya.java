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

    Field myField ;
    WaitingFrame myw1;
    Object lock;


    /**
     * KONSTUKTOR
     */
    public Palya(Field f1,WaitingFrame w1,Object _lock)  {
        try {
            lock = _lock;
            myw1 =w1;
            myField = f1;
            clientSocket = null;
            serverSocket = new ServerSocket(PORT_NUMBER);
            timeout = 100000;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private Object Mylock = new Object();
    /**
     * Close socket
     */
    public void close() {
                running = false;
                if(MyPalya_szerver != null )  MyPalya_szerver.close();
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean running = true;
    private Palya_szerver MyPalya_szerver = null;
    /**
     * Parhuzamosan futtathato programresz
     */
    public void run() {
        System.out.println("Palya fut");
        try {
            serverSocket.setSoTimeout(timeout);


                while(running){
                    System.out.println("Palya fut");
                        if (serverSocket != null) {

                            // TODO
                            // EZ AKKOR NYILIK MEG, HA SIKERÜLT A CONNECTION
                            if(clientSocket == null)  {
                                //WaitingFrame frame = new WaitingFrame(1);
                            }


                            try {
                                clientSocket = serverSocket.accept(); // itt elfogadja a kérést és nyit egy socketet
                                MyPalya_szerver = new Palya_szerver(clientSocket,myField,myw1,lock);
                                new Thread(MyPalya_szerver).run();

                                //new Thread(MyPalya_szerver).run();
                                //if(!running) break;
                            } catch (java.net.SocketTimeoutException ste) {
                                // Itt fut le, ha timeout történik
                                System.err.println("SERVER       - TIMEOUT");
                                break; // A fő ciklust leállítjuk a timeout után
                            } catch (IOException e) {
                                System.err.println("SERVER       - Failed to communicate with RAKTAR!");
                            }
                        }
                        else{
                            System.err.println("SERVER       - Server socket is not empty");

                    }

                }


        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println("SERVER : socket close");
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("SERVER       - CLOSED");
    }
}
