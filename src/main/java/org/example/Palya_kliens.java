package org.example;

import javax.swing.plaf.PanelUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.String.valueOf;

public class Palya_kliens implements Runnable{
    public Palya_kliens(String host) throws IOException {
        clientSocket = new Socket(host,PORT_NUMBER);
    }


    protected Socket clientSocket;
    protected static int PORT_NUMBER = 19999;
    protected static String host;










    public void close() throws IOException {clientSocket.close();}






    /// ------------------- GETTER / SETTER ----------------------------------------------------
    /// ----------------------------------------------------------------------------------------
    public static String getHost() {return host;  }
    public static void setHost(String host) {Palya_kliens.host = host;  }
    public static String getPORT_NUMBER() { return valueOf(PORT_NUMBER);  }
    public static void setPORT_NUMBER(int newport) { PORT_NUMBER = newport;  }
    /// ----------------------------------------------------------------------------------------
    /// ----------------------------------------------------------------------------------------






    public void run() {
        try {
            WaitingFrame frame = new WaitingFrame(2);
            //frame.setVisible(true);

            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Failed to communicate with server!");
        }
    }



    /// ----------------------------------------------------------------------------------------
    /// ----------------------------------------------------------------------------------------




    public static void main(String[] args) {
        try {
            // ezzál már lehet csatlakozni másik gépre, LAN-on woooooow
            // new Thread(new JokeClient("192.168.249.31")).start();
            new Thread(new Palya_kliens("localhost")).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}





