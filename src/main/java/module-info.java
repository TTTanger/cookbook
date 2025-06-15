module g {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires transitive javafx.graphics;
    
    opens g to javafx.fxml;
    exports g;
}

