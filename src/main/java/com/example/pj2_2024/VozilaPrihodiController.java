package com.example.pj2_2024;

import com.example.pj2_2024.Vozilo.Automobil;
import com.example.pj2_2024.Vozilo.EBike;
import com.example.pj2_2024.Vozilo.ETrotinet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

public class VozilaPrihodiController implements Initializable {
    private static final String FOLDER = "vozilaSaNajvecimPrihodom";
    @FXML
    private Label automobilL;
    @FXML
    private Label biciklL;
    @FXML
    private Label trotinetL;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        try {
            ObjectInputStream oisAutomobil = new ObjectInputStream(new FileInputStream(Paths.get(FOLDER, "automobil.ser").toFile()));
            Automobil automobil = (Automobil) oisAutomobil.readObject();
            oisAutomobil.close();

            ObjectInputStream oisBicikl = new ObjectInputStream(new FileInputStream(Paths.get(FOLDER, "bicikl.ser").toFile()));
            EBike bicikl = (EBike) oisBicikl.readObject();
            oisBicikl.close();

            ObjectInputStream oisTrotinet = new ObjectInputStream(new FileInputStream(Paths.get(FOLDER, "trotinet.ser").toFile()));
            ETrotinet trotinet = (ETrotinet) oisTrotinet.readObject();
            oisTrotinet.close();

            automobilL.setText(automobil.getId());
            biciklL.setText(bicikl.getId());
            trotinetL.setText(trotinet.getId());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
