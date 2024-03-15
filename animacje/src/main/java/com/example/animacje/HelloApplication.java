package com.example.animacje;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {

        Box box = new Box();

        box.setWidth(150.0);
        box.setHeight(150.0);
        box.setDepth(150.0);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.DARKSLATEBLUE);
        box.setMaterial(material);

        RotateTransition rotateTransition = new RotateTransition();

        rotateTransition.setDuration(Duration.millis(3000));
        rotateTransition.setNode(box);
        rotateTransition.setAxis(Rotate.Y_AXIS);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition.setAutoReverse(false);


        rotateTransition.play();

        Group root = new Group();
        root.getChildren().add(box);

        Scene scene = new Scene(root, 800, 300, true);

        root.setLayoutX(scene.getWidth() / 2);
        root.setLayoutY(scene.getHeight() / 2);

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