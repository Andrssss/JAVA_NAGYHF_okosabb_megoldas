package org.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class Palya_szerver implements Runnable { // Ő LESZ BAL OLDALT
    protected boolean jobbra_atmehet = true;
    protected boolean balra_atmehet  = false;
















    protected Socket clientSocket;
    protected BufferedReader clientReader;
    protected PrintWriter clientWriter;

    Palya_szerver(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.clientWriter = new PrintWriter(clientSocket.getOutputStream()); //
    }




    /**
     * Minden beszelgetesnek kulon szala van. 2 dolog van amit vedeni kell a tobbszalusagtol -> check_out_item() & addnewitem()
     */
    /// ---------------------------------   RUN      -------------------------------------------
    /// ----------------------------------------------------------------------------------------
    public void run() {
        try {
            if(clientSocket.isConnected()) {
                System.out.println("PLAYER1       - KAPCSOLAT ELINDULT A : " + clientSocket.getPort() + " -AL");
                // itt kell elinditani a jatekot
                // todo
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


    public static void main(String[] args) {
        new WaitingFrame(1);
    }
}