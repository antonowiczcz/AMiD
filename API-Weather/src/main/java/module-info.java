module com.apiweather.apiweather {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires jdk.xml.dom;


    opens com.apiweather.apiweather to javafx.fxml;
    exports com.apiweather.apiweather;
}