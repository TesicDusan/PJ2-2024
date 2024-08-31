package com.example.pj2_2024.Iznajmljivanje;

import com.example.pj2_2024.HelloController;
import com.example.pj2_2024.Vozilo.Vozilo;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;

import java.util.Date;

public class Iznajmljivanje extends Thread {
    private static final int INTERVAL = 50;
    private static Timeline timeline;
    private Vozilo vozilo; //vozilo koje se koristi
    private String korisnik; //ime osobe koja vrsi iznajmljivanje
    private Date datumIznajmljivanja; //datum kada je vozilo iznajmljeno korisniku
    private int[] startPos; //pocetne koordinate
    private int[] currentPos; //trenutne koordinate
    private int[] destPos; // koordinate odredista
    private int trajanje; //trajanje iznajmlivanja u sekundama
    private boolean kvar;
    private boolean promocija;

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
        int zadrzavanje = (int)((double)(Math.abs(destPos[0] - currentPos[0]) + Math.abs(destPos[1] - currentPos[1])) / trajanje) * 1000;

        Platform.runLater(() -> {
            timeline = new Timeline(new KeyFrame(Duration.millis(INTERVAL), actionEvent -> HelloController.prikaziNaMapi(this)));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        });

        while(currentPos[0] != destPos[0] && currentPos[1] != destPos[1]) {
            try {
                //HelloController.prikaziNaMapi(this);
                if (currentPos[0] < destPos[0]) currentPos[0]++;
                else if (currentPos[0] > destPos[0]) currentPos[0]--;
                else if (currentPos[1] < destPos[1]) currentPos[1]++;
                else currentPos[1]--;
                Thread.sleep(zadrzavanje);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public int[] getCurrentPos() { return currentPos; }
    public Vozilo getVozilo() { return vozilo; }
}
