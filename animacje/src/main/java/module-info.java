module com.example.animacje {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.animacje to javafx.fxml;
    exports com.example.animacje;
}