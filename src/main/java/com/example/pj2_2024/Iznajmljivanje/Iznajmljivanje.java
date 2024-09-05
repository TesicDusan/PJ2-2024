package com.example.pj2_2024.Iznajmljivanje;

import com.example.pj2_2024.HelloController;
import com.example.pj2_2024.Kvar.Kvar;
import com.example.pj2_2024.Vozilo.Vozilo;

import java.time.LocalDateTime;
import java.util.Date;

public class Iznajmljivanje extends Thread {
    private final Vozilo vozilo;
    private final String korisnik;
    private final Date datumIznajmljivanja;
    private final int[] startPos;
    private int[] currentPos;
    private final int[] destPos;
    private final int trajanje;
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

        this.vozilo.addIznajmljivanje(this);
    }

    @Override
    public void run() {
            if (kvar) {
                Kvar noviKvar = new Kvar(LocalDateTime.now(), "opis", vozilo);
                vozilo.addKvar(noviKvar);
            } else {
                //double zadrzavanje = ((double) (Math.abs(destPos[0] - currentPos[0]) + Math.abs(destPos[1] - currentPos[1])) / trajanje) * 1000;
                int zadrzavanje = 1000;
                try {
                    synchronized (HelloController.getLock()) {
                        while (currentPos[0] != destPos[0] || currentPos[1] != destPos[1]) {
                            if (vozilo.baterijaPrazna()) vozilo.napuniBateriju();
                            else {
                                // Update position based on current and destination positions
                                if (currentPos[0] < destPos[0]) currentPos[0]++;
                                else if (currentPos[0] > destPos[0]) currentPos[0]--;
                                else if (currentPos[1] < destPos[1]) currentPos[1]++;
                                else currentPos[1]--;
                                vozilo.trosiBateriju();
                                HelloController.getLock().wait(zadrzavanje);
                                HelloController.getLock().notifyAll();
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
        generisiRacun();
    }

    public void generisiRacun() {
        //generisi tekstualni fajl koji će biti smješten u datoteku sa nazivom koji odgovara imenu korisnika
    }
    public int[] getCurrentPos() { return currentPos; }
    public Vozilo getVozilo() { return vozilo; }

    public Date getDatumIznajmljivanja() {
        return datumIznajmljivanja;
    }
}
