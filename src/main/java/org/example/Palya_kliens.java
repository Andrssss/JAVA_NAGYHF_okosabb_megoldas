package org.example;
import java.io.IOException;
import java.net.Socket;
import static java.lang.String.valueOf;
public class Palya_kliens implements Runnable{ // Ő VAN JOBB OLDALT
    protected boolean jobbra_atmehet = false;
    protected boolean balra_atmehet  = true ;





    protected Socket clientSocket;
    protected static int PORT_NUMBER = 19999;
    protected static String host;
    protected boolean connected;


    public Palya_kliens(String _host) throws IOException {
        if(_host.isEmpty()) _host = "localhost";
        this.host = _host;
        connected = false;
        System.out.println("2 - konstruktor lefut");
    }









    /// ------------------- GETTER / SETTER ----------------------------------------------------
    /// ----------------------------------------------------------------------------------------
    public static String getHost() {return Palya_kliens.host;  }
    public static void setHost(String host) {Palya_kliens.host = host;  }
    public static String getPORT_NUMBER() { return valueOf(PORT_NUMBER);  }
    public static void setPORT_NUMBER(int newport) { PORT_NUMBER = newport;  }
    /// ----------------------------------------------------------------------------------------
    /// ----------------------------------------------------------------------------------------




    /// ---------------------------------   RUN      -------------------------------------------
    /// ----------------------------------------------------------------------------------------
    public void run() {
        try {
            //while (!connected) { // EZ SPAMEL, NEM FASZA
            System.out.println("2 - csatlakozas elindul");
                try {
                    clientSocket = new Socket(host, PORT_NUMBER);
                    connected = true;
                    System.out.println("Connected to server: " + host);
                    System.out.println("2 - success elkuldes");
                    //synchronized (WaitingFrame.messege){
                        WaitingFrame.setMessege("success");
                      //  notify();
                    //}


                } catch (IOException e) {
                    System.err.println("Failed to connect to server: " + host);
                    System.out.println("2 - fail elkuldes");
                    WaitingFrame.setMessege("fail");
                }
            //}


            //setMessege
            if(clientSocket != null)  clientSocket.close();
        } catch (IOException e) {
            System.err.println("Failed to communicate with server!");
        }
    }
    private void close() throws IOException {clientSocket.close();  connected = false;}

    /// ----------------------------------------------------------------------------------------
    /// ----------------------------------------------------------------------------------------

    public static void main(String[] args) throws IOException {
        Palya_kliens p = new Palya_kliens("localhost");
        new WaitingFrame(2);
    }
}





