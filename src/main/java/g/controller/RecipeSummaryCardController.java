package g.controller;
import java.net.URL;
import java.util.ResourceBundle;

import g.service.RecipeService;
import javafx.fxml.Initializable;


public class RecipeSummaryCardController implements Initializable {
    private RecipeService recipeService;

    public RecipeSummaryCardController() {
        this.recipeService = new RecipeService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("RecipeSummaryCardController initialized");
    }
    
}
