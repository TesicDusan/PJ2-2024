package com.example.pj2_2024.Vozilo;

import com.example.pj2_2024.Kvar.Kvar;
import com.example.pj2_2024.Iznajmljivanje.Iznajmljivanje;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class Vozilo {
    private final String id;
    private final String proizvodjac;
    private final String model;
    private final int cijenaNabavke;
    private int nivoBaterije;
    private ArrayList<Kvar> kvarovi = new ArrayList<>();
    private ArrayList<Iznajmljivanje> iznajmljivanja = new ArrayList<>();

    public Vozilo(String id, String proizvodjac, String model, int cijenaNabavke) {
        this.id = id;
        this.proizvodjac = proizvodjac;
        this.model = model;
        this.cijenaNabavke = cijenaNabavke;
        this.nivoBaterije = 100;
    }

    public Color getColor() { return Color.WHITE; }
    public String getId() { return id; }
}
