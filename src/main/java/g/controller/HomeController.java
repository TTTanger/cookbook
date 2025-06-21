package g.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class HomeController implements Initializable {

    @FXML
    private ListViewController listViewController;

    @FXML
    private RecipeDetailCardController recipeDetailCardController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listViewController.setOnItemSelected(recipeDetailCardController::renderRecipeData);
    }
}
