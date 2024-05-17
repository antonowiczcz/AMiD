module com.example.animacja {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.animacja to javafx.fxml;
    exports com.example.animacje;
    opens com.example.animacje to javafx.fxml;
}