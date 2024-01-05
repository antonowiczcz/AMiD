package com.example.formularz;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.regex.Pattern;

public class HelloApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Formularz danych osobowych");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(new Label("Imię:"), 0, 0);
        grid.add(new Label("Nazwisko:"), 0, 1);
        grid.add(new Label("Adres:"), 0, 2);
        grid.add(new Label("Miejscowość:"), 0, 3);
        grid.add(new Label("Telefon:"), 0, 4);
        grid.add(new Label("Email:"), 0, 5);

        TextField firstNameField = createTextField();
        TextField lastNameField = createTextField();
        TextField addressField = createTextField();
        TextField cityField = createTextField();
        TextField phoneField = createTextField();
        TextField emailField = createTextField();

        Button submitButton = new Button("Zatwierdź");
        submitButton.setDisable(true);

        Button resetButton = new Button("Resetuj");
        resetButton.setOnAction(e -> {
            firstNameField.clear();
            lastNameField.clear();
            addressField.clear();
            cityField.clear();
            phoneField.clear();
            emailField.clear();
            submitButton.setDisable(true);
        });

        submitButton.setOnAction(e -> {
            // Tutaj umieść kod obsługujący zatwierdzenie danych
            System.out.println("Dane zatwierdzone!");
        });

        grid.add(firstNameField, 1, 0);
        grid.add(lastNameField, 1, 1);
        grid.add(addressField, 1, 2);
        grid.add(cityField, 1, 3);
        grid.add(phoneField, 1, 4);
        grid.add(emailField, 1, 5);
        grid.add(submitButton, 0, 6);
        grid.add(resetButton, 1, 6);

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Dodaj walidację pól
        addValidation(firstNameField, "[a-zA-Z]{2,}", "Imię musi składać się z co najmniej 2 liter.");
        addValidation(lastNameField, "[a-zA-Z]{2,}", "Nazwisko musi składać się z co najmniej 2 liter.");
        addValidation(addressField, ".+", "Adres nie może być pusty.");
        addValidation(cityField, "[a-zA-Z\\s]{3,}", "Miejscowość musi składać się z co najmniej 3 liter.");
        addValidation(phoneField, "\\d{9,}", "Numer telefonu musi składać się z co najmniej 9 cyfr.");
        addValidation(emailField, "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b",
                "Niepoprawny format adresu email.");

        // Aktywuj przycisk "Zatwierdź" tylko gdy wszystkie pola są poprawne
        enableSubmitButtonWhenValid(submitButton, firstNameField, lastNameField, addressField,
                cityField, phoneField, emailField);
    }

    private TextField createTextField() {
        return new TextField();
    }

    private void addValidation(TextField textField, String pattern, String errorMessage) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Pattern.matches(pattern, newValue)) {
                textField.setStyle("-fx-border-color: red;");
                textField.setTooltip(new Tooltip(errorMessage));
            } else {
                textField.setStyle(null);
                textField.setTooltip(null);
            }
        });
    }

    private void enableSubmitButtonWhenValid(Button submitButton, TextField... fields) {
        for (TextField field : fields) {
            field.textProperty().addListener((observable, oldValue, newValue) -> {
                boolean allFieldsValid = true;
                for (TextField f : fields) {
                    if (f.getStyle() != null) {
                        allFieldsValid = false;
                        break;
                    }
                }
                submitButton.setDisable(!allFieldsValid);
            });
        }
    }
}
