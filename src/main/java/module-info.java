module g {
    requires javafx.controls;
    requires javafx.fxml;

    opens g to javafx.fxml;
    exports g;
}
