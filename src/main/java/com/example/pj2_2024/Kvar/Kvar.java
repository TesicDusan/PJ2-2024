package com.example.pj2_2024.Kvar;

import com.example.pj2_2024.Vozilo.Automobil;
import com.example.pj2_2024.Vozilo.EBike;
import com.example.pj2_2024.Vozilo.ETrotinet;
import com.example.pj2_2024.Vozilo.Vozilo;

import java.time.LocalDateTime;

public class Kvar {
    private final String idVozila;
    private final String vrstaVozila;
    private final LocalDateTime vrijeme;
    private final String opis;

    public Kvar(LocalDateTime vrijeme, String opis, Vozilo vozilo) {
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
    public LocalDateTime getVrijeme() { return vrijeme; }
}
