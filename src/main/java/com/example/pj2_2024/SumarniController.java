package com.example.pj2_2024;

import com.example.pj2_2024.Izvjestaj.Izvjestaj;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class SumarniController implements Initializable {
    @FXML
    private Label prihod;
    @FXML
    private Label popust;
    @FXML
    private Label promocije;
    @FXML
    private Label iznosVoznji;
    @FXML
    private Label odrzavanje;
    @FXML
    private Label popravke;
    @FXML
    private Label troskovi;
    @FXML
    private Label porez;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Izvjestaj izvjestaj = new Izvjestaj(HelloController.getRacuni());

        prihod.setText(String.valueOf(izvjestaj.getPrihod()));
        popust.setText(String.valueOf(izvjestaj.getPopust()));
        promocije.setText(String.valueOf(izvjestaj.getPromocije()));
        iznosVoznji.setText(String.valueOf(izvjestaj.getIznosVoznji()));
        odrzavanje.setText(String.valueOf(izvjestaj.getOdrzavanje()));
        popravke.setText(String.valueOf(izvjestaj.getPopravke()));
        troskovi.setText(String.valueOf(izvjestaj.getTroskovi()));
        porez.setText(String.valueOf(izvjestaj.getPorez()));
    }
}
