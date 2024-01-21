module com.weather.weatherapi {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens com.weather.weatherapi to javafx.fxml;
    exports com.weather.weatherapi;
}