package org.example;
import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Palya_szerver implements Runnable , Serializable{ // Ő LESZ BAL OLDALT
    protected Socket clientSocket;
    protected BufferedReader clientReader;
    protected static PrintWriter clientWriter;
    Field myField;

    private static String send_messege;
    public static synchronized void send_animal(String send_this){
        send_messege = send_this;

        // todo
        // todo
        // todo
        // maga a kuldest
    }

    //private ObjectInputStream inputStream;
    //private ObjectOutputStream outputStream;

    Palya_szerver(Socket clientSocket, Field f1) throws IOException {
        myField = f1;
        this.clientSocket = clientSocket;
        this.clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.clientWriter = new PrintWriter(clientSocket.getOutputStream()); //
        //this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        //this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
    }











    /**
     * Minden beszelgetesnek kulon szala van. 2 dolog van amit vedeni kell a tobbszalusagtol -> check_out_item() & addnewitem()
     */
    /// ---------------------------------   RUN      -------------------------------------------
    /// ----------------------------------------------------------------------------------------
    public void run() {
        try {
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            if(clientSocket.isConnected()) {
                System.out.println("PLAYER1       - KAPCSOLAT ELINDULT A : " + clientSocket.getPort() + " -AL");

                while(true){
                    String kapott_allat = serverInput.readLine();
                    if (kapott_allat == null || kapott_allat.isEmpty()){System.out.println("Uzenet ures");
                    Thread.sleep(1000);}
                    else{
                        // TODO -----------------------------------------------------------------------
                        // TODO -----------------------------------------------------------------------
                        // EZ MÉG CSAK JOBBRA MŰKÖDIK
                        if(kapott_allat.substring(0,6).equals("Barany")){
                            int number = convert_StringMessege_to_int(kapott_allat);
                            Barany b = new Barany(0,number);
                            b.setGazdi(1);
                            myField.addBarany(b);
                        }
                        else if(kapott_allat.equals("Farkas")){
                            int number = convert_StringMessege_to_int(kapott_allat);
                            Farkas b = new Farkas(0,number);
                            b.setGazdi(1);
                            myField.addFarkas(b);
                        }else {
                            System.out.println("Palya_szerver kapott uzenet : " + kapott_allat);
                        }
                        // itt kell elinditani a jatekot
                        // TODO -----------------------------------------------------------------------
                        // TODO -----------------------------------------------------------------------
                    }

                }



                ///Thread receiverThread = new Thread(this::receiveObjects);
                ///receiverThread.start();
                ///receiverThread.join();


                //Thread senderThread = new Thread(this::sendObjects);
                //senderThread.start();
                //senderThread.join();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try{
            clientSocket.close();
            System.out.println("PLAYER1       - CLOSE SOKET: " + clientSocket.getPort());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /// ----------------------------------------------------------------------------------------
    /// ----------------------------------------------------------------------------------------



    protected static void sendLine(String line) throws IOException {
        clientWriter.print(line + "\r\n");
        clientWriter.flush();
    }
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

    public static void main(String[] args) {
        new WaitingFrame(1);
    }
}


/*

public void sendObjects(Allat sendthis) {
        try {
            // Message objektumok küldése a kliensnek

                Allat message = sendthis;
                outputStream.writeObject(message);
                outputStream.flush();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void receiveObjects() {
        try {
            // Message objektumok fogadása a kliensről
            while (true) {
                Allat receivedMessage = (Allat) inputStream.readObject();
                System.out.println("Message received from client: " + receivedMessage.gazdi);
                // todo
                // todo
                // todo
                // bezarni a kapcsolatot
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
 */
