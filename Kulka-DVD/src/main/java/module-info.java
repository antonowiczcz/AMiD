module com.example.kulkadvd {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kulkadvd to javafx.fxml;
    exports com.example.kulkadvd;
}