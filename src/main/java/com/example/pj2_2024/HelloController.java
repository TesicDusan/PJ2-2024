package com.example.pj2_2024;

import com.example.pj2_2024.Iznajmljivanje.Iznajmljivanje;
import com.example.pj2_2024.Vozilo.Automobil;
import com.example.pj2_2024.Vozilo.EBike;
import com.example.pj2_2024.Vozilo.ETrotinet;
import com.example.pj2_2024.Vozilo.Vozilo;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    private static final int BROJ_KOLONA = 20;
    private static final int BROJ_REDOVA = 20;
    private static final String COMMA_DELIMITER = ",";
    private static final String V_CSV_PATH = "src/main/resources/com/example/pj2_2024/PJ2 - projektni zadatak 2024 - Prevozna sredstva.csv";
    private static final String I_CSV_PATH = "src/main/resources/com/example/pj2_2024/PJ2 - projektni zadatak 2024 - Iznajmljivanja.csv";
    @FXML
    private VBox vbox;
    private static GridPane pane;
    private static final SimpleDateFormat vFormatter = new SimpleDateFormat("dd.MM.yyyy.");
    private static final SimpleDateFormat iFormatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
    private final ArrayList<Vozilo> vozila = new ArrayList<>();
    private final ArrayList<Iznajmljivanje> iznajmljivanja = new ArrayList<>();


    @FXML
    private void onPokreniButtonClick() {
        try {
            for (Iznajmljivanje iznajmljivanje : iznajmljivanja) {
                iznajmljivanje.start();
                iznajmljivanje.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSvaVozilaButtonClick() {

    }

    @FXML
    private void onKvaroviButtonClick() {

    }

    @FXML
    private void onPoslovanjeButtonClick() {

    }

    public static void prikaziNaMapi(Iznajmljivanje iznajmljivanje) {
        pane.add(new Rectangle(21, 21, iznajmljivanje.getVozilo().getColor()), iznajmljivanje.getCurrentPos()[0], iznajmljivanje.getCurrentPos()[1]);
    }   //NOT ON FX APP THREAD

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane = new GridPane();
        for(int i = 0; i < BROJ_REDOVA; i++) {
            RowConstraints rowConstraint = new RowConstraints(21);
            pane.getRowConstraints().add(rowConstraint);
        }
        for(int i = 0; i < BROJ_KOLONA; i++) {
            ColumnConstraints columnConstraint = new ColumnConstraints(21);
            pane.getColumnConstraints().add(columnConstraint);
        }
        pane.setGridLinesVisible(true);
        VBox.setVgrow(pane, Priority.ALWAYS);
        vbox.getChildren().add(pane);

        try {
            List<List<String>> lines = Files.readAllLines(Paths.get(V_CSV_PATH)).stream()
                    .map(line -> Arrays.asList(line.split(COMMA_DELIMITER))).toList();
            for(List<String> line : lines) {
                if("automobil".equals(line.get(8))) vozila.add(new Automobil(line.get(0), line.get(1), line.get(2),
                        vFormatter.parse(line.get(3)), Integer.parseInt(line.get(4)), line.get(7)));
                else if("bicikl".equals(line.get(8))) vozila.add(new EBike(line.get(0), line.get(1), line.get(2),
                        Integer.parseInt(line.get(4)), Integer.parseInt(line.get(5))));
                else if("trotinet".equals(line.get(8))) vozila.add(new ETrotinet(line.get(0), line.get(1), line.get(2),
                        Integer.parseInt(line.get(4)), Integer.parseInt(line.get(6))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            List<List<String>> lines = Files.readAllLines(Paths.get(I_CSV_PATH)).stream()
                    .map(line -> Arrays.asList(line.split(COMMA_DELIMITER))).toList();
            for(List<String> line : lines) {
                if (!line.equals(lines.get(0))) iznajmljivanja.add(new Iznajmljivanje(iFormatter.parse(line.get(0)),
                        line.get(1), searchVozila(line.get(2)), toIntArray(line.get(3), line.get(4)), toIntArray(line.get(5),
                        line.get(6)), Integer.parseInt(line.get(7)), line.get(8), line.get(9)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int[] toIntArray(String string1, String string2) {
        int[] tmp = new int[2];
        tmp[0] = Integer.parseInt(string1.replace("\"", ""));
        tmp[1] = Integer.parseInt(string2.replace("\"", ""));
        return tmp;
    }

    public Vozilo searchVozila(String id) {
        for(int i = 0; i < vozila.size(); i++) {
            if(id.equals(vozila.get(i).getId())) return vozila.get(i);
        }
        return null;
    }
}