package com.example.pj2_2024;

import com.example.pj2_2024.Izvjestaj.Izvjestaj;
import com.example.pj2_2024.Racun.Racun;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class DnevniController implements Initializable {
    @FXML
    private TableView<Izvjestaj> table;
    @FXML
    private TableColumn<Izvjestaj, Date> datum;
    @FXML
    private TableColumn<Izvjestaj, Double> prihod;
    @FXML
    private TableColumn<Izvjestaj, Double> popust;
    @FXML
    private TableColumn<Izvjestaj, Double> promocije;
    @FXML
    private TableColumn<Izvjestaj, Double> iznosVoznji;
    @FXML
    private TableColumn<Izvjestaj, Double> odrzavanje;
    @FXML
    private TableColumn<Izvjestaj, Double> popravke;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        ObservableList<Izvjestaj> izvjestaji = FXCollections.observableArrayList();

        Map<Date, List<Racun>> groupedByDate = HelloController.getRacuni().
            stream().collect(Collectors.groupingBy(r -> {
                try {
                    return formatter.parse(formatter.format(r.getDatumIznajmljivanja()));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                }
            }));

        for(Date date : groupedByDate.keySet())
            izvjestaji.add(new Izvjestaj(groupedByDate.get(date)));

        datum.setCellValueFactory(new PropertyValueFactory<>("datum"));
        prihod.setCellValueFactory(new PropertyValueFactory<>("prihod"));
        popust.setCellValueFactory(new PropertyValueFactory<>("popust"));
        promocije.setCellValueFactory(new PropertyValueFactory<>("promocije"));
        iznosVoznji.setCellValueFactory(new PropertyValueFactory<>("iznosVoznji"));
        odrzavanje.setCellValueFactory(new PropertyValueFactory<>("odrzavanje"));
        popravke.setCellValueFactory(new PropertyValueFactory<>("popravke"));
        table.setItems(izvjestaji);
    }
}
