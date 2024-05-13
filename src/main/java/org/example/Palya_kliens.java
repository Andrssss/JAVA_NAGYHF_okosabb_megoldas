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


    public Palya_kliens(String host1,Field f1) {
        myField = f1;
        host = host1;


        try{
            System.out.println(PORT_NUMBER);
            clientSocket = new Socket("localhost",PORT_NUMBER);
            clientWriter = new PrintWriter(clientSocket.getOutputStream());
        }catch (IOException e){
            System.err.println("fail to connect");
            WaitingFrame.setMessege("fail");
        }

        if (clientSocket != null) {
            System.out.println("Kliens : clientSocket NOT null");
            WaitingFrame.setMessege("success");
        }
        else {
            System.out.println("Kliens : clientSocket null");
            WaitingFrame.setMessege("fail");
        }
    }


    protected static Socket clientSocket;
    protected static int PORT_NUMBER=19999;
    protected static String host="localhost";
    protected static PrintWriter clientWriter;









    public void close() throws IOException {clientSocket.close();}






    /// ------------------- GETTER / SETTER ----------------------------------------------------
    /// ----------------------------------------------------------------------------------------
    public static String getHost() {return host;}
    public static void setHost(String host) {Palya_kliens.host = host;  }
    public static String getPORT_NUMBER() { return valueOf(PORT_NUMBER);  }
    public static void setPORT_NUMBER(int newport) { PORT_NUMBER = newport;  }
    /// ----------------------------------------------------------------------------------------
    /// ----------------------------------------------------------------------------------------





    public void run() {

        System.out.println("Kliens socket : "+ clientSocket.getPort()+ " " + PORT_NUMBER );

        if (clientSocket != null) {
            try {
                BufferedReader serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while (true) {
                    String kapott_allat = serverInput.readLine();
                    System.out.println("Kliens Uzenet : "+ kapott_allat);
                    if (kapott_allat == null || kapott_allat.isEmpty()) {
                        System.out.println("Kliens : Uzenet ures");
                        //Thread.sleep(1000);
                    }
                    else if (kapott_allat.equals("game_over")) {
                        clientSocket.close();
                        return;
                    }else {
                        // TODO -----------------------------------------------------------------------
                        // TODO -----------------------------------------------------------------------
                        // EZ MÉG CSAK JOBBRA MŰKÖDIK

                        if (kapott_allat.substring(0, 6).equals("Barany")) {
                            int number = convert_StringMessege_to_int(kapott_allat);
                            Barany b = new Barany(0, number);
                            b.setGazdi(1);
                            myField.addBarany(b,number);
                        } else if (kapott_allat.equals("Farkas")) {
                            int number = convert_StringMessege_to_int(kapott_allat);
                            Farkas b = new Farkas(0, number);
                            b.setGazdi(1);
                            myField.addFarkas(b,number);
                        } else {
                            System.out.println("Palya_szerver kapott uzenet : " + kapott_allat);
                        }
                        // itt kell elinditani a jatekot
                        // TODO -----------------------------------------------------------------------
                        // TODO -----------------------------------------------------------------------
                    }

                }
            }
            catch (IOException e){
            //catch (IOException | InterruptedException e){
                e.printStackTrace();
            }
            //Listener_Thread listener = new Listener_Thread(clientSocket);
            //listener.run();
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
            System.out.println(remainingText);

            int number=0;

            try {
                number = Integer.valueOf(remainingText);
                System.out.println("Converted integer: " + number);

                number = Integer.valueOf(remainingText);
                System.out.println("Converted integer: " + number);
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer input");
            }
            return number;
        }
    protected Field myField=null;
    public void setMyField(Field f1){
        myField = f1;
    }
    public static void main(String[] args) {
        new WaitingFrame(2);
    }

    protected static void sendLine(String line) throws IOException {
        System.out.println("Kliens send : "+ line + " to : " +clientSocket.getInetAddress()+" : "+ PORT_NUMBER);
        clientWriter.print(line + "\r\n");
        clientWriter.flush();
    }

}




