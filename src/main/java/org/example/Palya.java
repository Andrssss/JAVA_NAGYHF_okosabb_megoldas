package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import static java.lang.String.valueOf;

/**
 * Palya fog segíteni külön szálon segíteni a --Palya_szerver-- -nek segíteni a bejövő csatlakozási kérelmeket fogadni.
 * Lényegében a --Palya_szerver-- segítő apja.
 * Van timeout.
 */
public class Palya implements Runnable {

    /**
     * KONSTRUKTOR
     * @param w1 --WaitingFrame--, hogy tudjunk rá üzeneteket kiírni, mint hogy "timeout" vagy "waiting for player2",
     *           ezt odaadjuk a --Palya_szerver-- -nek.
     * @param _lock Ez fogja a --Palya_szerver-- és --Field-- közti nézeteltéréseket rendezni
     */
    public Palya(WaitingFrame w1, Object _lock) {
        System.out.println("Palya : letrehozas");
        timeout = 100000; // 100 másodperc
        running = true;
        lock = _lock;
        myw1 = w1;
        clientSocket = null;

        try {
            serverSocket = new ServerSocket(PORT_NUMBER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Itt valósul meg a bejövő kérések fogadása és a --Pálya-szerver-- elinditása, ha van kapcsolat. / timeout
     */
    public void run() {
        System.out.println("Palya : fut");
        try {
            serverSocket.setSoTimeout(timeout);

            while (running) {
                System.out.println("Palya : ciklusban fut");
                if (serverSocket != null) {
                    try {
                        System.out.println("Palya : kliens elfogadas");
                        clientSocket = serverSocket.accept(); // itt elfogadja a kérést és nyit egy socketet
                        MyPalya_szerver = new Palya_szerver(clientSocket, myw1, lock, this);
                        Thread serverThread = new Thread(MyPalya_szerver);
                        serverThread.start();

                        // Megvárjuk, hogy a MyPalya_szerver befejezze a futását
                        serverThread.join();

                        // Amikor a MyPalya_szerver befejeződött, újra elfogadunk kapcsolatot
                        clientSocket = null;
                        MyPalya_szerver = null;
                    } catch (java.net.SocketTimeoutException ste) {
                        // Itt fut le, ha timeout történik
                        myw1.timeout();
                        System.err.println("Palya       - TIMEOUT");
                    } catch (IOException e) {
                        System.err.println("Palya       - Failed to communicate with Kliens!");
                    } catch (InterruptedException e) {
                        System.err.println("Palya       - Interrupted while waiting for server thread to finish.");
                        Thread.currentThread().interrupt();
                        break; // Kilépés a while ciklusból megszakítás esetén
                    }
                } else {
                    System.err.println("Palya       - Server socket is not empty");
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                System.out.println("Palya : socket close");
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Palya       - CLOSED");
        }
    }


    /**
     * Portot 2 helyre iratom ki, --WaitingFrame-- és --ChangePortFrame--, ezek kérik le
     * @return A szerver által használt PORT
     */
    public static String getPORT_NUMBER() {
        return valueOf(PORT_NUMBER);
    }

    /**
     * A --ChangePortFrame-- ide küldi az új PORT-ot, ha helyes a formátuma.
     * @param newport új port
     */
    public static void setPORT_NUMBER(int newport) {
        PORT_NUMBER = newport;
    }


    /**
     * Ha a játék vége van, akkor --Palya_szerver-- hívja meg
     * Ha a játékos valamiért kilép, a főképernyőből (--WaitingFrame--), akkor az meghívja ezt.
     */
    public void close() {
        running = false;
        System.out.println("MyPalya_szerver : " + MyPalya_szerver);
        if (MyPalya_szerver != null) {
            System.out.println("MyPalya_szerver nem null");
            //MyPalya_szerver.close();
        }
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //  ------------------------  PRIVATE VALTOZOK JATEKHOZ  -------------------------
    protected ServerSocket serverSocket;
    protected Socket clientSocket;
    private static int PORT_NUMBER = 19999;
    private WaitingFrame myw1;
    private Object lock;
    private static int timeout;
    public static boolean running; // todo getter // setter
    private Palya_szerver MyPalya_szerver = null;
}
