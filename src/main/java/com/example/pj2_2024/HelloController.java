package com.example.pj2_2024;

import com.example.pj2_2024.Iznajmljivanje.Iznajmljivanje;
import com.example.pj2_2024.Korisnik.Korisnik;
import com.example.pj2_2024.Racun.Racun;
import com.example.pj2_2024.Simulacija.Simulacija;
import com.example.pj2_2024.Vozilo.Automobil;
import com.example.pj2_2024.Vozilo.EBike;
import com.example.pj2_2024.Vozilo.ETrotinet;
import com.example.pj2_2024.Vozilo.Vozilo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class HelloController implements Initializable {
    private static final int BROJ_KOLONA = 20;
    private static final int BROJ_REDOVA = 20;
    private static final int V_BROJ_ATRIBUTA = 9;
    private static final int I_BROJ_ATRIBUTA = 10;

    private static final String COMMA_DELIMITER = ",";
    private static final String V_CSV_NAME = "PJ2 - projektni zadatak 2024 - Prevozna sredstva.csv";
    private static final String I_CSV_NAME = "PJ2 - projektni zadatak 2024 - Iznajmljivanja.csv";

    @FXML
    private VBox vbox;
    @FXML
    private Label datum;
    private static GridPane pane;
    private static final SimpleDateFormat vFormatter = new SimpleDateFormat("dd.MM.yyyy.");
    private static final SimpleDateFormat iFormatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
    private final List<Vozilo> vozila = new ArrayList<>();
    private List<Iznajmljivanje> iznajmljivanja = new ArrayList<>();
    private static List<Racun> racuni = new ArrayList<>();


    /**
     * Metoda koja pokrece izvrsavanje simulacije.
     */
    @FXML
    private void onPokreniButtonClick() {
        Map<Date, List<Iznajmljivanje>> grupisanaIznajmljivanja = iznajmljivanja.stream()
                .collect(Collectors.groupingBy(Iznajmljivanje::getDatumIznajmljivanja));

        Simulacija simulacija = new Simulacija(grupisanaIznajmljivanja, this);
        simulacija.pokreni();
    }

    /**
     * Metoda koja otvara novi prozor na kome ce biti prikazana sva ucitana vozila.
     */
    @FXML
    private void onSvaVozilaButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloController.class.getResource("vozila-view.fxml"));
            Parent root = loader.load();

            VozilaController controller = loader.getController();
            controller.ucitajVozila(vozila);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Vozila");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda koja otvara novi prozor na kome ce bit prikazani svi kvarovi koji su se dogodili tokom simulacije.
     */
    @FXML
    private void onKvaroviButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloController.class.getResource("kvarovi-view.fxml"));
            Parent root = loader.load();

            KvaroviController controller = loader.getController();
            controller.ucitajKvarove(vozila);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Kvarovi");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda koja otvara novi prozor na kome ce biti prikazane opcije poslovanja.
     */
    @FXML
    private void onPoslovanjeButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloController.class.getResource("poslovanje-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Napravi direktorij u koji ce biti smjesteni racuni
        File file = new File("Racuni");
        file.mkdir();
        //Napravi grid
        pane = new GridPane();
        for(int i = 0; i < BROJ_REDOVA; i++) {
            RowConstraints rowConstraint = new RowConstraints(21);
            pane.getRowConstraints().add(rowConstraint);
        }
        for(int i = 0; i < BROJ_KOLONA; i++) {
            ColumnConstraints columnConstraint = new ColumnConstraints(41);
            pane.getColumnConstraints().add(columnConstraint);
        }
        pane.setGridLinesVisible(true);
        VBox.setVgrow(pane, Priority.ALWAYS);
        vbox.getChildren().add(pane);

        //Ucitaj vozila
        try {
            List<List<String>> lines = Files.readAllLines(Paths.get(Objects.requireNonNull(HelloController.class.getResource(V_CSV_NAME)).toURI())).stream()
                    .map(line -> Arrays.asList(line.split(COMMA_DELIMITER))).toList();
            for(List<String> line : lines) {
                if(provjeriVozilo(line)) {
                    if ("automobil".equals(line.get(8))) vozila.add(new Automobil(line.get(0), line.get(1), line.get(2),
                            vFormatter.parse(line.get(3)), Integer.parseInt(line.get(4)), line.get(7)));
                    else if ("bicikl".equals(line.get(8))) vozila.add(new EBike(line.get(0), line.get(1), line.get(2),
                            Integer.parseInt(line.get(4)), Integer.parseInt(line.get(5))));
                    else if ("trotinet".equals(line.get(8)))
                        vozila.add(new ETrotinet(line.get(0), line.get(1), line.get(2),
                                Integer.parseInt(line.get(4)), Integer.parseInt(line.get(6))));
                }
            }
        } catch (IOException | ParseException | URISyntaxException e) {
            e.printStackTrace();
        }

        //Ucitaj iznajmljivanja
        try {
            List<List<String>> lines = Files.readAllLines(Paths.get(Objects.requireNonNull(HelloController.class.getResource(I_CSV_NAME)).toURI())).stream()
                    .map(line -> Arrays.asList(line.split(COMMA_DELIMITER))).toList();
            for(List<String> line : lines) {
                if (!line.equals(lines.get(0)) && provjeriIznajmljivanje(line)) iznajmljivanja.add(new Iznajmljivanje(iFormatter.parse(line.get(0)),
                        new Korisnik(line.get(1), Korisnik.generisiStranac()), searchVozila(line.get(2)), toIntArray(line.get(3), line.get(4)), toIntArray(line.get(5),
                        line.get(6)), Integer.parseInt(line.get(7)), line.get(8), line.get(9)));
            }
            iznajmljivanja = iznajmljivanja.stream()
                    .sorted(Comparator.comparing(Iznajmljivanje::getDatumIznajmljivanja))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    /**
     * Pomocna metoda koja se koristi da ucitane vrijednosti koordinata pretvori u niz dva int-a.
     * @param string1 x koordinata
     * @param string2 y koordinata
     * @return niz dva int-a koji predstavlja koordinate
     */
    public int[] toIntArray(String string1, String string2) {
        int[] tmp = new int[2];
        tmp[0] = Integer.parseInt(string1.replace("\"", ""));
        tmp[1] = Integer.parseInt(string2.replace("\"", ""));
        return tmp;
    }

    /**
     * Pomocna metoda koja provjerava da li se vozilo sa zadanim identifikatorom nalazi u listi vozila.
     * @param id identifikator vozila
     * @return objekat vozila ukoliko se ono nalazi u listi
     */
    public Vozilo searchVozila(String id) {
        for (Vozilo vozilo : vozila) {
            if (id.equals(vozilo.getId())) return vozilo;
        }
        return null;
    }

    public Label getDatum() { return datum; }
    public static GridPane getPane() { return pane; }
    public static void addRacun(Racun racun) { racuni.add(racun); }
    public static List<Racun> getRacuni() { return racuni; }

    /**
     * Pomocna metoda koja provjerava da li ucitana linija fajla ima odgovarajuci broj atributa i preskace prvu liniju fajla.
     * @param line linija fajla ciji se sadrzaj provjerava
     * @return true ako je linija validna, false ako nije
     */
    public boolean provjeriVozilo(List<String> line) {
        return line.size() == V_BROJ_ATRIBUTA && searchVozila(line.get(0)) == null;
    }

    /**
     * Pomocna metoda koja provjerava da li ucitana linija fajla ima odgovarajuci broj atributa i preskace prvu liniju fajla.
     * @param line linija fajla ciji se sadrzaj provjerava
     * @return true ako je linija validna, false ako nije
     */
    public boolean provjeriIznajmljivanje(List<String> line) {
        //Provjera broja atributa
        if(line.size() != I_BROJ_ATRIBUTA) {
            System.out.println("Pogresan broj atributa pri ucitavanju iznajmljivanja!");
            return false;
        }
        //Provjera dvostrukog iznajmljivanja
        for (Iznajmljivanje iznajmljivanje : iznajmljivanja.stream().filter(i -> {
            try {
                return i.getDatumIznajmljivanja().equals(iFormatter.parse(line.get(0)));
            } catch (ParseException e) {
                e.printStackTrace();
            } return false;
        }).toList()) if (iznajmljivanje.getVozilo().getId().equals(line.get(2))) {
            System.out.println("Dvostruko iznajmljivanje istog vozila!");
            return false;
        }
        //Provjera opsega koordinata
        for(int i = 3; i <= 6; i++) {
            int j = Integer.parseInt(line.get(i).replace("\"", ""));
            if(j < 0 || j > 19) {
                System.out.println("Koordinate van opsega!");
                return false;
            }
        }
        //Provjera postojanja vozila
        if(searchVozila(line.get(2)) == null) {
            System.out.println("Neposojece vozilo!");
            return false;
        }
        return true;
    }
}