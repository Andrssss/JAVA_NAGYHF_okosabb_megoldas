package org.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import static java.lang.String.valueOf;

public class Palya_kliens implements Runnable{
    protected Palya_kliens(String host1,Object _lock) {
        lock = _lock;
        Field f1 = new Field(2,true ,lock);
        myField = f1;
        host = host1;
        kuldott_game_overt = false;



        try{
            clientSocket = new Socket("localhost",PORT_NUMBER);
            clientWriter = new PrintWriter(clientSocket.getOutputStream());
        }catch (IOException e){
            System.err.println("fail to connect");
            WaitingFrame.setMessege("fail");
        }

        if (clientSocket != null) {
            System.out.println("KLIENS : CSATLAKOZOTT");
            WaitingFrame.setMessege("success");
            myField_thread = new Thread(myField);
            myField_thread.start();
        }
        else {
            System.err.println("Kliens : clientSocket null");
            WaitingFrame.setMessege("fail");
        }
    }






















    /// --------------------------------       RUN     -----------------------------------------
    /// ----------------------------------------------------------------------------------------
    public void run() {

        System.out.println("Kliens socket : "+ clientSocket.getPort()+ " " + PORT_NUMBER );

        if (clientSocket != null) {
            try {
                BufferedReader serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while (running) {

                    String kapott_allat = serverInput.readLine();


                    if (kapott_allat == null || kapott_allat.isEmpty()) {
                        //System.out.println("Kliens : Uzenet ures");
                        //Thread.sleep(1000);
                    }
                    else if (kapott_allat.startsWith("game_over")) {
                        System.out.println("Palya_kliens : KAPOTT game_over-t");
                        // kapott uzenet ugy nez ki, hogy "game_over 2", ahol a szam a masik jatekos baranyait jelent
                        int masik_baranyai = convert_StringMessege_to_int(kapott_allat);
                        running = false;
                        close();
                        synchronized (lock){
                            lock.wait(1000);
                            new Ending_Frame(baranyaim,masik_baranyai,playernumber);
                            //System.out.println("KLIENS : ÉnBaranyaim-  "+  baranyaim + "  KliensBaranyai-  "+masik_baranyai);
                        }
                        break;
                        //  todo --- itt nem szabad bezárni
                        //clientSocket.close();
                    }else {
                        //System.out.println("Kliens 1 "+ kapott_allat);


                        if (kapott_allat.substring(0, 6).equals("Barany")) {
                            String[] szavak = kapott_allat.split(" ");
                            int x = Integer.parseInt(szavak[1]);
                            int y = Integer.parseInt(szavak[2]);
                            myField.addBarany(x,y);
                        } else if (kapott_allat.substring(0,6).equals("Farkas")) {
                            String[] szavak = kapott_allat.split(" ");
                            int x = Integer.parseInt(szavak[1]);
                            int y = Integer.parseInt(szavak[2]);
                            myField.addFarkas(x,y);
                        } else {
                            System.err.println("KLIENS kapott uzenet : " + kapott_allat);
                        }
                    }
                }

            }catch (SocketException e){
                myField.setGame_over(true);
                System.err.println("Palya_kliens : kapcsolat megszakadt");
                running = false;
                new Ending_Frame(-1,-1,playernumber);
            }
            catch (IOException e){
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                synchronized (lock){
                    if(!kuldott_game_overt) lock.wait(1000);

                    clientSocket.close();
                    System.out.println("PLAYER2       - CLOSE SOKET: " + clientSocket.getPort());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /// ----------------------------------------------------------------------------------------
    /// ----------------------------------------------------------------------------------------







    /// ------------------- GETTER / SETTER ----------------------------------------------------
    /// ----------------------------------------------------------------------------------------
    protected static String getHost() {return host;}
    protected static void setHost(String host) {Palya_kliens.host = host;  }
    protected static String getPORT_NUMBER() { return valueOf(PORT_NUMBER);  }
    protected static void setPORT_NUMBER(int newport) { PORT_NUMBER = newport;  }
    protected void close() throws IOException {clientSocket.close();  myField.setGame_over(true); running= false;}
    // todo ------------
    // todo ------------
    // todo ------------
    // todo ------------
    // todo ------------
    /// ----------------------------------------------------------------------------------------
    /// ----------------------------------------------------------------------------------------




    /// ---------------------------- PRIVATE FUGGVENYEK  ---------------------------------------
    /// ----------------------------------------------------------------------------------------
    private int convert_StringMessege_to_int(String kapott_allat){
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
    protected static void ennyi_baranyod_van(int ennyi_baranyom_van){
        kuldott_game_overt = true;
        baranyaim = ennyi_baranyom_van;
        try {
            sendLine("game_over"+baranyaim);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    protected static void sendLine(String line) throws IOException {
        //System.out.println("Kliens send : "+ line + " to : " +clientSocket.getInetAddress()+" : "+ PORT_NUMBER);
        clientWriter.print(line + "\r\n");
        clientWriter.flush();
    }
    /// ----------------------------------------------------------------------------------------
    /// ----------------------------------------------------------------------------------------


    //  ------------------------  PRIVATE VALTOZOK JATEKHOZ  -------------------------
    // -------------------------------------------------------------------------------
    private static Socket clientSocket;
    private static int PORT_NUMBER=19999;
    private static String host="localhost";
    private static PrintWriter clientWriter;
    private static final int playernumber = 2;
    private Object lock;
    private Field myField=null;
    private static int baranyaim = -1;
    public static boolean running = true;
    private Thread myField_thread;
    private static boolean kuldott_game_overt;

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------






    public static void Palya_kliens_inditas() {

        WaitingFrame w1 = new WaitingFrame(2);
    }
    public static void main(String[] args) {
        WaitingFrame w1 = new WaitingFrame(playernumber);

        while(true){
            int activeThreads = Thread.activeCount();
            System.out.println("Jelenleg futó szálak : "+ activeThreads);
            if(activeThreads == 2) break ;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

}




