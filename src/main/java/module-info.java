module com.example.tema1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.junit.jupiter.api;
    requires junit;

    opens com.example.tema1 to javafx.fxml, org.junit.jupiter.api;
    exports com.example.tema1;
    exports com.example.tema1.model;
    exports com.example.tema1.viewController;
    opens com.example.tema1.viewController to javafx.fxml;


}