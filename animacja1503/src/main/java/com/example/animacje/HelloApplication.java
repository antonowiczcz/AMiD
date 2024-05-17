package com.example.animacja1503;


import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HelloApplication extends Application {
    private static RotateTransition rotateTransition;

    @Override
    public void start(Stage stage) throws IOException {

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

        Group root = new Group(box);

        Scene scene = new Scene(root, 800, 300, true);

        root.setLayoutX(scene.getWidth() / 2);
        root.setLayoutY(scene.getHeight() / 2);

        PerspectiveCamera camera = new PerspectiveCamera();
        scene.setCamera(camera);

        stage.setTitle("Sześcian");

        Parent fxmlRoot = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        ((Group) scene.getRoot()).getChildren().add(fxmlRoot);

        stage.setScene(scene);

        stage.show();
    }

    public static void startAnimation() {
        rotateTransition.play();
    }

    public static void stopAnimation() {
        rotateTransition.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}