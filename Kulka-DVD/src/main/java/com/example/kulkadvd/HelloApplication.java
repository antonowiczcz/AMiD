package com.example.kulkadvd;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {

        Circle ball = new Circle(15, Color.BLUE);
        ball.setTranslateX(100);
        ball.setTranslateY(100);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(20), e -> {

                    ball.setTranslateX(ball.getTranslateX() + deltaX);
                    ball.setTranslateY(ball.getTranslateY() + deltaY);

                    if (ball.getTranslateX() <= 0 || ball.getTranslateX() >= 400) {
                        deltaX *= -1;
                    }
                    if (ball.getTranslateY() <= 0 || ball.getTranslateY() >= 400) {
                        deltaY *= -1;
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Pane root = new Pane();
        root.setPrefSize(400, 400);
        root.getChildren().add(ball);

        Scene scene = new Scene(root, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.setTitle("DVD KULKA");
        primaryStage.show();
    }

    private double deltaX = 5;
    private double deltaY = 3;

    public static void main(String[] args) {
        launch(args);
    }
}