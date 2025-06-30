package org.example.Field_components;
import org.example.Frames.Ending_Frame;
import org.example.Frames.Field;
import org.example.Frames.WaitingFrame;
import org.example.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import static java.lang.String.valueOf;


/**
 * Úgy lett mogoldva a kapcsolat, hogy van egy játékos aki a szerver és egy aki nem... Ő a nem szerver.
 */
public class Field_client extends Player implements Runnable{
    /**
     * KONSTRUKTOR
     */
    public Field_client() {
        lock = new Object();
        myField = new Field(2,lock);
        host = "localhost";
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
            myField_thread.setPriority(10);
            myField_thread.start();
        }
        else {
            System.err.println("Kliens : clientSocket null");
            WaitingFrame.setMessege("fail");
        }
    }






















    /// --------------------------------       RUN     -----------------------------------------
    /// ----------------------------------------------------------------------------------------

    /**
     * Próbál csatlakozni, ha sikerűl, akkor figyel a beérkező üzenetekre, kéri a --Field-- -et, hogy adja hozzá a pályájához
     * létrehozza a --Field-- -jét és ha kap egy
     * "game_over" üzenetet, akkor meghívja a végeredményekkel a végső kijelzőt.
     */
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
                        System.out.println("Palya_kliens : KAPOTT game_over-t"); // kapott uzenet ugy nez ki, hogy "game_over 2", ahol a szam a masik jatekos baranyait jelent
                        myField_thread.join(); // van, hogy kapcsolatot bont az előtt, hogy a pályája elküldi a befejezné a futást, das nem küldi el a másiknak, hogy mennyi báránya volt, ez azért baj, mert így a másik nem fog leállni, mert az eredményre vár

                        int masik_baranyai = convert_StringMessege_to_int(kapott_allat);
                        running = false;
                        close();
                        synchronized (lock){
                            //while (baranyaim==-1) lock.wait(); // ez nagyon sexy elméleti síkon, de itt nem :/
                            if(baranyaim==-1) lock.wait(500);
                            //lock.wait(100);
                            new Ending_Frame(baranyaim,masik_baranyai,playernumber);
                            //System.out.println("KLIENS : ÉnBaranyaim-  "+  baranyaim + "  KliensBaranyai-  "+masik_baranyai);
                        }
                        break;
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
                serverInput.close(); // Buffereder becsuk
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

                    if(!kuldott_game_overt) ennyi_baranyod_van(baranyaim);
                    synchronized (lock) {lock.wait(500);}
                    clientSocket.close();
                    System.out.println("PLAYER2       - CLOSE SOKET: " + clientSocket.getPort());

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

    /**
     * --WaintigFrame-- -re jazoláshoz kell ez a függvény
     * @return host
     */
    public static String getHost() {return host;}

    /**
     * --ChangePortFrame-- -állítja be, hogy melyik IP-re csatlakozzunk rá
     * @param host host
     */
    public static void setHost(String host) {
        Field_client.host = host;  }
    /**
     * --WaintigFrame-- -re jazoláshoz kell ez a függvény
     * @return PORTNUMBER
     */
    public static String getPORT_NUMBER() { return valueOf(PORT_NUMBER);  }
    /**
     * --ChangePortFrame-- -állítja be, hogy melyik IP-re csatlakozzunk rá
     * @return PORTNUMBER
     */
    public static void setPORT_NUMBER(int newport) { PORT_NUMBER = newport;  }

    /**
     * run(), hívja meg, ha kap egy "game-over"-t
     * @throws IOException Ha kapcsolat megszakad
     */
    protected void close() throws IOException {clientSocket.close();  myField.setGame_over(true); running= false;}
    /// ----------------------------------------------------------------------------------------
    /// ----------------------------------------------------------------------------------------




    /// ---------------------------- PRIVATE FUGGVENYEK  ---------------------------------------
    /// ----------------------------------------------------------------------------------------
    /**
     * A --Field-- -től kapunk egy számot, hogy mennyi bárányunk van. Ha a játéknak vége.
     * Ez a függvény ez is küldi a másik játékosnak, az eredményt.
     * @param ennyi_baranyom_van
     */
    public static void ennyi_baranyod_van(int ennyi_baranyom_van){
        kuldott_game_overt = true;
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
        //System.out.println("Kliens send : "+ line + " to : " +clientSocket.getInetAddress()+" : "+ PORT_NUMBER);
        clientWriter.print(line + "\r\n");
        clientWriter.flush();
    }
    /// ----------------------------------------------------------------------------------------
    /// ----------------------------------------------------------------------------------------


    //  ------------------------  PRIVATE VALTOZOK JATEKHOZ  -------------------------
    // -------------------------------------------------------------------------------

    private static PrintWriter clientWriter;
    private static int PORT_NUMBER=19999;
    private static String host="localhost";

    private static final int playernumber = 2;
    //private final Object lock;
    //private final Field myField;
    //private Socket clientSocket;
    private static int baranyaim = -1;
    public static boolean running = true;
    //private Thread myField_thread;
    private static boolean kuldott_game_overt;

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------


    /**
     * main-ből inditható legyen a KLIENS
     */
    public static void Palya_kliens_inditas() {
        new WaitingFrame(2);
    }


    /**
     * Saját main
     */
    public static void main(String[] args) {
        new WaitingFrame(playernumber);

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




