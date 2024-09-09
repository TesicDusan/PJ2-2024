package com.example.pj2_2024;

import com.example.pj2_2024.Racun.Racun;
import com.example.pj2_2024.Vozilo.Automobil;
import com.example.pj2_2024.Vozilo.EBike;
import com.example.pj2_2024.Vozilo.ETrotinet;
import com.example.pj2_2024.Vozilo.Vozilo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class PoslovanjeController {
    private static final String FOLDER = "vozilaSaNajvecimPrihodom";

    /**
     * Metoda koja otvara novi prozor u kome ce biti prikazani dnevni izvjestaji.
     */
    @FXML
    public void onDnevniButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(PoslovanjeController.class.getResource("dnevni-view.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Dnevni izvjestaj");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda koja otvara novi prozor u kome ce bit prikazan sumarni izvjestaj.
     */
    @FXML
    public void onSumarniButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(PoslovanjeController.class.getResource("sumarni-view.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Sumarni izvjestaj");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda koja otvara novi prozor u kome ce biti prikazana vozila sa najvisim prihodima.
     */
    @FXML
    public void onVozilaButtonClick() {
        serializeVozila();

        try {
            FXMLLoader loader = new FXMLLoader(VozilaPrihodiController.class.getResource("vozilaPrihodi-view.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Vozila sa najvise prihoda");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda koja vrsi serijalizaciju vozila sa najvisim prihodima.
     */
    public void serializeVozila() {
        Map<Vozilo, List<Racun>> racuniMap = HelloController.getRacuni().stream().collect(Collectors.groupingBy(Racun::getVozilo));
        Map<Automobil, Double> automobilMap = new HashMap<>();
        Map<EBike, Double> biciklMap = new HashMap<>();
        Map<ETrotinet, Double> trotinetMap = new HashMap<>();

        for(Vozilo vozilo : racuniMap.keySet()) {
            double prihodi = 0;
            for (Racun racun : racuniMap.get(vozilo)) prihodi += racun.getIznos();
            if(vozilo instanceof Automobil automobil) automobilMap.put(automobil, prihodi);
            else if(vozilo instanceof EBike eBike) biciklMap.put(eBike, prihodi);
            else if(vozilo instanceof ETrotinet eTrotinet) trotinetMap.put(eTrotinet, prihodi);
        }

        Automobil automobil = Collections.max(automobilMap.entrySet(), (entry1, entry2) -> (int) (entry1.getValue() - entry2.getValue())).getKey();
        EBike bicikl = Collections.max(biciklMap.entrySet(), (entry1, entry2) -> (int) (entry1.getValue() - entry2.getValue())).getKey();
        ETrotinet trotinet = Collections.max(trotinetMap.entrySet(), (entry1, entry2) -> (int) (entry1.getValue() - entry2.getValue())).getKey();
        try {
            ObjectOutputStream oosAutomobil = new ObjectOutputStream(new FileOutputStream(Paths.get(FOLDER, "automobil.ser").toFile()));
            oosAutomobil.writeObject(automobil);
            oosAutomobil.close();

            ObjectOutputStream oosBicikl = new ObjectOutputStream(new FileOutputStream(Paths.get(FOLDER, "bicikl.ser").toFile()));
            oosBicikl.writeObject(bicikl);
            oosBicikl.close();

            ObjectOutputStream oosTrotinet = new ObjectOutputStream(new FileOutputStream(Paths.get(FOLDER, "trotinet.ser").toFile()));
            oosTrotinet.writeObject(trotinet);
            oosTrotinet.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
