package com.example.pj2_2024.Iznajmljivanje;

import com.example.pj2_2024.HelloController;
import com.example.pj2_2024.Kvar.Kvar;
import com.example.pj2_2024.Vozilo.Automobil;
import com.example.pj2_2024.Vozilo.EBike;
import com.example.pj2_2024.Vozilo.Vozilo;

import java.io.*;
import java.util.Date;
import java.util.Properties;

public class Iznajmljivanje extends Thread {
    private static final String CONFIG_FILE = "config.properties";
    private static int IZNAJMLJIVANJE_ID = 0;
    private static int DISCOUNT_BR = 0;
    private final Vozilo vozilo;
    private final String korisnik;
    private final Date datumIznajmljivanja;
    private final int[] startPos;
    private int[] currentPos;
    private final int[] destPos;
    private int trajanje;
    private final boolean kvar;
    private final boolean promocija;
    private boolean popust;
    private boolean siriDioGrada = false;

    public Iznajmljivanje(Date datumIznajmljivanja, String korisnik, Vozilo vozilo, int[] startPos, int[] destPos,
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

        this.vozilo.addIznajmljivanje(this);
    }

    @Override
    public void run() {
            if(++DISCOUNT_BR == 10) {
                popust = true;
                DISCOUNT_BR = 0;
            }
            if (kvar) {
                Kvar noviKvar = new Kvar(datumIznajmljivanja, "opis", vozilo);
                vozilo.addKvar(noviKvar);
                trajanje = 0;
            } else {
                int zadrzavanje = (int) (((double) (Math.abs(destPos[0] - currentPos[0]) + Math.abs(destPos[1] - currentPos[1])) / trajanje) * 1000);

                try {
                    synchronized (HelloController.getLock()) {
                        while (currentPos[0] != destPos[0] || currentPos[1] != destPos[1]) {
                            if((currentPos[0] < 5 || currentPos[0] > 14) && (currentPos[1] < 4 || currentPos[1] > 14)) siriDioGrada = true;
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
        try {
            File directory = new File(korisnik);
            directory.mkdir();
            File file = new File(directory, ++IZNAJMLJIVANJE_ID + ".txt");
            if(file.exists())file.delete();
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            double ukupno;

            if (kvar) ukupno = 0;
            else {
                Properties properties = new Properties();
                properties.load(Iznajmljivanje.class.getResourceAsStream(CONFIG_FILE));
                double iznos;
                if (vozilo instanceof Automobil)
                    iznos = Integer.parseInt(properties.getProperty("CAR_UNIT_PRICE")) * trajanje
                            * (siriDioGrada ? Double.parseDouble(properties.getProperty("DISTANCE_WIDE")) : Double.parseDouble(properties.getProperty("DISTANCE_NARROW")));
                else if (vozilo instanceof EBike)
                    iznos = Integer.parseInt(properties.getProperty("BIKE_UNIT_PRICE")) * trajanje
                            * (siriDioGrada ? Double.parseDouble(properties.getProperty("DISTANCE_WIDE")) : Double.parseDouble(properties.getProperty("DISTANCE_NARROW")));
                else iznos = Integer.parseInt(properties.getProperty("SCOOTER_UNIT_PRICE")) * trajanje
                            * (siriDioGrada ? Double.parseDouble(properties.getProperty("DISTANCE_WIDE")) : Double.parseDouble(properties.getProperty("DISTANCE_NARROW")));
                ukupno = iznos - (popust ? iznos * Double.parseDouble(properties.getProperty("DISCOUNT")) : 0) - (promocija ? iznos * Double.parseDouble(properties.getProperty("DISCOUNT_PROM")) : 0);
            }
            writer.write(datumIznajmljivanja + "\n"
                    + "Korisnik: " + korisnik + "\n"
                    + "Vozilo: " + vozilo.getId() + "\n"
                    + "Popust: " + (popust ? "da" : "ne") + "\n"
                    + "Promocija: " + (promocija ? "da" : "ne") + "\n"
                    + "Kvar: " + (kvar ? "da" : "ne") + "\n"
                    + "Lokacija preuzimanja: " + startPos[0] + "," + startPos[1] + "\n"
                    + "Lokacija ostavljanja: " + currentPos[0] + "," + currentPos[1] + "\n"
                    + "Trajanje iznajmljivanja: " + trajanje + "s\n"
                    + "Cijena: " + ukupno + "KM\n");
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int[] getCurrentPos() { return currentPos; }
    public Vozilo getVozilo() { return vozilo; }

    public Date getDatumIznajmljivanja() {
        return datumIznajmljivanja;
    }
}
