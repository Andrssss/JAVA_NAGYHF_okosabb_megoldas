package org.example;
import java.io.*;
import java.net.Socket;


public class Palya_szerver implements Runnable , Serializable{ // Ő LESZ BAL OLDALT
    Palya_szerver(Socket _clientSocket, WaitingFrame w1,Object _lock) throws IOException {
        lock = _lock;
        Myw1 =w1;
        myField = new Field(1,true,lock);

        clientSocket = _clientSocket;
        clientWriter = new PrintWriter(clientSocket.getOutputStream()); //

        if (clientSocket != null) {
            myField_thread = new Thread(myField);
            myField_thread.start();
        }
        //this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        //this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
    }













    /**
     * Minden beszelgetesnek kulon szala van. 2 dolog van amit vedeni kell a tobbszalusagtol -> check_out_item() & addnewitem()
     */
    /// ---------------------------------   RUN      -------------------------------------------
    /// ----------------------------------------------------------------------------------------
    public void run() {
        System.out.println("Palya_szerver fut");
        try {
            //BufferedReader serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            if(clientSocket.isConnected()) {

                //new Thread(myField).run();
                if(Myw1!= null)  Myw1.close();


                System.out.println("PLAYER1       - KAPCSOLAT ELINDULT A : " + clientSocket.getPort() + " -AL");

                BufferedReader serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                while (running) {
                    String kapott_allat = serverInput.readLine();
                    System.out.println("Kliens Uzenet : "+ kapott_allat);
                    if (kapott_allat == null || kapott_allat.isEmpty()) {
                        //System.out.println("Kliens : Uzenet ures");
                        //Thread.sleep(1000);
                    }
                    else if (kapott_allat.startsWith("game_over")) {
                        // kapott uzenet ugy nez ki, hogy "game_over 2", ahol a szam a masik jatekos baranyait jelent
                        int masik_baranyai = convert_StringMessege_to_int(kapott_allat);
                        running = false;

                        synchronized (lock){
                            lock.wait(100);
                            new Ending_Frame(baranyaim,masik_baranyai,playernumber);
                            System.out.println("KLIENS : ÉnBaranyaim-  "+  baranyaim + "  KliensBaranyai-  "+masik_baranyai);
                        }
                        break;
                        //  todo --- itt nem szabad bezárni
                        //clientSocket.close();
                    }else {
                        System.out.println("Kliens 1 "+ kapott_allat);


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
                //


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











    // ------------------------------ FÜGGVENYEK  ------------------------------------
    // -------------------------------------------------------------------------------
    public void close(){
        try {
            myField.setGame_over(true);
            running = false;
            clientSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private int convert_StringMessege_to_int(String kapott_allat){
        String remainingText = kapott_allat.substring(9);
        int number=0;
        try {
            number = Integer.valueOf(remainingText);
        } catch (NumberFormatException e) {
            System.err.println("Invalid integer input");
        }
        return number;
    }
    protected static void ennyi_baranyod_van(int ennyi_baranyom_van){
        baranyaim = ennyi_baranyom_van;
        try {
            sendLine("game_over"+baranyaim);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
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

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------






    //  ------------------------  PRIVATE VALTOZOK JATEKHOZ  -------------------------
    // -------------------------------------------------------------------------------
    private static Socket clientSocket;
    private static final int playernumber = 1;
    private static final int PORT_NUMBER= 19999;
    protected static PrintWriter clientWriter;
    private Field myField=null;
    private final Object lock;
    private static WaitingFrame Myw1;
    private static int baranyaim = -1;
    private boolean running = true;
    private Thread myField_thread;
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------


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
