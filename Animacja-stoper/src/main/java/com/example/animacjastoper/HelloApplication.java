import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class StopwatchApp extends Application {

    private boolean isRunning = false;
    private long startTime = 0;
    private long elapsedTime = 0;
    private Thread thread;
    private List<Long> loopTimes = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        DecimalFormat df = new DecimalFormat("#0.0");

        Label timeLabel = new Label("0.0");
        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");
        Button resetButton = new Button("Reset");
        Button loopButton = new Button("Loop");
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        startButton.setOnAction(e -> {
            if (!isRunning) {
                isRunning = true;
                startTime = System.currentTimeMillis() - elapsedTime;
                thread = new Thread(() -> {
                    while (isRunning) {
                        long currentTime = System.currentTimeMillis();
                        elapsedTime = currentTime - startTime;
                        Platform.runLater(() -> {
                            timeLabel.setText(df.format(elapsedTime / 1000.0));
                        });
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });

        stopButton.setOnAction(e -> {
            isRunning = false;
        });

        resetButton.setOnAction(e -> {
            isRunning = false;
            elapsedTime = 0;
            timeLabel.setText("0.0");
            loopTimes.clear();
        });

        loopButton.setOnAction(e -> {
            loopTimes.add(elapsedTime);
        });

        root.getChildren().addAll(timeLabel, startButton, stopButton, resetButton, loopButton);

        Scene scene = new Scene(root, 200, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Stopwatch App");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
