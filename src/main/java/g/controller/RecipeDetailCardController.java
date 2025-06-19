package g.controller;

import java.util.ArrayList;

import g.DTO.RecipeDetailResponse;
import g.model.Ingredient;
import g.model.Recipe;
import g.service.RecipeService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RecipeDetailCardController {
    @FXML private Label recipeId;
    @FXML private Label title;
    @FXML private Label prepTime;
    @FXML private Label cookTime;
    @FXML private Label ingredients;
    @FXML private Label instructions;
    @FXML private Label imgAddr;
    @FXML private Label categories;

    private RecipeService recipeService;

    public RecipeDetailCardController() {
        // JavaFX requires a no-arg constructor
    }

    public void setRecipeService(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    public void loadRecipeData(int recipeId, String title, int prepTime, int cookTime,
                               ArrayList<Ingredient> ingredients, String instructions,
                               String imgAddr, String categories) {
        this.recipeId.setText(String.valueOf(recipeId));
        this.title.setText(title);
        this.prepTime.setText(String.valueOf(prepTime));
        this.cookTime.setText(String.valueOf(cookTime));
        this.ingredients.setText(ingredients.toString());
        this.instructions.setText(instructions);
        this.imgAddr.setText(imgAddr);
        this.categories.setText(categories);
    }

    public RecipeDetailResponse fetchRecipeData(int recipeId) {
        // 模拟数据
        Recipe testRecipe = new Recipe(recipeId, "Test Recipe", 10, 20, "Test instructions", "Test image address", 4);
        return new RecipeDetailResponse(testRecipe, null);
    }
}