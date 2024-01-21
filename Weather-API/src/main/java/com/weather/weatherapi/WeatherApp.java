package com.weather.weatherapi;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class WeatherApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Aplikacja Pogodowa");

        TextField miejsceTextField = new TextField();
        Button searchButton = new Button();
        ImageView searchIcon = new ImageView(new Image(getClass().getResourceAsStream("/search.png")));
        searchIcon.setFitWidth(16);
        searchIcon.setFitHeight(16);
        searchButton.setGraphic(searchIcon);

        TextArea wynikiTextArea = new TextArea();
        wynikiTextArea.setEditable(false);

        searchButton.setOnAction(event -> {
            String miejscowosc = miejsceTextField.getText().trim();
            if (!miejscowosc.isEmpty()) {
                try {
                    String currentWeather = getWeatherData(miejscowosc);
                    String forecast = getForecastData(miejscowosc);
                    wynikiTextArea.setText(currentWeather + "\n\nPrognoza na kilka dni:\n" + forecast);
                } catch (IOException e) {
                    wynikiTextArea.setText("Błąd podczas pobierania danych pogodowych.");
                    e.printStackTrace();
                }
            }
        });

        HBox hbox = new HBox(miejsceTextField, searchButton);
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10));

        VBox vbox = new VBox(hbox, wynikiTextArea);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String getWeatherData(String location) throws IOException {
        String apiKey = "dla-bezpieczenstwa-usuniety";
        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + apiKey + "&units=metric";

        String jsonData = fetchData(apiUrl);
        JSONObject weatherJson = new JSONObject(jsonData);

        return formatWeatherData(weatherJson);
    }

    private String getForecastData(String location) throws IOException {
        String apiKey = "dla-bezpieczenstwa-usuniety";
        String apiUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" + location + "&appid=" + apiKey + "&units=metric";

        String jsonData = fetchData(apiUrl);
        JSONObject forecastJson = new JSONObject(jsonData);

        return formatForecastData(forecastJson.getJSONArray("list"));
    }

    private String fetchData(String apiUrl) throws IOException {
        try (InputStream inputStream = new URL(apiUrl).openStream()) {
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }

    private String formatWeatherData(JSONObject weatherJson) {
        double temperature = weatherJson.getJSONObject("main").getDouble("temp");
        String description = weatherJson.getJSONArray("weather").getJSONObject(0).getString("description");

        return String.format("Temperatura: %.1f°C\nOpis: %s", temperature, description);
    }

    private String formatForecastData(JSONArray forecastJsonArray) {
        StringBuilder forecastStringBuilder = new StringBuilder();

        for (int i = 0; i < forecastJsonArray.length(); i++) {
            JSONObject forecastObject = forecastJsonArray.getJSONObject(i);
            double temperature = forecastObject.getJSONObject("main").getDouble("temp");
            String description = forecastObject.getJSONArray("weather").getJSONObject(0).getString("description");
            String date = forecastObject.getString("dt_txt");

            forecastStringBuilder.append(String.format("Data: %s, Temperatura: %.1f°C, Opis: %s\n", date, temperature, description));
        }

        return forecastStringBuilder.toString();
    }
}
