package com.example.pj2_2024.Vozilo;

import javafx.scene.paint.Color;

public class EBike extends Vozilo {
    private static final Color BIKE_COLOR = Color.YELLOW;
    private final int domet;

    public EBike(String id, String proizvodjac, String model, int cijenaNabavke, int domet) {
        super(id, proizvodjac, model, cijenaNabavke);
        this.domet = domet;
    }

    @Override
    public Color getColor() { return BIKE_COLOR; }
}
