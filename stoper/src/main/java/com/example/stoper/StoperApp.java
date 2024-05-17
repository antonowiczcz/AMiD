package com.example.stoper;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class StoperApp extends Application {
    private Stoper stoper;
    private Label timeLabel;
    private VBox loopsBox;
    private List<Long> loops = new ArrayList<>();

    Line clockHand = new Line(0, 150, 0, -150);

    @Override
    public void start(Stage primaryStage) {
        timeLabel = new Label("00:00:00.000");
        stoper = new Stoper(timeLabel);
        loopsBox = new VBox();

        Button startButton = new Button("Start");
        startButton.setOnAction(e -> stoper.startStoper());

        Button stopButton = new Button("Stop");
        stopButton.setOnAction(e -> stoper.stopStoper());

        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> {
            stoper.resetStoper();
            loops.clear();
            loopsBox.getChildren().clear();
        });

        Button loopButton = new Button("Loop");
        loopButton.setOnAction(e -> saveLoop());

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        gridPane.add(startButton, 0, 0);
        gridPane.add(stopButton, 1, 0);
        gridPane.add(resetButton, 2, 0);
        gridPane.add(loopButton, 3, 0);
        gridPane.add(timeLabel, 0, 1, 4, 1);

        Circle clockFace = new Circle(150, 150, 150);

        clockHand.setStroke(Color.WHITE);

        Pane clockPane = new Pane(clockFace, clockHand);
        clockHand.setTranslateX(150);
        clockHand.setTranslateY(150);

        BorderPane root = new BorderPane();
        root.setCenter(clockPane);
        root.setBottom(gridPane);
        root.setRight(loopsBox);

        Scene scene = new Scene(root, 500, 500);

        primaryStage.setTitle("Stoper App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveLoop() {
        long loopTime = stoper.getElapsedTime();
        loops.add(loopTime);
        Label loopLabel = new Label(formatTime(loopTime));
        loopsBox.getChildren().add(loopLabel);
    }

    private String formatTime(long time) {
        long millis = time % 1000;
        long seconds = (time / 1000) % 60;
        long minutes = (time / (1000 * 60)) % 60;
        long hours = (time / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public class Stoper {
        private long startTime;
        private boolean running;
        private long elapsedTime;

        private Label timeLabel;
        private Thread thread;

        RotateTransition rotateTransition = new RotateTransition();

        public Stoper(Label timeLabel) {
            this.timeLabel = timeLabel;

            rotateTransition.setDuration(Duration.seconds(1));
            rotateTransition.setNode(clockHand);
            rotateTransition.setByAngle(360);
            rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        }

        public void startStoper() {
            if (!running) {
                if (elapsedTime == 0) {
                    startTime = System.currentTimeMillis();
                } else {
                    startTime = System.currentTimeMillis() - elapsedTime;
                }
                running = true;
                thread = new Thread(() -> {
                    while (running) {
                        elapsedTime = System.currentTimeMillis() - startTime;
                        updateUI();
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();

                rotateTransition.play();
            }
        }

        public void stopStoper() {
            running = false;
            rotateTransition.stop();
        }

        public void resetStoper() {
            running = false;
            elapsedTime = 0;
            updateUI();

            rotateTransition.stop();
            rotateTransition.setFromAngle(0);
        }

        public long getElapsedTime() {
            return elapsedTime;
        }

        private void updateUI() {
            Platform.runLater(() -> {
                long millis = elapsedTime % 1000;
                long seconds = (elapsedTime / 1000) % 60;
                long minutes = (elapsedTime / (1000 * 60)) % 60;
                long hours = (elapsedTime / (1000 * 60 * 60)) % 24;

                String timeStr = String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
                timeLabel.setText(timeStr);
            });
        }
    }
}