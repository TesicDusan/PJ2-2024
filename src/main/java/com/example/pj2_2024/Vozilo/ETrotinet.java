package com.example.pj2_2024.Vozilo;

import javafx.scene.paint.Color;

public class ETrotinet extends Vozilo {
    private static final Color SCOOTER_COLOR = Color.RED;
    private final int maxBrzina;

    public ETrotinet(String id, String proizvodjac, String model, int cijenaNabavke, int maxBrzina) {
        super(id, proizvodjac, model, cijenaNabavke);
        this.maxBrzina = maxBrzina;
    }

    @Override
    public Color getColor() { return SCOOTER_COLOR; }
    public int getMaxBrzina() { return maxBrzina; }
}
