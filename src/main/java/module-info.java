module com.example.resumebuilder {
    requires javafx.controls;
    requires javafx.fxml;
    requires itextpdf;
    requires log4j;
    requires java.sql;


    opens com.example.resumebuilder to javafx.fxml;
    exports com.example.resumebuilder;
}