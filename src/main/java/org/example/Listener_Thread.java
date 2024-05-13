package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Listener_Thread implements Runnable{
    protected Socket clientSocket;

    Listener_Thread(Socket _clientSocket){
        clientSocket = _clientSocket;
    }

    @Override
    public void run() {
       /* try {
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //connected = true;
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }
}
