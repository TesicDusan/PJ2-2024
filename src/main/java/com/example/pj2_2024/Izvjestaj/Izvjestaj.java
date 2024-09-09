package com.example.pj2_2024.Izvjestaj;

import com.example.pj2_2024.Racun.Racun;
import com.example.pj2_2024.Vozilo.Automobil;
import com.example.pj2_2024.Vozilo.EBike;
import com.example.pj2_2024.Vozilo.Vozilo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Izvjestaj {
    private static final double CAR_FIX = 0.07;
    private static final double BIKE_FIX = 0.04;
    private static final double SCOOTER_FIX = 0.02;
    private Date datum;
    private double prihod = 0;
    private double popust = 0;
    private double promocije = 0;
    private double iznosVoznji = 0;
    private final double odrzavanje;
    private double popravke = 0;
    private final double troskovi;
    private final double porez;

    public Izvjestaj(List<Racun> racuni) {
        try {
            DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");
            datum = formatter.parse(formatter.format(racuni.get(0).getDatumIznajmljivanja()));

            for (Racun racun : racuni) {
                Vozilo vozilo = racun.getVozilo();

                prihod += racun.getUkupno();
                popust += racun.getPopust();
                promocije += racun.getPromocija();
                iznosVoznji += racun.getIznos();

                if (racun.isKvar()) {
                    if (vozilo instanceof Automobil) popravke += CAR_FIX * vozilo.getCijenaNabavke();
                    else if (vozilo instanceof EBike) popravke += BIKE_FIX * vozilo.getCijenaNabavke();
                    else popravke += SCOOTER_FIX * vozilo.getCijenaNabavke();
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
            odrzavanje = prihod * 0.2;
            troskovi = prihod * 0.2;
            porez = (prihod - odrzavanje - popravke - troskovi) * 0.1;
    }

    public Date getDatum() { return datum; }
    public double getPopust() { return popust; }
    public double getIznosVoznji() { return iznosVoznji; }
    public double getOdrzavanje() { return odrzavanje;}
    public double getPopravke() { return popravke; }
    public double getPorez() { return porez; }
    public double getPrihod() { return prihod; }
    public double getPromocije() { return promocije; }
    public double getTroskovi() { return troskovi; }
}
