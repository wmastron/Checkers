module checkers {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens ui to javafx.fxml;
    exports ui;
    exports core;
}