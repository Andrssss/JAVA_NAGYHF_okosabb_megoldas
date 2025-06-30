package org.example.Field_components;
import org.example.Frames.Ending_Frame;
import org.example.Frames.WaitingFrame;
import org.example.Player;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;


public class Field_server extends Player implements Runnable , Serializable{ // Ő LESZ BAL OLDALT

    Field_server(Socket _clientSocket, WaitingFrame w1, Object _lock, Field _myPalya) throws IOException {
        myPalya = _myPalya;
        lock = _lock;
        Myw1 =w1;
        kuldott_game_overt = false;

        myField = new org.example.Frames.Field(1,lock);

        clientSocket = _clientSocket;
        clientWriter = new PrintWriter(clientSocket.getOutputStream()); //

        if (clientSocket != null) {
            myField_thread = new Thread(myField);
            myField_thread.setPriority(10);
            myField_thread.start();
        }
        //this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        //this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
    }













    /// ---------------------------------   RUN      -------------------------------------------
    /// ----------------------------------------------------------------------------------------
    /**
     * Ha a kapcsolat létrejött, akkor figyel a beérkező üzenetekre, kéri a --Field-- -et, hogy adja hozzá a pályájához az állatokat.
     * létrehozza a --Field-- -jét és ha kap egy "game_over" üzenetet,
     * akkor meghívja a végeredményekkel a végső kijelzőt.
     */
    public void run() {
        System.out.println("Palya_szerver fut");
        try {
            if(clientSocket.isConnected()) {
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
                        myField_thread.join(); // hogy egyszerre ne tudjon több csaklakozni

                        // kapott uzenet ugy nez ki, hogy "game_over 2", ahol a szam a masik jatekos baranyait jelent
                        int masik_baranyai = convert_StringMessege_to_int(kapott_allat);
                        running = false;

                        synchronized (lock){
                            //while (baranyaim==-1) lock.wait();
                            if(baranyaim==-1) lock.wait(500);
                            new Ending_Frame(baranyaim,masik_baranyai,playernumber);
                            //System.out.println("Palya_szerver : ÉnBaranyaim-  "+  baranyaim + "  KliensBaranyai-  "+masik_baranyai);
                        }

                        // todo
                        // todo
                        if(myPalya != null) myPalya.close();
                        else {System.err.println("Palya_szerver : myPalya is null");}
                        break;
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
                // mindenképp be kell zárni Bufferreadert
                serverInput.close();
            }
        } catch (SocketException e){           // KAPCSOLAT MEGSZAKADT
            myField.setGame_over(true);
            System.err.println("Palya_szerver : kapcsolat megszakadt");
            running = false;
            new Ending_Frame(-1,-1,playernumber);
            myPalya.close();
            this.close();
        }catch (Exception e) {
            System.err.println("Palya_szerver : Exception ERROR");
            throw new RuntimeException(e);
        }
        try{

                if(!kuldott_game_overt) ennyi_baranyod_van(baranyaim);
                synchronized (lock) {lock.wait(500);}
                clientSocket.close();
                System.out.println("PLAYER1       - CLOSE SOKET: " + clientSocket.getPort());

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

    /**
     * --Palya-- hívja meg, ha nem sikerűlt csatlakozni
     * run() hívja meg, ha "game_over" -t kap.
     */
    public void close(){
               try {
            myField.setGame_over(true);
            running = false;
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    /**
     * Azért van rá szükség, hogy biztosan elküldje a game_over üzenetet. Enélkül néha előbb állt le a szál, minthogy küldött volna.
     */
    private static void setKuldott_game_overt(){ kuldott_game_overt = true;}


    /**
     * A --Field-- -től kapunk egy számot, hogy mennyi bárányunk van. Ha a játéknak vége.
     * Ez a függvény ez is küldi a másik játékosnak, az eredményt.
     * @param ennyi_baranyom_van
     */
    public static void ennyi_baranyod_van(int ennyi_baranyom_van){
        setKuldott_game_overt();
        baranyaim = ennyi_baranyom_van;
        try {
            sendLine("game_over"+baranyaim);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Ezt a függvényt a --Field-- használja állatok küldésére. "static" mert amúgyse terveztem ezt a játékot, több mint 2 játékosra és így sok sor kódot megspórolt.
     * Továbbá az ennyi_baranyod_van() függvény hívja meg, küldére.
     * @param line Az elküldendő üzenet
     * @throws IOException Ha idő közben socket -el valami történt.
     */
    public static void sendLine(String line) throws IOException {
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
    private final org.example.Frames.Field myField;
    private final Object lock;
    private static WaitingFrame Myw1;
    private static int baranyaim = -1;
    private boolean running = true;
    private Thread myField_thread;
    private final Field myPalya;
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------

    /**
     * Hogy Main-ből hívható legyen.
     */
    public static void Palya_szerver_inditas() {
        Myw1 = new WaitingFrame(playernumber);
    }


    /**
     * Saját main
     * @param args isten se tudja micsoda
     */
    public static void main(String[] args) {
        Myw1 = new WaitingFrame(playernumber);

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
