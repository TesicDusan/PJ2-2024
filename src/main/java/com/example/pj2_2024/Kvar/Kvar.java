package com.example.pj2_2024.Kvar;

import com.example.pj2_2024.Vozilo.Automobil;
import com.example.pj2_2024.Vozilo.EBike;
import com.example.pj2_2024.Vozilo.ETrotinet;
import com.example.pj2_2024.Vozilo.Vozilo;

import java.util.Date;

public class Kvar {
    private final String idVozila;
    private final String vrstaVozila;
    private final Date vrijeme;
    private final String opis;

    public Kvar(Date vrijeme, String opis, Vozilo vozilo) {
        this.vrijeme = vrijeme;
        this.opis = opis;
        idVozila = vozilo.getId();
        if(vozilo instanceof Automobil) vrstaVozila = "Automobil";
        else if(vozilo instanceof EBike) vrstaVozila = "Bicikl";
        else if(vozilo instanceof ETrotinet) vrstaVozila = "Trotinet";
        else vrstaVozila = "";
    }

    public String getVrstaVozila() { return  vrstaVozila; }
    public String getIdVozila() { return idVozila; }
    public String getOpis() { return opis; }
    public Date getVrijeme() { return vrijeme; }
}
