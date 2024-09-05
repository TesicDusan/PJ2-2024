package com.example.pj2_2024;

import com.example.pj2_2024.Vozilo.Automobil;
import com.example.pj2_2024.Vozilo.EBike;
import com.example.pj2_2024.Vozilo.ETrotinet;
import com.example.pj2_2024.Vozilo.Vozilo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;
import java.util.List;

public class VozilaController {

    //Tabela automobila
    @FXML
    private TableView<Automobil> automobili;
    @FXML
    private TableColumn<Automobil, String> automobilId;
    @FXML
    private TableColumn<Automobil, String> automobilProizvodjac;
    @FXML
    private TableColumn<Automobil, String> automobilModel;
    @FXML
    private TableColumn<Automobil, Integer> automobilCijena;
    @FXML
    private TableColumn<Automobil, Integer> automobilBaterija;
    @FXML
    private TableColumn<Automobil, Date> datumNabavke;
    @FXML
    private TableColumn<Automobil, String> opis;

    //Tabela bicikala
    @FXML
    private TableView<EBike> bicikli;
    @FXML
    private TableColumn<EBike, String> biciklId;
    @FXML
    private TableColumn<EBike, String> biciklProizvodjac;
    @FXML
    private TableColumn<EBike, String> biciklModel;
    @FXML
    private TableColumn<EBike, Integer> biciklCijena;
    @FXML
    private TableColumn<EBike, Integer> biciklBaterija;
    @FXML
    private TableColumn<EBike, Integer> domet;

    //Tabela trotineta
    @FXML
    private  TableView<ETrotinet> trotineti;
    @FXML
    private TableColumn<ETrotinet, String> trotinetId;
    @FXML
    private TableColumn<ETrotinet, String> trotinetProizvodjac;
    @FXML
    private TableColumn<ETrotinet, String> trotinetModel;
    @FXML
    private TableColumn<ETrotinet, Integer> trotinetCijena;
    @FXML
    private TableColumn<ETrotinet, Integer> trotinetBaterija;
    @FXML
    private TableColumn<ETrotinet, Integer> maxBrzina;

    public void ucitajVozila(List<Vozilo> vozila) {
        ObservableList<Automobil> automobils = FXCollections.observableArrayList();
        ObservableList<EBike> eBikes = FXCollections.observableArrayList();
        ObservableList<ETrotinet> eTrotinets = FXCollections.observableArrayList();

        for(Vozilo vozilo : vozila) {
            if(vozilo instanceof Automobil automobil) automobils.add(automobil);
            else if(vozilo instanceof EBike eBike) eBikes.add(eBike);
            else if(vozilo instanceof  ETrotinet eTrotinet) eTrotinets.add(eTrotinet);
        }

        automobilId.setCellValueFactory(new PropertyValueFactory<>("id"));
        automobilProizvodjac.setCellValueFactory(new PropertyValueFactory<>("proizvodjac"));
        automobilModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        automobilCijena.setCellValueFactory(new PropertyValueFactory<>("cijenaNabavke"));
        automobilBaterija.setCellValueFactory(new PropertyValueFactory<>("nivoBaterije"));
        datumNabavke.setCellValueFactory(new PropertyValueFactory<>("datumNabavke"));
        opis.setCellValueFactory(new PropertyValueFactory<>("opis"));
        automobili.setItems(automobils);

        biciklId.setCellValueFactory(new PropertyValueFactory<>("id"));
        biciklProizvodjac.setCellValueFactory(new PropertyValueFactory<>("proizvodjac"));
        biciklModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        biciklCijena.setCellValueFactory(new PropertyValueFactory<>("cijenaNabavke"));
        biciklBaterija.setCellValueFactory(new PropertyValueFactory<>("nivoBaterije"));
        domet.setCellValueFactory(new PropertyValueFactory<>("domet"));
        bicikli.setItems(eBikes);

        trotinetId.setCellValueFactory(new PropertyValueFactory<>("id"));
        trotinetProizvodjac.setCellValueFactory(new PropertyValueFactory<>("proizvodjac"));
        trotinetModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        trotinetCijena.setCellValueFactory(new PropertyValueFactory<>("cijenaNabavke"));
        trotinetBaterija.setCellValueFactory(new PropertyValueFactory<>("nivoBaterije"));
        maxBrzina.setCellValueFactory(new PropertyValueFactory<>("maxBrzina"));
        trotineti.setItems(eTrotinets);
    }
}
