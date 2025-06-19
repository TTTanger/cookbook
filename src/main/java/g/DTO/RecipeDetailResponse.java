package g.DTO;

import java.util.List;

import g.model.Ingredient;
import g.model.Recipe;

public class RecipeDetailResponse {

    private Recipe recipe;
    private List<Ingredient> ingredients;
    private String imagePath;

    public RecipeDetailResponse(Recipe recipe, List<Ingredient> ingredients, String imagePath) {
        this.recipe = recipe;
        this.ingredients = ingredients;
        this.imagePath = imagePath;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getImagePath() {
        return imagePath;
    }


}
