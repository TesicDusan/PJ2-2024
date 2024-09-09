package com.example.pj2_2024.Vozilo;

import javafx.scene.paint.Color;

import java.util.Date;

public class Automobil extends Vozilo {
    private static final Color CAR_COLOR = Color.GREEN;
    private final Date datumNabavke;
    private final String opis;

    public Automobil(String id, String proizvodjac, String model, Date datumNabavke, int cijenaNabavke, String opis) {
        super(id, proizvodjac, model, cijenaNabavke);
        this.datumNabavke = datumNabavke;
        this.opis = opis;
    }


    @Override
    public Color getColor() { return CAR_COLOR; }
    public String getOpis() { return opis; }
    public Date getDatumNabavke() { return datumNabavke; }
}
