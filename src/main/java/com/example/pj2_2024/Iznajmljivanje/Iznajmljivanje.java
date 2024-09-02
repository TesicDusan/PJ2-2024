package com.example.pj2_2024.Iznajmljivanje;

import com.example.pj2_2024.Vozilo.Vozilo;

import java.util.Date;

public class Iznajmljivanje extends Thread {
    private final Vozilo vozilo; //vozilo koje se koristi
    private final String korisnik; //ime osobe koja vrsi iznajmljivanje
    private final Date datumIznajmljivanja; //datum kada je vozilo iznajmljeno korisniku
    private final int[] startPos; //pocetne koordinate
    private int[] currentPos; //trenutne koordinate
    private final int[] destPos; // koordinate odredista
    private final int trajanje; //trajanje iznajmlivanja u sekundama
    private final boolean kvar;
    private final boolean promocija;

    public Iznajmljivanje(Date datumIznajmljivanja, String korisnik, Vozilo vozilo, int[] startPos, int[] destPos,
                          int trajanje, String kvar, String promocija) {
        this.datumIznajmljivanja = datumIznajmljivanja;
        this.korisnik = korisnik;
        this.vozilo = vozilo;
        this.currentPos = this.startPos = startPos;
        this.destPos = destPos;
        this.trajanje = trajanje;
        this.kvar = "da".equals(kvar);
        this.promocija = "da".equals(promocija);
    }

    @Override
    public void run() {
                //double zadrzavanje = ((double) (Math.abs(destPos[0] - currentPos[0]) + Math.abs(destPos[1] - currentPos[1])) / trajanje) * 1000;
                int zadrzavanje = 1000;
                try {
                    while (currentPos[0] != destPos[0] || currentPos[1] != destPos[1]) {
                        // Update position based on current and destination positions
                        if (currentPos[0] < destPos[0]) currentPos[0]++;
                        else if (currentPos[0] > destPos[0]) currentPos[0]--;
                        else if (currentPos[1] < destPos[1]) currentPos[1]++;
                        else currentPos[1]--;
                        Thread.sleep(zadrzavanje);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
    }

    public int[] getCurrentPos() { return currentPos; }
    public Vozilo getVozilo() { return vozilo; }

    public Date getDatumIznajmljivanja() {
        return datumIznajmljivanja;
    }
}
