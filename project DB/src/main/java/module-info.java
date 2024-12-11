
module com.example.id313524159 {
    requires javafx.controls;
    requires javafx.fxml;
    
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires java.sql;
    requires transitive javafx.graphics;

    opens Application to javafx.fxml;
    exports Application;
    exports control;
    opens control to javafx.fxml;


}