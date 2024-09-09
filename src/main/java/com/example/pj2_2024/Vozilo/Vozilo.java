package com.example.pj2_2024.Vozilo;

import com.example.pj2_2024.Kvar.Kvar;
import com.example.pj2_2024.Iznajmljivanje.Iznajmljivanje;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Vozilo implements Serializable {
    private final String id;
    private final String proizvodjac;
    private final String model;
    private final int cijenaNabavke;
    private int nivoBaterije;
    private transient ArrayList<Kvar> kvarovi = new ArrayList<>();

    public Vozilo(String id, String proizvodjac, String model, int cijenaNabavke) {
        this.id = id;
        this.proizvodjac = proizvodjac;
        this.model = model;
        this.cijenaNabavke = cijenaNabavke;
        this.nivoBaterije = 100;
    }

    /**
     * Metoda koja simulira punjenje baterije.
     */
    public void napuniBateriju () { if(nivoBaterije < 100) nivoBaterije = 100; }

    /**
     * Metoda koja simulria trosenje baterije.
     */
    public void trosiBateriju() { if(nivoBaterije > 0) nivoBaterije -= 5; }

    /**
     * Metoda koja provjerava da li je baterija prazna.
     * @return true ako je baterija prazna, false ako nije.
     */
    public boolean baterijaPrazna() {
        return nivoBaterije == 0;
    }

    public Color getColor() { return Color.WHITE; }
    public String getId() { return id; }
    public String getProizvodjac() { return proizvodjac; }
    public String getModel() { return model; }
    public int getCijenaNabavke() { return cijenaNabavke; }
    public int getNivoBaterije() { return nivoBaterije; }
    public List<Kvar> getKvarovi() { return kvarovi; }
    public void addKvar(Kvar kvar) { kvarovi.add(kvar); }
}
