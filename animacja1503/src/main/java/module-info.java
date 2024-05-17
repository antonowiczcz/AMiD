module com.example.animacja1503 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.animacja1503 to javafx.fxml;
    exports com.example.animacja1503;
    exports com.example.animacje;
    opens com.example.animacje to javafx.fxml;
}