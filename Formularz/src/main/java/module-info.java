module com.example.formularz {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.formularz to javafx.fxml;
    exports com.example.formularz;
}