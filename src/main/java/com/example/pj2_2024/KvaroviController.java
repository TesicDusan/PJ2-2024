package com.example.pj2_2024;

import com.example.pj2_2024.Kvar.Kvar;
import com.example.pj2_2024.Vozilo.Vozilo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;
import java.util.List;

public class KvaroviController {

    @FXML
    private TableView<Kvar> kvaroviTable;
    @FXML
    private TableColumn<Kvar, String> idVozila;
    @FXML
    private TableColumn<Kvar, String> vrstaVozila;
    @FXML
    private TableColumn<Kvar, Date> vrijeme;
    @FXML
    private TableColumn<Kvar, String> opis;

    /**
     * Metoda koja ucitava kvarove iz liste vozila i postavlja ih na tabelu.
     * @param vozila lista vozila cije kvarove treba ucitati
     */
    public void ucitajKvarove(List<Vozilo> vozila) {

        ObservableList<Kvar> kvarovi = FXCollections.observableArrayList();
        for (Vozilo vozilo : vozila)
            kvarovi.addAll(vozilo.getKvarovi());

        idVozila.setCellValueFactory(new PropertyValueFactory<>("idVozila"));
        vrstaVozila.setCellValueFactory(new PropertyValueFactory<>("vrstaVozila"));
        vrijeme.setCellValueFactory(new PropertyValueFactory<>("vrijeme"));
        opis.setCellValueFactory(new PropertyValueFactory<>("opis"));
        kvaroviTable.setItems(kvarovi);
    }
}
