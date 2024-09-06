module com.example.pj2_2024 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pj2_2024 to javafx.fxml;
    exports com.example.pj2_2024;
    exports com.example.pj2_2024.Vozilo;
    exports com.example.pj2_2024.Kvar;
}