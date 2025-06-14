module g {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    opens g to javafx.fxml;
    exports g;
}

