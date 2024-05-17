package com.example.animacja1503;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXML;

public class HelloController {
    @FXML
    protected void onStartButtonClick() {
        HelloApplication.startAnimation();
    }

    @FXML
    protected void onStopButtonClick() {
        HelloApplication.stopAnimation();
    }

    @FXML
    protected void onAuthorButtonClick() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Author Information");
        alert.setHeaderText(null);
        alert.setContentText("Autor: Your Name");

        alert.showAndWait();
    }
}