package com.example.pj2_2024.Racun;

import com.example.pj2_2024.HelloController;
import com.example.pj2_2024.Iznajmljivanje.Iznajmljivanje;
import com.example.pj2_2024.Korisnik.Korisnik;
import com.example.pj2_2024.Vozilo.Automobil;
import com.example.pj2_2024.Vozilo.EBike;
import com.example.pj2_2024.Vozilo.Vozilo;

import java.io.*;
import java.util.Date;
import java.util.Properties;

public class Racun {
    private static final String CONFIG_FILE = "config.properties";
    private static int ID = 0;
    private Date datumIznajmljivanja;
    private Korisnik korisnik;
    private Vozilo vozilo;
    private int trajanje;
    private double iznos;
    private double popust;
    private double promocija;
    private double ukupno;
    private boolean kvar;
    private String startPos;
    private String finalPos;

    public Racun(Iznajmljivanje iznajmljivanje) {
        try {
            Properties properties = new Properties();
            properties.load(HelloController.class.getResourceAsStream(CONFIG_FILE));

            datumIznajmljivanja = iznajmljivanje.getDatumIznajmljivanja();
            korisnik = iznajmljivanje.getKorisnik();
            vozilo = iznajmljivanje.getVozilo();
            kvar = iznajmljivanje.isKvar();
            startPos = iznajmljivanje.getStartPos()[0] + "," + iznajmljivanje.getStartPos()[1];
            finalPos = iznajmljivanje.getCurrentPos()[0] + "," + iznajmljivanje.getCurrentPos()[1];
            trajanje = iznajmljivanje.getTrajanje();

            if (kvar) ukupno = iznos = 0;
            else {

                double cijenaZonaGrada = iznajmljivanje.isSiriDioGrada() ? Double.parseDouble(properties.getProperty("DISTANCE_WIDE")) : Double.parseDouble(properties.getProperty("DISTANCE_NARROW"));

                if (iznajmljivanje.getVozilo() instanceof Automobil)
                    iznos = Integer.parseInt(properties.getProperty("CAR_UNIT_PRICE")) * trajanje * cijenaZonaGrada;
                else if (iznajmljivanje.getVozilo() instanceof EBike)
                    iznos = Integer.parseInt(properties.getProperty("BIKE_UNIT_PRICE")) * trajanje * cijenaZonaGrada;
                else
                    iznos = Integer.parseInt(properties.getProperty("SCOOTER_UNIT_PRICE")) * trajanje * cijenaZonaGrada;
                popust = iznajmljivanje.isPopust() ? iznos * Double.parseDouble(properties.getProperty("DISCOUNT")) : 0;
                promocija = iznajmljivanje.isPromocija() ? iznos * Double.parseDouble(properties.getProperty("DISCOUNT_PROM")) : 0;
                ukupno = iznos - popust - promocija;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda koja zapisuje racun u tekstualni fajl.
     */
    public void generisiRacun() {
        try {
            File file = new File(new File("Racuni"), ++ID + ".txt");
            if(file.exists())file.delete();
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            writer.write(datumIznajmljivanja + "\n"
                    + "Korisnik: " + korisnik.getIme() + "\n"
                    + (korisnik.isStranac() ? "Pasos: " + korisnik.getPasos() : "Licna karta: " + korisnik.getLicna()) + "\n"
                    + "Vozacka dozvola: " + korisnik.getVozacka() + "\n"
                    + "Vozilo: " + vozilo.getId() + "\n"
                    + "Popust: " + popust + "\n"
                    + "Promocija: " + promocija + "\n"
                    + "Kvar: " + (kvar ? "da" : "ne") + "\n"
                    + "Lokacija preuzimanja: " + startPos + "\n"
                    + "Lokacija ostavljanja: " + finalPos + "\n"
                    + "Trajanje iznajmljivanja: " + trajanje + "s\n"
                    + "Cijena: " + ukupno + "KM\n");
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Date getDatumIznajmljivanja() { return datumIznajmljivanja; }
    public Korisnik getKorisnik() { return korisnik; }
    public Vozilo getVozilo() { return vozilo; }
    public double getIznos() { return iznos; }
    public double getUkupno() { return ukupno; }
    public double getPopust() { return popust; }
    public double getPromocija() { return promocija; }
    public boolean isKvar() { return kvar; }
}
