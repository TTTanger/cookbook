package g;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class MainController {
    @FXML private StackPane contentPane;
    @FXML private Node homePage;
    @FXML private Node categoryPage;

    @FXML
    public void initialize() {
        showHome(); 
    }

    @FXML
    private void showHome() {
        homePage.setVisible(true);
        categoryPage.setVisible(false);
    }

    @FXML
    private void showCategory() {
        homePage.setVisible(false);
        categoryPage.setVisible(true);
    }
}
