module com.example.animacjakwadrat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.animacjakwadrat to javafx.fxml;
    exports com.example.animacjakwadrat;
}