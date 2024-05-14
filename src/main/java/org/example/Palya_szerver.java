package org.example;
import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Palya_szerver implements Runnable , Serializable{ // Ő LESZ BAL OLDALT
    protected static Socket clientSocket;

    private static final int playernumber = 1;
    private static int PORT_NUMBER= 19999;
    protected BufferedReader clientReader;
    protected static PrintWriter clientWriter;
    Field myField;
    //private ObjectInputStream inputStream;
    //private ObjectOutputStream outputStream;

    Palya_szerver(Socket _clientSocket, Field f1, WaitingFrame w1) throws IOException {
        Myw1 =w1;
        myField = f1;
        new Thread(myField).start();
        this.clientSocket = _clientSocket;
        this.clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        clientWriter = new PrintWriter(clientSocket.getOutputStream()); //
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
            //BufferedReader serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            if(clientSocket.isConnected()) {
                if(Myw1!= null)Myw1.kapcsolat_indul();

                System.out.println("PLAYER1       - KAPCSOLAT ELINDULT A : " + clientSocket.getPort() + " -AL");

                while(true){
                    String kapott_allat = clientReader.readLine();

                    if (kapott_allat == null || kapott_allat.isEmpty()){
                        //System.out.println("Szerver : Uzenet ures");
                        //Thread.sleep(1000);
                    }
                    else if (kapott_allat.startsWith("game_over")) {
                        // kapott uzenet ugy nez ki, hogy "game_over 2", ahol a szam a masik jatekos baranyait jelent
                        int masik_baranyai = convert_StringMessege_to_int(kapott_allat);
                        new Ending_Frame(baranyaim,masik_baranyai,playernumber);
                        clientSocket.close();
                        return;
                    }
                    else{
                        // TODO -----------------------------------------------------------------------
                        // TODO -----------------------------------------------------------------------
                        // EZ MÉG CSAK JOBBRA MŰKÖDIK
                        if(kapott_allat.substring(0,6).equals("Barany")){
                            //int number = convert_StringMessege_to_int(kapott_allat);
                            //myField.addBarany(number);
                            String[] szavak = kapott_allat.split(" ");
                            int x = Integer.parseInt(szavak[1]);
                            int y = Integer.parseInt(szavak[2]);
                            myField.addBarany(x,y);
                        }
                        else if(kapott_allat.substring(0,6).equals("Farkas")){
                            //int number = convert_StringMessege_to_int(kapott_allat);
                            //myField.addFarkas(number);
                            String[] szavak = kapott_allat.split(" ");
                            int x = Integer.parseInt(szavak[1]);
                            int y = Integer.parseInt(szavak[2]);
                            myField.addFarkas(x,y);
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
        // NULLPOINTER ERROR
        if(clientSocket!=null){
            System.out.println("Szerver send : "+ line + " to : " +clientSocket.getInetAddress()+":"+ PORT_NUMBER+" - "+ clientSocket.getPort());
            clientWriter.print(line + "\r\n");
            clientWriter.flush();
        }else{
            System.err.println("Server : Clientsocket is null");
        }
    }

    private static int baranyaim = 0;
    private static int baranyai_a_masiknak = 0;
    protected static void ennyi_baranyod_van(int ennyi_baranyom_van){
        baranyaim = ennyi_baranyom_van;
        try {
            sendLine("game_over"+baranyaim);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // "Barany - "
    // "game_over"
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


    private static WaitingFrame Myw1;
    public static void main(String[] args) {
        WaitingFrame w1 = new WaitingFrame(playernumber);
        Myw1 = w1;
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
