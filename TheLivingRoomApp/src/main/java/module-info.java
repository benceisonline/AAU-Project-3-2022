module com.example {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.mongodb.bson;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires java.management;

    opens controller to javafx.fxml;
    opens launcher to javafx.fxml;
    opens model to javafx.fxml;

    exports model;
    exports controller;
    exports launcher;
}