package g.dto;

import java.util.List;

import g.model.Ingredient;
import g.model.Recipe;

public class RecipeDetailResponse {

    private Recipe recipe;
    private List<Ingredient> ingredients;

    public RecipeDetailResponse(Recipe recipe, List<Ingredient> ingredients) {
        this.recipe = recipe;
        this.ingredients = ingredients;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

}
