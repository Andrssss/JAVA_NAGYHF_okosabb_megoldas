package org.example;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;


public class Palya_szerver implements Runnable , Serializable{ // Ő LESZ BAL OLDALT
    Palya myPalya = null ;
    Palya_szerver(Socket _clientSocket, WaitingFrame w1,Object _lock,Palya _myPalya) throws IOException {
        myPalya = _myPalya;
        lock = _lock;
        Myw1 =w1;
        kuldott_game_overt = false;

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
                    if (kapott_allat == null || kapott_allat.isEmpty()) {
                        //System.out.println("Kliens : Uzenet ures");
                        //Thread.sleep(1000);
                    }
                    else if (kapott_allat.startsWith("game_over")) {
                        System.out.println("Palya_szerver : KAPOTT game_over-t");
                        // kapott uzenet ugy nez ki, hogy "game_over 2", ahol a szam a masik jatekos baranyait jelent
                        int masik_baranyai = convert_StringMessege_to_int(kapott_allat);
                        running = false;

                        synchronized (lock){
                            lock.wait(1000);
                            new Ending_Frame(baranyaim,masik_baranyai,playernumber);
                            //System.out.println("Palya_szerver : ÉnBaranyaim-  "+  baranyaim + "  KliensBaranyai-  "+masik_baranyai);
                        }

                        // todo
                        // todo
                        if(myPalya != null) myPalya.close();
                        else {System.err.println("Palya_szerver : myPalya is null");}
                        // todo
                        // todo
                        break;
                        //  todo --- itt nem szabad bezárni
                        //clientSocket.close();
                    }else {
                        //System.out.println("Palya_szerver : "+ kapott_allat);


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
                            System.err.println("Palya_szerver kapott uzenet : " + kapott_allat);
                        }
                    }
                }
                //


            }

        } catch (SocketException e){
            myField.setGame_over(true);
            System.err.println("Palya_szerver : kapcsolat megszakadt");
            running = false;
            new Ending_Frame(-1,-1,playernumber);
        }catch (Exception e) {
            System.err.println("Palya_szerver : Exception ERROR");
            throw new RuntimeException(e);
        }
        try{
            synchronized (lock){
                if(!kuldott_game_overt) lock.wait(1000);

                clientSocket.close();
                System.out.println("PLAYER1       - CLOSE SOKET: " + clientSocket.getPort());
            }
        } catch (IOException e) {
            System.err.println("Palya_szerver : Exception ERROR");
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    /// ----------------------------------------------------------------------------------------
    /// ----------------------------------------------------------------------------------------











    // ------------------------------ FÜGGVENYEK  ------------------------------------
    // -------------------------------------------------------------------------------
    public void close(){
        //todo     kuldott_game_overt
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

    private static void setKuldott_game_overt(){ kuldott_game_overt = true;}
    protected static void ennyi_baranyod_van(int ennyi_baranyom_van){
        setKuldott_game_overt() ;
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
    private static boolean kuldott_game_overt;
    private Field myField=null;
    private final Object lock;
    private static WaitingFrame Myw1;
    private static int baranyaim = -1;
    private boolean running = true;
    private Thread myField_thread;
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------


    public static void Palya_szerver_inditas() {

        WaitingFrame w1 = new WaitingFrame(playernumber);
        Myw1 = w1;
    }

    public static void main(String[] args) {
        WaitingFrame w1 = new WaitingFrame(playernumber);
        Myw1 = w1;

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
