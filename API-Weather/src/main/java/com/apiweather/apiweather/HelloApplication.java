package com.apiweather.apiweather;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.w3c.dom.css.CSS2Properties;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HelloApplication extends Application {

    private static final String API_KEY = "181daeb87cf996a470a1df7e03c24745";
    private static final String API_URL = "http://api.openweathermap.org/data/3.0/weather";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("API Weather");

        BorderPane borderPane = new BorderPane();

        // Utwórz górny panel z napisem "API Weather"
        Label titleLabel = new Label("API Weather");
        titleLabel.setStyle("-fx-font-size: 24;");
        borderPane.setTop(titleLabel);

        // Utwórz panel z menu wyszukiwania miasta
        HBox searchBox = new HBox();
        TextField cityField = new TextField();
        Button searchButton = new Button();
        ImageView searchIcon = new ImageView(new Image(getClass().getResourceAsStream("search.png")));
        searchIcon.setFitHeight(20);
        searchIcon.setFitWidth(20);
        searchButton.setGraphic(searchIcon);

        searchButton.setOnAction(e -> {
            String cityName = cityField.getText();
            if (!cityName.isEmpty()) {
                String weatherInfo = getWeatherInfo(cityName);
                displayWeatherInfo(weatherInfo);
            } else {
                // Obsługa błędu - brak wpisanego miasta
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText(null);
                alert.setContentText("Wprowadź nazwę miasta.");
                alert.showAndWait();
            }
        });

        searchBox.getChildren().addAll(cityField, searchButton);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setSpacing(10);
        borderPane.setCenter(searchBox);

        // Utwórz panel z ikoną pogody
        HBox weatherBox = new HBox();
        ImageView weatherIcon = new ImageView();
        weatherBox.getChildren().add(weatherIcon);
        weatherBox.setAlignment(Pos.CENTER);
        borderPane.setBottom(weatherBox);

        // Utwórz nieedytowalny obszar tekstowy na informacje o bieżącej temperaturze
        TextArea weatherInfoArea = new TextArea();
        weatherInfoArea.setEditable(false);
        borderPane.setRight(weatherInfoArea);

        // Utwórz scenę i ustaw ją na primaryStage
        Scene scene = new Scene(borderPane, 400, 300);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private String getWeatherInfo(String cityName) {
        try {
            // Utwórz URL do zapytania API OpenWeatherMap
            String apiUrl = API_URL + "?q=" + cityName + "&appid=" + API_KEY;
            URL url = new URL(apiUrl);

            // Utwórz połączenie HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Odczytaj odpowiedź
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            connection.disconnect();

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void displayWeatherInfo(String weatherInfo) {
        if (weatherInfo != null) {
            // Parsuj dane JSON z odpowiedzi API OpenWeatherMap
            JSONObject json = new JSONObject(weatherInfo);

            // Pobierz ikonę pogody
            String iconCode = json.getJSONArray("weather").getJSONObject(0).getString("icon");
            String iconUrl = "http://openweathermap.org/img/w/" + iconCode + ".png";

            // Ustaw ikonę pogody
            ImageView weatherIcon = new ImageView(new Image(iconUrl));
            CSS2Properties borderPane;
            HBox weatherBox = (HBox) borderPane.getBottom();
            weatherBox.getChildren().clear();
            weatherBox.getChildren().add(weatherIcon);

            // Pobierz informacje o temperaturze
            double temperature = json.getJSONObject("main").getDouble("temp");
            String cityName = json.getString("name");

            // Wyświetl informacje w obszarze tekstowym
            TextArea weatherInfoArea = new TextArea();
            weatherInfoArea.setEditable(false);
            borderPane.setRight(weatherInfoArea);
            weatherInfoArea.setText("Temperatura w " + cityName + ": " + temperature + " °C");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
