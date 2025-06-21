package g.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import g.DTO.RecipeDetailResponse;
import g.DTO.RecipeSummaryResponse;
import g.model.Ingredient;
import g.model.Recipe;
import g.service.RecipeService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class RecipeDetailCardController implements Initializable {

    private final RecipeService recipeService;

    public RecipeDetailCardController() {
        // JavaFX requires a no-arg constructor
        this.recipeService = new RecipeService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("RecipeDetailCardController initialized");
    }

    @FXML private Label recipeId;
    @FXML private Label title;
    @FXML private Label prepTime;
    @FXML private Label cookTime;
    @FXML private Label ingredients;
    @FXML private Label instructions;
    @FXML private Label imgAddr;

    @FXML
    public void loadRecipeData(int recipeId, String title, int prepTime, int cookTime,
            List<Ingredient> ingredients, String instructions,
            String imgAddr) {
        this.recipeId.setText(String.valueOf(recipeId));
        this.title.setText(title);
        this.prepTime.setText(String.valueOf(prepTime));
        this.cookTime.setText(String.valueOf(cookTime));
        this.ingredients.setText(ingredients.toString());
        this.instructions.setText(instructions);
        this.imgAddr.setText(imgAddr);
    }

    @FXML
    public void renderRecipeData(RecipeSummaryResponse recipeResponse) {
        int recipeId = recipeResponse.getRecipeId();
        RecipeDetailResponse recipeDetail = recipeService.getRecipeById(recipeId);
        Recipe recipe = recipeDetail.getRecipe();
        List<Ingredient> ingredients = recipeDetail.getIngredients();
        loadRecipeData(
            recipe.getRecipeId(),
            recipe.getTitle(),
            recipe.getPrepTime(),
            recipe.getCookTime(),
            ingredients,
            recipe.getInstruction(),
            recipe.getImgAddr()
        );

    }
}
