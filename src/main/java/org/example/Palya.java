package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import static java.lang.String.valueOf;

public class Palya implements Runnable{
    public Palya(WaitingFrame w1,Object _lock)  {
        System.out.println("Palya : letrehozas");
        timeout = 100000;
        running=true;
        lock = _lock;
        myw1 =w1;
        clientSocket = null;



        try {
            serverSocket = new ServerSocket(PORT_NUMBER);
        }catch (IOException e){
            e.printStackTrace();
        }
    }




    public void run() {
        System.out.println("Palya : fut");
        try {
            int csak_1_kapcsolat = 0;
            serverSocket.setSoTimeout(timeout);


            //while(running){
                System.out.println("Palya : ciklusban fut");
                if (serverSocket != null) {
                    try {
                        System.out.println("Palya : kliens elfogadas");
                        csak_1_kapcsolat++;
                        clientSocket = serverSocket.accept(); // itt elfogadja a kérést és nyit egy socketet
                        MyPalya_szerver = new Palya_szerver(clientSocket,myw1,lock,this);
                        new Thread(MyPalya_szerver).run();
                        running = false;

                    } catch (java.net.SocketTimeoutException ste) {
                        // Itt fut le, ha timeout történik
                        System.err.println("Palya       - TIMEOUT");
                        //break; // A fő ciklust leállítjuk a timeout után
                    } catch (IOException e) {
                        System.err.println("Palya       - Failed to communicate with Kliens!");
                    }
                }
                else{
                    System.err.println("Palya       - Server socket is not empty");

                }
            //}
        } catch (SocketException e) { throw new RuntimeException(e); }
        try {
            System.out.println("Palya : socket close");
            serverSocket.close();
        } catch (IOException e) { e.printStackTrace(); }
        System.out.println("Palya       - CLOSED");
    }






    // ------------------------------ FÜGGVENYEK  ------------------------------------
    // -------------------------------------------------------------------------------
    public static String getPORT_NUMBER() {
        return valueOf(PORT_NUMBER);
    }
    public static void setPORT_NUMBER(int newport) {
        PORT_NUMBER = newport;
    }
    public void close() {
        running = false;
        System.out.println("MyPalya_szerver : "+ MyPalya_szerver );
        if(MyPalya_szerver != null ){
            System.out.println("MyPalya_szerver nem null");
            MyPalya_szerver.close();
        }
        try {
            serverSocket.close();
            //clientSocket.close(); // todo , nem fasza, mert ekkor még el se fogadott ilyet
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------







    //  ------------------------  PRIVATE VALTOZOK JATEKHOZ  -------------------------
    // -------------------------------------------------------------------------------
    protected ServerSocket serverSocket;
    protected  Socket clientSocket ;
    private static int PORT_NUMBER = 19999;
    private WaitingFrame myw1;
    private Object lock;
    private static int timeout ;
    public static boolean running; // todo getter // setter
    private Palya_szerver MyPalya_szerver = null;
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
}
