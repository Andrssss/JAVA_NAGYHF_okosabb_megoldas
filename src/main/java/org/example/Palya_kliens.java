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


    public Palya_kliens(String host1,Field f1,Object _lock) {
        myField = f1;
        //myField.setLock(lock);
        lock = _lock;
        host = host1;


        try{
            System.out.println(PORT_NUMBER);
            clientSocket = new Socket("localhost",PORT_NUMBER);
            this.clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientWriter = new PrintWriter(clientSocket.getOutputStream());
            //Thread.sleep(1000);
        }catch (IOException e){
            System.err.println("fail to connect");
            WaitingFrame.setMessege("fail");
        //} catch (InterruptedException e) {
          //  throw new RuntimeException(e);
        }

        if (clientSocket != null) {

            System.err.println("Kliens : clientSocket NOT null");
            WaitingFrame.setMessege("success");
            MyField_thread = new Thread(myField);
            MyField_thread.start();
        }
        else {
            System.err.println("Kliens : clientSocket null");
            WaitingFrame.setMessege("fail");
        }
    }


    protected static Socket clientSocket;
    protected static int PORT_NUMBER=19999;
    protected static String host="localhost";
    protected static PrintWriter clientWriter;
    protected BufferedReader clientReader;
    private static final int playernumber = 2;
    private Thread MyField_thread;
    Object lock = new Object();









    public void close() throws IOException {clientSocket.close();}






    /// ------------------- GETTER / SETTER ----------------------------------------------------
    /// ----------------------------------------------------------------------------------------
    public static String getHost() {return host;}
    public static void setHost(String host) {Palya_kliens.host = host;  }
    public static String getPORT_NUMBER() { return valueOf(PORT_NUMBER);  }
    public static void setPORT_NUMBER(int newport) { PORT_NUMBER = newport;  }
    protected static void ennyi_baranyod_van(int ennyi_baranyom_van){
        baranyaim = ennyi_baranyom_van;
        try {
            sendLine("game_over"+baranyaim);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /// ----------------------------------------------------------------------------------------
    /// ----------------------------------------------------------------------------------------


    private static int baranyaim = -1;



    public void run() {

        System.out.println("Kliens socket : "+ clientSocket.getPort()+ " " + PORT_NUMBER );

        if (clientSocket != null) {
            try {
                BufferedReader serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while (true) {
                    String kapott_allat = serverInput.readLine();
                    //String kapott_allat = clientReader.readLine();
                    System.out.println("Kliens Uzenet : "+ kapott_allat);
                    if (kapott_allat == null || kapott_allat.isEmpty()) {
                        //System.out.println("Kliens : Uzenet ures");
                        //Thread.sleep(1000);
                    }
                    else if (kapott_allat.startsWith("game_over")) {
                        // kapott uzenet ugy nez ki, hogy "game_over 2", ahol a szam a masik jatekos baranyait jelent
                        int masik_baranyai = convert_StringMessege_to_int(kapott_allat);



                        // todo --- ennek meg kell várnia, míg a Field kihal
                        //MyField_thread.join();
                        synchronized (lock){
                            lock.wait(100);
                            new Ending_Frame(baranyaim,masik_baranyai,playernumber);
                            System.out.println("KLIENS : ÉnBaranyaim-"+  baranyaim + "  KliensBaranyai-"+masik_baranyai);
                        }
                        break;
                        //  todo --- itt nem szabad bezárni
                        //clientSocket.close();
                    }else {
                        System.out.println("Kliens 1 "+ kapott_allat);
                        // TODO -----------------------------------------------------------------------
                        // TODO -----------------------------------------------------------------------
                        // EZ MÉG CSAK JOBBRA MŰKÖDIK

                        if (kapott_allat.substring(0, 6).equals("Barany")) {
                            String[] szavak = kapott_allat.split(" ");
                            int x = Integer.parseInt(szavak[1]);
                            int y = Integer.parseInt(szavak[2]);
                            myField.addBarany(x,y);

                            //System.out.println("Kliens 2 "+ kapott_allat);
                            //int number = convert_StringMessege_to_int(kapott_allat);
                            //System.out.println("Kliens 3 "+ kapott_allat);


                            //System.out.println("Kliens 4 "+ kapott_allat);
                        } else if (kapott_allat.substring(0,6).equals("Farkas")) {
                            String[] szavak = kapott_allat.split(" ");
                            int x = Integer.parseInt(szavak[1]);
                            int y = Integer.parseInt(szavak[2]);
                            myField.addFarkas(x,y);
                            //int number = convert_StringMessege_to_int(kapott_allat);
                            //myField.addFarkas(number);
                        } else {
                            System.err.println("KLIENS kapott uzenet : " + kapott_allat);
                        }
                        // itt kell elinditani a jatekot
                        // TODO -----------------------------------------------------------------------
                        // TODO -----------------------------------------------------------------------
                    }
                    //System.out.println("Kliens 5 "+ kapott_allat);

                }

            }
            catch (IOException e){
            //catch (IOException | InterruptedException e){
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Kliens : true vége");
            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /// ----------------------------------------------------------------------------------------
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
    protected Field myField=null;

    public static void main(String[] args) {
        new WaitingFrame(2);
    }

    protected static void sendLine(String line) throws IOException {
        System.out.println("Kliens send : "+ line + " to : " +clientSocket.getInetAddress()+" : "+ PORT_NUMBER);
        clientWriter.print(line + "\r\n");
        clientWriter.flush();
    }

}




