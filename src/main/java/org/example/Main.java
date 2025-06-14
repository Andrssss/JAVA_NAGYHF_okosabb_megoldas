package org.example;

public class Main {
    public static void main(String[] args) {
        // Jaték ideje -- Field_Panel -- tetején változtatható
        // Állatok száma -- Field -- tetején


        // Rövid leírás :
            // - Palya_szerver -> Player1, ő lesz egyben a szerver is, a Palya-n keresztűl tudja fogadni a kéréseket
            // - Palya-kliens -> Player2, Csatlakozni akaró player.

            // - Field -> Lényegében Game_master, minden ami a játékkal kapcsolatos azt ő irányítja
            // - Field_Panel -> A Field rajzolásáért felelős. Mivel itt van az időzítés megoldva, ezért ő ütemezi a Field feladatait.

            // FRAME -ek
                // WaitingFrame : A főmenű.
                // ChangeProtFrame : Itt lehet IP / PORT számot válzotatni. (Frame-bő nyilik)
                // Ending_Frame : Ez nyilik meg, a játék végeztével az eredményekkel.





        // jobbra és balra lehet és ennyi :(
        Allat.jobbra();


        // külön külön is enindíthatók, megszakítás tesztelésre
        Palya_szerver.Palya_szerver_inditas();
        Palya_kliens.Palya_kliens_inditas();
        //Palya_kliens.Palya_kliens_inditas();



        // EXIT-et meg kell nyomni a GUI-n, hogy minden szál leálljon.



        while(true){
            int activeThreads = Thread.activeCount();
            System.out.println("Jelenleg futó szálak : "+ activeThreads);
           if(activeThreads == 2) break; // ( Main + Thread )

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}