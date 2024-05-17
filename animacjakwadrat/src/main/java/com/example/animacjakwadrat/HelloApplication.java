package com.example.animacjakwadrat;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HelloApplication extends Application {
    private RotateTransition rotateTransition;

    public static void startAnimation() {
    }

    @Override
    public void start(Stage stage) {

        Box box = new Box();

        box.setWidth(150.0);
        box.setHeight(150.0);
        box.setDepth(150.0);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.DARKSLATEBLUE);
        box.setMaterial(material);

        rotateTransition = new RotateTransition();

        rotateTransition.setDuration(Duration.millis(3000));
        rotateTransition.setNode(box);
        rotateTransition.setAxis(Rotate.Y_AXIS);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition.setAutoReverse(false);

        box.setTranslateX(325);
        box.setTranslateY(150);
        box.setTranslateZ(0);

        Group root = new Group();
        root.getChildren().add(box);

        // Przyciski start i stop
        Button startButton = new Button("Start");
        startButton.setOnAction(event -> rotateTransition.play());

        Button stopButton = new Button("Stop");
        stopButton.setOnAction(event -> rotateTransition.pause());

        HBox buttonsBox = new HBox(10);
        buttonsBox.getChildren().addAll(startButton, stopButton);
        buttonsBox.setLayoutX(325); // Ustawianie położenia przycisków
        buttonsBox.setLayoutY(320);

        root.getChildren().add(buttonsBox);

        Scene scene = new Scene(root, 1000, 500, true);

        PerspectiveCamera camera = new PerspectiveCamera();
        scene.setCamera(camera);

        stage.setTitle("Sześcian");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}