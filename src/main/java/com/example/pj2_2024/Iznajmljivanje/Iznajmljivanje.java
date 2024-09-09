package com.example.pj2_2024.Iznajmljivanje;

import com.example.pj2_2024.HelloController;
import com.example.pj2_2024.Korisnik.Korisnik;
import com.example.pj2_2024.Kvar.Kvar;
import com.example.pj2_2024.Racun.Racun;
import com.example.pj2_2024.Vozilo.Vozilo;

import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Iznajmljivanje extends Thread {
    private static final Object LOCK = new Object();
    private CyclicBarrier barrier;
    private static int DISCOUNT_BR = 0;
    private final Vozilo vozilo;
    private final Korisnik korisnik;
    private final Date datumIznajmljivanja;
    private final int[] startPos;
    private int[] currentPos;
    private final int[] destPos;
    private int trajanje;
    private final boolean kvar;
    private final boolean promocija;
    private boolean popust;
    private boolean siriDioGrada = false;

    public Iznajmljivanje(Date datumIznajmljivanja, Korisnik korisnik, Vozilo vozilo, int[] startPos, int[] destPos,
                          int trajanje, String kvar, String promocija) {
        this.datumIznajmljivanja = datumIznajmljivanja;
        this.korisnik = korisnik;
        this.vozilo = vozilo;
        this.startPos = startPos;
        this.currentPos = startPos.clone();
        this.destPos = destPos;
        this.trajanje = trajanje;
        this.kvar = "da".equals(kvar);
        this.promocija = "da".equals(promocija);
    }

    @Override
    public void run() {
        try {
            if(++DISCOUNT_BR == 10) {
                popust = true;
                DISCOUNT_BR = 0;
            }
            if (kvar) {
                Kvar noviKvar = new Kvar(datumIznajmljivanja, "opis", vozilo);
                vozilo.addKvar(noviKvar);
                trajanje = 0;
            } else {
                int zadrzavanje = (int) (((double) trajanje / (Math.abs(destPos[0] - currentPos[0]) + Math.abs(destPos[1] - currentPos[1]))) * 1000);

                    synchronized (LOCK) {
                        while (currentPos[0] != destPos[0] || currentPos[1] != destPos[1]) {
                            if((currentPos[0] < 5 || currentPos[0] > 14) && (currentPos[1] < 4 || currentPos[1] > 14)) siriDioGrada = true;
                            if (vozilo.baterijaPrazna()) vozilo.napuniBateriju();
                            else {
                                if (currentPos[0] < destPos[0]) currentPos[0]++;
                                else if (currentPos[0] > destPos[0]) currentPos[0]--;
                                else if (currentPos[1] < destPos[1]) currentPos[1]++;
                                else currentPos[1]--;
                                vozilo.trosiBateriju();

                                LOCK.wait(zadrzavanje);
                                LOCK.notifyAll();
                            }
                        }
                    }
            }
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        Racun racun = new Racun(this);
        HelloController.addRacun(racun);
        racun.generisiRacun();
    }

    public void setBarrier(CyclicBarrier barrier) { this.barrier = barrier; }

    public int[] getCurrentPos() { return currentPos; }
    public int[] getStartPos() { return startPos; }
    public Vozilo getVozilo() { return vozilo; }
    public Korisnik getKorisnik() { return korisnik; }
    public boolean isSiriDioGrada() { return siriDioGrada; }
    public boolean isKvar() { return kvar; }
    public boolean isPopust() { return popust; }
    public boolean isPromocija() { return promocija; }
    public int getTrajanje() { return trajanje; }
    public Date getDatumIznajmljivanja() {
        return datumIznajmljivanja;
    }
}
