package g;

import java.io.IOException;

import javafx.fxml.FXML;

public class MainController {

    @FXML
    public void goToMain() throws IOException {
        App.setRoot("Main");
    }
}
