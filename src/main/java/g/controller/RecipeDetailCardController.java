package g.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import g.DTO.RecipeDetailResponse;
import g.model.Ingredient;
import g.model.Recipe;
import g.service.RecipeService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class RecipeDetailCardController implements Initializable {

    private RecipeService recipeService;

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
    public void renderRecipeData(){
        RecipeDetailResponse recipeDetail = fetchRecipeData(1);
        Recipe recipe = recipeDetail.getRecipe();
        ArrayList<Ingredient> ingredientsList = new ArrayList<>(recipeDetail.getIngredients());
        
        if (recipe != null) {
            loadRecipeData(recipe.getRecipeId(), recipe.getTitle(), recipe.getPrepTime(),
                    recipe.getCookTime(), ingredientsList, recipe.getInstruction(),
                    recipe.getImgAddr());
        } else {
            System.out.println("Recipe not found");
        }
    }
    
    public void loadRecipeData(int recipeId, String title, int prepTime, int cookTime,
            ArrayList<Ingredient> ingredients, String instructions,
            String imgAddr) {
        this.recipeId.setText(String.valueOf(recipeId));
        this.title.setText(title);
        this.prepTime.setText(String.valueOf(prepTime));
        this.cookTime.setText(String.valueOf(cookTime));
        this.ingredients.setText(ingredients.toString());
        this.instructions.setText(instructions);
        this.imgAddr.setText(imgAddr);
    }

    public RecipeDetailResponse fetchRecipeData(int recipeId) {
        RecipeDetailResponse recipe = recipeService.getRecipeById(recipeId);
        return recipe;
    }

}
