module com.example.animacjastoper {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.animacjastoper to javafx.fxml;
    exports com.example.animacjastoper;
}