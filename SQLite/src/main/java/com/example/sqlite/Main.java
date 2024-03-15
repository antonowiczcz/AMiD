package com.example.sqlite;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public class Main extends Application {

    private Connection conn;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Database App");

        connect();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        ComboBox<String> menu = new ComboBox<>();
        menu.getItems().addAll("Create Table", "Add Records", "Search", "Exit");
        menu.setValue("Choose option");
        grid.add(menu, 0, 0);

        menu.setOnAction(e -> {
            String selectedOption = menu.getSelectionModel().getSelectedItem();
            switch (selectedOption) {
                case "Create Table":
                    createTableForm(grid);
                    break;
                case "Add Records":
                    addRecordsForm(grid);
                    break;
                case "Search":
                    searchForm(grid);
                    break;
                case "Exit":
                    primaryStage.close();
                    break;
            }
        });

        Scene scene = new Scene(grid, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createTableForm(GridPane grid) {

        grid.getChildren().clear();

        Label tableNameLabel = new Label("Table Name:");
        GridPane.setConstraints(tableNameLabel, 0, 0);
        TextField tableNameInput = new TextField();
        GridPane.setConstraints(tableNameInput, 1, 0);

        Label numColumnsLabel = new Label("Number of Columns:");
        GridPane.setConstraints(numColumnsLabel, 0, 1);
        TextField numColumnsInput = new TextField();
        GridPane.setConstraints(numColumnsInput, 1, 1);

        Button submitButton = new Button("Submit");
        GridPane.setConstraints(submitButton, 1, 2);
        submitButton.setOnAction(e -> {
            try {
                int numColumns = Integer.parseInt(numColumnsInput.getText());
                createDynamicTableForm(grid, tableNameInput.getText(), numColumns);
            } catch (NumberFormatException ex) {
                showAlert("Invalid number of columns: " + ex.getMessage());
            }
        });

        Button switchButton = new Button("Switch Mode");
        GridPane.setConstraints(switchButton, 1, 3);
        switchButton.setOnAction(e -> addRecordsForm(grid));

        grid.getChildren().addAll(tableNameLabel, tableNameInput, numColumnsLabel, numColumnsInput, submitButton, switchButton);
    }

    private void createDynamicTableForm(GridPane grid, String tableName, int numColumns) {

        grid.getChildren().clear();

        List<TextField> nameInputs = new ArrayList<>();
        List<ComboBox<String>> typeInputs = new ArrayList<>();
        List<CheckBox> keyInputs = new ArrayList<>();
        List<CheckBox> aiInputs = new ArrayList<>();

        for (int i = 0; i < numColumns; i++) {
            Label nameLabel = new Label("Column Name:");
            GridPane.setConstraints(nameLabel, 0, i + 1);
            TextField nameInput = new TextField();
            GridPane.setConstraints(nameInput, 1, i + 1);
            nameInputs.add(nameInput);

            Label typeLabel = new Label("Data Type:");
            GridPane.setConstraints(typeLabel, 2, i + 1);
            ComboBox<String> typeInput = new ComboBox<>();
            typeInput.getItems().addAll("TEXT", "INTEGER", "REAL", "BLOB");
            typeInput.setValue("Choose type");
            GridPane.setConstraints(typeInput, 3, i + 1);
            typeInputs.add(typeInput);

            CheckBox keyInput = new CheckBox("Primary Key");
            GridPane.setConstraints(keyInput, 4, i + 1);
            keyInputs.add(keyInput);

            CheckBox aiInput = new CheckBox("Auto Increment");
            GridPane.setConstraints(aiInput, 5, i + 1);
            aiInputs.add(aiInput);

            grid.getChildren().addAll(nameLabel, nameInput, typeLabel, typeInput, keyInput, aiInput);
        }

        Button submitButton = new Button("Submit");
        GridPane.setConstraints(submitButton, 1, numColumns + 1);
        submitButton.setOnAction(e -> {
            try {
                StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
                sql.append(tableName).append(" (");
                for (int i = 0; i < nameInputs.size(); i++) {
                    sql.append(nameInputs.get(i).getText()).append(" ").append(typeInputs.get(i).getValue());
                    if (keyInputs.get(i).isSelected()) {
                        sql.append(" PRIMARY KEY");
                    }
                    if (aiInputs.get(i).isSelected()) {
                        sql.append(" AUTOINCREMENT");
                    }
                    if (i < nameInputs.size() - 1) {
                        sql.append(", ");
                    }
                }
                sql.append(")");
                PreparedStatement pstmt = conn.prepareStatement(sql.toString());
                pstmt.executeUpdate();
                showAlert("Table created successfully.");
            } catch (SQLException ex) {
                showAlert("Error creating table: " + ex.getMessage());
            }
        });

        grid.getChildren().add(submitButton);
    }

    private List<String> getTableNames() {
        List<String> tableNames = new ArrayList<>();
        try {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                tableNames.add(rs.getString(3));
            }
        } catch (SQLException e) {
            showAlert("Error retrieving table names: " + e.getMessage());
        }
        return tableNames;
    }

    private void addRecordsForm(GridPane grid) {

        grid.getChildren().clear();

        Label tableSelectLabel = new Label("Select Table:");
        GridPane.setConstraints(tableSelectLabel, 0, 0);
        ComboBox<String> tableSelect = new ComboBox<>();
        tableSelect.getItems().addAll(getTableNames());
        tableSelect.setValue("Choose table");
        GridPane.setConstraints(tableSelect, 1, 0);

        Button submitButton = new Button("Submit");
        GridPane.setConstraints(submitButton, 1, 1);
        submitButton.setOnAction(e -> addDynamicRecordsForm(grid, tableSelect.getValue()));

        Button switchButton = new Button("Switch Mode");
        GridPane.setConstraints(switchButton, 1, 2);
        switchButton.setOnAction(e -> createTableForm(grid));

        grid.getChildren().addAll(tableSelectLabel, tableSelect, submitButton, switchButton);
    }

    private Map<String, String> getTableStructure(String tableName) {
        Map<String, String> tableStructure = new LinkedHashMap<>();
        try {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getColumns(null, null, tableName, null);
            while (rs.next()) {
                tableStructure.put(rs.getString("COLUMN_NAME"), rs.getString("TYPE_NAME"));
            }
        } catch (SQLException e) {
            showAlert("Error retrieving table structure: " + e.getMessage());
        }
        return tableStructure;
    }

    private void addDynamicRecordsForm(GridPane grid, String tableName) {

        grid.getChildren().clear();


        Map<String, String> tableStructure = getTableStructure(tableName);
        List<TextField> recordInputs = new ArrayList<>();
        int i = 0;
        for (String columnName : tableStructure.keySet()) {
            Label recordLabel = new Label(columnName + ":");
            GridPane.setConstraints(recordLabel, 0, i + 1);
            TextField recordInput = new TextField();
            GridPane.setConstraints(recordInput, 1, i + 1);
            recordInputs.add(recordInput);

            grid.getChildren().addAll(recordLabel, recordInput);
            i++;
        }

        Button submitButton = new Button("Submit");
        GridPane.setConstraints(submitButton, 1, i + 1);
        submitButton.setOnAction(e -> {
            try {
                StringBuilder sql = new StringBuilder("INSERT INTO ");
                sql.append(tableName).append(" (");
                sql.append(String.join(", ", tableStructure.keySet()));
                sql.append(") VALUES (");
                sql.append("?, ".repeat(tableStructure.size() - 1)).append("?)");
                PreparedStatement pstmt = conn.prepareStatement(sql.toString());
                for (int j = 0; j < recordInputs.size(); j++) {
                    pstmt.setString(j + 1, recordInputs.get(j).getText());
                }
                pstmt.executeUpdate();
                showAlert("Record added successfully.");
            } catch (SQLException ex) {
                showAlert("Error adding record: " + ex.getMessage());
            }
        });

        grid.getChildren().add(submitButton);
    }

    private void searchForm(GridPane grid) {

        grid.getChildren().clear();

        Label nameLabel = new Label("Table Name:");
        GridPane.setConstraints(nameLabel, 0, 1);
        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 1);

        Label columnLabel = new Label("Column Name:");
        GridPane.setConstraints(columnLabel, 0, 2);
        TextField columnInput = new TextField();
        GridPane.setConstraints(columnInput, 1, 2);

        Label searchLabel = new Label("Search:");
        GridPane.setConstraints(searchLabel, 0, 3);
        TextField searchInput = new TextField();
        GridPane.setConstraints(searchInput, 1, 3);

        Button submitButton = new Button("Submit");
        GridPane.setConstraints(submitButton, 1, 4);
        submitButton.setOnAction(e -> {
            try {
                String sql = "SELECT * FROM " + nameInput.getText() + " WHERE " + columnInput.getText() + " = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, searchInput.getText());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    showAlert("Found at: " + rs.getInt(1));
                }
            } catch (SQLException ex) {
                showAlert("Error searching record: " + ex.getMessage());
            }
        });

        grid.getChildren().addAll(nameLabel, nameInput, columnLabel, columnInput, searchLabel, searchInput, submitButton);
    }

    private void connect() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Connection to SQLite database established.");
        } catch (SQLException e) {
            showAlert("Error connecting to database: " + e.getMessage());
        }
    }

    @Override
    public void stop() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Connection to SQLite database closed.");
            }
        } catch (SQLException e) {
            showAlert("Error closing database connection: " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}