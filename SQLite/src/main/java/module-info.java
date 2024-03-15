module com.example.sqlite {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.example.sqlite to javafx.fxml;
    exports com.example.sqlite;
}