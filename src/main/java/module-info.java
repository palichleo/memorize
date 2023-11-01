module com.memorize.memorize {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.memorize.memorize to javafx.fxml;
    exports com.memorize.memorize;
    exports com.memorize.memorize.controller;
    opens com.memorize.memorize.controller to javafx.fxml;
    exports com.memorize.memorize.stages;
    opens com.memorize.memorize.stages to javafx.fxml;
    exports com.memorize.memorize.logic;
    opens com.memorize.memorize.logic to javafx.fxml;
    exports com.memorize.memorize.logic.security;
    opens com.memorize.memorize.logic.security to javafx.fxml;
}