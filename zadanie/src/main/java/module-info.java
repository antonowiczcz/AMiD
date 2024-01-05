module com.example.zadanie {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.zadanie to javafx.fxml;
    exports com.example.zadanie;
}