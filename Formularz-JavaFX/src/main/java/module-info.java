module com.formularz.formularzjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.formularz.formularzjavafx to javafx.fxml;
    exports com.formularz.formularzjavafx;
}