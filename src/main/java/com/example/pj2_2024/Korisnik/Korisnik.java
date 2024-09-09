package com.example.pj2_2024.Korisnik;

import java.util.Random;

public class Korisnik {
    private static int COUNTER = 0;
    private final String ime;
    private final String licna;
    private final String pasos;
    private final String vozacka;
    private final boolean stranac;

    public Korisnik(String ime, boolean stranac) {
        this.ime = ime;
        this.stranac = stranac;
        pasos = stranac ? String.valueOf(COUNTER) : "";
        licna = stranac ? "" : String.valueOf(COUNTER);
        vozacka = String.valueOf(COUNTER++);
    }

    /**
     * Metoda koja odredjuje da li ce korisnik biti tretiran kao strani drzavljanin.
     * @return true ako je stranac
     */
    public static boolean generisiStranac() {
        Random random = new Random();
        int i = random.nextInt(100);
        return i > 70;
    }

    public boolean isStranac() { return stranac; }
    public String getIme() { return ime; }
    public String getVozacka() { return vozacka; }
    public String getLicna() { return licna; }
    public String getPasos() { return pasos; }
}
