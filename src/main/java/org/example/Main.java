package org.example;

public class Main {
    public static void main(String[] args) {
        // Jaték ideje -- Field_Panel -- tetején változtatható
        // Állatok száma -- Field -- tetején


        // jobbra és balra lehet és ennyi :(
        Allat.jobbra();


        // külön külön is enindíthatók, megszakítás tesztelésre
        Palya_szerver.Palya_szerver_inditas();
        Palya_kliens.Palya_kliens_inditas();






        while(true){
            int activeThreads = Thread.activeCount();
            System.out.println("Jelenleg futó szálak : "+ activeThreads);
            if(activeThreads == 2) break ;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}